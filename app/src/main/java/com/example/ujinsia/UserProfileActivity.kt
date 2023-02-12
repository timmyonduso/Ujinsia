package com.example.ujinsia

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ujinsia.databinding.ActivityUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference:StorageReference
    private lateinit var imageUri:Uri
    private lateinit var dialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        binding.saveBtn.setOnClickListener{

            showProgressBar()
            val userName = binding.etFirstName.text.toString()
            val userEmail = binding.etLastName.text.toString()
            val userLocation = binding.etBio.text.toString()

            val user = User(userName,userEmail,userLocation)

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
        }

    }

    private fun uploadProfilePic() {

        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.community22}")
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {

            hideProgressBar()
            Toast.makeText(this@UserProfileActivity, "Profile successfully updated", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ProfileActivity::class.java))
        }.addOnFailureListener{

            hideProgressBar()
            Toast.makeText(this@UserProfileActivity, "Failed to upload the image", Toast.LENGTH_SHORT).show()

        }


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