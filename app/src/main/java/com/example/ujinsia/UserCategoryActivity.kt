package com.example.ujinsia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ujinsia.databinding.ActivityUserCategoryBinding

class UserCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}