package com.example.ujinsia

import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ujinsia.databinding.ActivityProfileBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class ProfileActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var binding:ActivityProfileBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var databaseReference:DatabaseReference
    private lateinit var storageReference:StorageReference
    private lateinit var dialog:Dialog
    private lateinit var uid:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
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

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        if (uid.isNotEmpty()){

            getUserData()

            binding.buttonEdit2.setOnClickListener{
                startActivity(Intent(this, UserProfileActivity::class.java))
            }

            binding.buttonEdit.setOnClickListener{
                signOut()
            }
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

    private fun signOut() {
        auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    private fun getUserData() {

        showProgressBar()
        databaseReference.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists() && snapshot.getValue(User::class.java) != null) {
                    val name = snapshot.child("Name").value.toString()
                    val email = snapshot.child("Email").value.toString()
                    val location = snapshot.child("Location").value.toString()

                    val nameTextView = findViewById<TextView>(R.id.tvfullName)
                    nameTextView.text = name


                    val emailTextView = findViewById<TextView>(R.id.tvEmail)
                    emailTextView.text = email

                    val locationTextView = findViewById<TextView>(R.id.tvLocation)
                    locationTextView.text = location


                    getUserProfile()
                } else {
                    hideProgressBar()
                    Toast.makeText(this@ProfileActivity, "Failed to get user profile data", Toast.LENGTH_SHORT).show()
                }
                hideProgressBar()
            }

            override fun onCancelled(error: DatabaseError) {

                hideProgressBar()
                Toast.makeText(this@ProfileActivity, "Failed to get user profile data", Toast.LENGTH_SHORT).show()


            }


        })

    }

    private fun getUserProfile() {

        storageReference = FirebaseStorage.getInstance().reference.child("ProfileImage/$uid")
        val localFile = File.createTempFile("tempImage", "jpg")
        storageReference.getFile(localFile).addOnSuccessListener {

            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.profileImage.setImageBitmap(bitmap)
            hideProgressBar()

        }.addOnFailureListener{

            hideProgressBar()
//            Toast.makeText(this@ProfileActivity, "Failed to retrieve image", Toast.LENGTH_SHORT).show()

        }

    }

    private fun showProgressBar(){

        dialog = Dialog(this@ProfileActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }

    private fun hideProgressBar(){

        dialog.dismiss()

    }

}


