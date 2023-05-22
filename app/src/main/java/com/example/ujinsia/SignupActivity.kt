package com.example.ujinsia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ujinsia.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private var regName: EditText? = null
    private var regEmail: EditText? = null
    private var confPassword: EditText? = null
    private var regPassword: EditText? = null
    private var regLocation: EditText? = null
    private var loginHere: TextView? = null
    private var register: Button? = null
    private var mAuth: FirebaseAuth? = null
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        regName = findViewById(R.id.inputName)
        regEmail = findViewById(R.id.inputemail)
        regPassword = findViewById(R.id.inputpassword)
        confPassword = findViewById(R.id.confirm_password)
        regLocation = findViewById(R.id.inputlocation)
        loginHere = findViewById(R.id.loginInstead)
        register = findViewById(R.id.btn_signup)

        mAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        binding.btnSignup.setOnClickListener{
            createUser()
        }

        loginHere!!.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
        databaseReference = FirebaseDatabase.getInstance().reference

    }

    private fun createUser(){

        val name = regName!!.text.toString().trim()
        val email = regEmail!!.text.toString().trim()
        val password = regPassword!!.text.toString().trim()
        val confpassword = confPassword!!.text.toString().trim()
        val location = regLocation!!.text.toString().trim()

        if (name.isEmpty()){
            regName!!.error = "Please provide your full name"
            regName!!.requestFocus()
        } else if (email.isEmpty()){
            regEmail!!.error = "Email cannot be empty"
            regEmail!!.requestFocus()
        }else if(password.isEmpty()){
            regPassword!!.error = "Password cannot be empty"
            regPassword!!.requestFocus()
        }else if(confpassword.isEmpty()){
            confPassword!!.error = "Password cannot be empty"
            confPassword!!.requestFocus()
        }else if(location.isEmpty()){
            regLocation!!.error = "Add your location"
            regLocation!!.requestFocus()
        }else {
            mAuth!!.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task->
                if (task.isSuccessful){
                    // Get the user's ID
                    val userId = mAuth!!.currentUser!!.uid

                    // Create a hashmap to store the user's details
                    val userData = hashMapOf(
                        "Name" to name,
                        "Email" to email,
                        "Location" to location
                    )

                    // Store the user's data in the real-time database
                    FirebaseDatabase.getInstance().reference
                        .child("Users")
                        .child(userId)
                        .setValue(userData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Data saving failed", Toast.LENGTH_SHORT).show()
                        }

                    // Store the user's password in a different path
                    FirebaseDatabase.getInstance().reference
                        .child("Passwords")
                        .child(userId)
                        .setValue(password)

                    Toast.makeText(this,"User registered successfully",
                        Toast.LENGTH_LONG).show()

                    startActivity(Intent(this,LoginActivity::class.java))

                }else{
                    Toast.makeText(this,"User registration failed",
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}