package com.example.ujinsia

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserProfileActivity : AppCompatActivity() {

    private lateinit var fullNameTextView: TextView
    private lateinit var emailTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        fullNameTextView = findViewById(R.id.tv_full_name)
        emailTextView = findViewById(R.id.tv_email)

        val loggedInUser = getLoggedInUser()
        if (loggedInUser != null) {
            fullNameTextView.text = loggedInUser.displayName
            emailTextView.text = loggedInUser.email
        }
    }

    private fun getLoggedInUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}
