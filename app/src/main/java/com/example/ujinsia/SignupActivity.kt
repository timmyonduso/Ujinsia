package com.example.ujinsia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private var regName: EditText? = null
    private var regEmail: EditText? = null
    private var regPassword: EditText? = null
    private var loginHere: TextView? = null
    private var register: Button? = null
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        regName = findViewById(R.id.inputName)
        regEmail = findViewById(R.id.inputemail)
        regPassword = findViewById(R.id.inputpassword)
        loginHere = findViewById(R.id.loginInstead)
        register = findViewById(R.id.btn_signup)

        mAuth = FirebaseAuth.getInstance()

        register!!.setOnClickListener {
            createUser()
        }
        loginHere!!.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

    }

    private fun createUser(){

        val name = regName!!.text.toString().trim()
        val email = regEmail!!.text.toString().trim()
        val password = regPassword!!.text.toString().trim()


        if (name.isEmpty()){
            regName!!.error = "Please provide your full name"
            regName!!.requestFocus()
        } else if (email.isEmpty()){
            regEmail!!.error = "Email cannot be empty"
            regEmail!!.requestFocus()
        }else if(password.isEmpty()){
            regPassword!!.error = "Password cannot be empty"
            regPassword!!.requestFocus()
        }else {
            mAuth!!.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                    task->
                if (task.isSuccessful){
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