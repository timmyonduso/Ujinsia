package com.example.ujinsia

import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ujinsia.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding:ActivityProfileBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var databaseReference:DatabaseReference
    private lateinit var storageReference:StorageReference
    private lateinit var dialog:Dialog
    private lateinit var user: User
    private lateinit var uid:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        if (uid.isNotEmpty()){

            getUserData()


            binding.buttonEdit.setOnClickListener{
                signOut()
            }
        }
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

                    val nameTextView = findViewById<TextView>(R.id.tvfullName)
                    nameTextView.text = name

                    val emailTextView = findViewById<TextView>(R.id.tvEmail)
                    emailTextView.text = email

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

        storageReference = FirebaseStorage.getInstance().reference.child("Users/$uid")
        val localFile = File.createTempFile("tempImage", "jpg")
        storageReference.getFile(localFile).addOnSuccessListener {

            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.profileImage.setImageBitmap(bitmap)
            hideProgressBar()

        }.addOnFailureListener{

            hideProgressBar()
            Toast.makeText(this@ProfileActivity, "Failed to retrieve image", Toast.LENGTH_SHORT).show()

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


