package com.example.ujinsia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {
    private var loginEmail: EditText? = null
    private var loginPassword: EditText? = null
    private var registerhere: Button? = null
    private var buttonlogin: Button? = null
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        loginEmail = findViewById(R.id.inputemail)
        loginPassword = findViewById(R.id.inputpassword)
        registerhere = findViewById(R.id.gotoRegister2)
        buttonlogin = findViewById(R.id.btn_login)

        mAuth = FirebaseAuth.getInstance()

        buttonlogin!!.setOnClickListener {
            loginUser()
        }
        registerhere!!.setOnClickListener {
            startActivity(Intent(this,SignupActivity::class.java))
        }

    }
    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth?.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            // Show login form
        }
    }
    private fun loginUser(){
        val email = loginEmail!!.text.toString().trim()
        val password = loginPassword!!.text.toString().trim()

        if (email.isEmpty()){
            loginEmail!!.error = "Email cannot be empty"
            loginEmail!!.requestFocus()
        }else if(password.isEmpty()){
            loginPassword!!.error = "Password cannot be empty"
            loginPassword!!.requestFocus()
        }else {
            mAuth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                    task->
                if (task.isSuccessful){
                    Toast.makeText(this,"User logged in successfully",
                        Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,MainActivity::class.java))
                }else{
                    Toast.makeText(this,"User log in failed",
                        Toast.LENGTH_LONG).show()
                }
            }
        }

    }

}