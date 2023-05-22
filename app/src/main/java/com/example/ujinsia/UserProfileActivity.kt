package com.example.ujinsia

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.ujinsia.databinding.ActivityUserProfileBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class UserProfileActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference:StorageReference
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var imageUri:Uri
    private lateinit var dialog:Dialog
    private val GALLERY_REQUEST_CODE = 1
    private val CAMERA_REQUEST_CODE = 2
    private val EDIT_IMAGE_REQUEST_CODE = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //code for toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        toolbar.setNavigationOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }


//Navigation drawer
        drawer = this.findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


        // Initialize Firebase Auth, Firestore and Storage
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        // Initialize Firebase Auth, Firestore and Storage
        storageReference = storage.reference


        binding.saveBtn.setOnClickListener{

            showProgressBar()
            val userName = binding.etFirstName.text.toString()
            val userEmail = binding.etLastName.text.toString()
            val userLocation = binding.etBio.text.toString()


            if (userName.isEmpty()){
                binding.etFirstName.error = "Please provide your full name"
                binding.etFirstName.requestFocus()
                hideProgressBar()
            } else if (userEmail.isEmpty()){
                binding.etLastName.error = "Please provide your email"
                binding.etLastName.requestFocus()
                hideProgressBar()
            }else if(userLocation.isEmpty()){
                binding.etBio.error = "Please add your location"
                binding.etBio.requestFocus()
                hideProgressBar()
            }
            else{
               lessgo()
            }
        }


        databaseReference.child(auth.currentUser?.uid ?: "").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(User::class.java)
                    user?.let {
                        binding.etFirstName.setText(it.Name)
                        binding.etLastName.setText(it.Email)
                        binding.etBio.setText(it.Location)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("UserProfileActivity", "Failed to read value.", error.toException())
            }
        })

        // Display profile image from Firebase Storage to hdodenhof circle image view
        val profileImageReference = storageReference.child("${auth.currentUser?.uid}ProfileImage/")
        profileImageReference.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this@UserProfileActivity)
                .load(uri)
                .placeholder(R.drawable.community22)
                .error(R.drawable.community22)
                .into(binding.profileImage)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Homie -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.Setto -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }

        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    imageUri = data?.data!!
                    launchImageEditor(imageUri)
                }
                CAMERA_REQUEST_CODE -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    imageUri = getImageUri(this, bitmap)
                    launchImageEditor(imageUri)
                }
                EDIT_IMAGE_REQUEST_CODE -> {
                    imageUri = data?.data!!
                    uploadImageToFirebase()
                }
            }
        }
    }

    private fun lessgo() {
        val uid = auth.currentUser?.uid

        val Name = binding.etFirstName.text.toString()
        val Email = binding.etLastName.text.toString()
        val Location = binding.etBio.text.toString()

        val user = User(Name, Email, Location)

        if(uid!=null){
            databaseReference.child(uid).setValue(user).addOnCompleteListener{

                if (it.isSuccessful){

                    uploadProfilePic()

                }
                else{

                    hideProgressBar()
                    Toast.makeText(this@UserProfileActivity, "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
            }
        }

        updateUserData(Name,Email,Location)

    }

    private fun updateUserData(Name: String, Email: String, Location: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = auth.currentUser?.uid

        if (user != null) {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(Name)
                .build()
            user.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("UserProfileActivity", "User profile updated.")
                    } else {
                        Log.d("UserProfileActivity", "Failed to update user profile.")
                    }
                }
            user.updateEmail(Email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("UserProfileActivity", "User email updated.")
                    } else {
                        Log.d("UserProfileActivity", "Failed to update user email.")
                    }
                }
        }
//        the following code to update the user data in Firebase Realtime Database
        databaseReference.child(uid!!).child("Name").setValue(Name)
        databaseReference.child(uid).child("Email").setValue(Email)
        databaseReference.child(uid).child("Location").setValue(Location)

    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun uploadProfilePic() {

        val options = arrayOf("Choose from Gallery", "Take a Picture")
        val builder = AlertDialog.Builder(this@UserProfileActivity)
        builder.setTitle("Upload Profile Picture")
        builder.setItems(options) { _, item ->
            when (item) {
                0 -> {
                    val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
                }
                1 -> {
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (takePictureIntent.resolveActivity(packageManager) != null) {
                        startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
                    }
                }
            }
        }
        builder.show()
        hideProgressBar()
    }



    private fun launchImageEditor(imageUri: Uri) {
        val editIntent = Intent(Intent.ACTION_EDIT)
        editIntent.setDataAndType(imageUri, "image/*")
        editIntent.putExtra("return-data", true)
        startActivityForResult(Intent.createChooser(editIntent, "Edit Image"), EDIT_IMAGE_REQUEST_CODE)
        hideProgressBar()
    }


    private fun uploadImageToFirebase() {
        storageReference = FirebaseStorage.getInstance().getReference("ProfileImage/" + auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {
            hideProgressBar()
            Toast.makeText(this@UserProfileActivity, "Profile successfully updated", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ProfileActivity::class.java))
        }.addOnFailureListener {
            hideProgressBar()
            Toast.makeText(this@UserProfileActivity, "Failed to upload the image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    private fun showProgressBar(){

        dialog = Dialog(this@UserProfileActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }

    private fun hideProgressBar(){

        dialog.dismiss()

    }
}