package com.example.ujinsia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavBar : AppCompatActivity() {

//    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav_bar)

//        loadFragment(HomeFragment())
//        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
//        bottomNav.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.connect -> {
//                    loadFragment(HomeFragment())
//                    true
//                }
//                R.id.Setto -> {
//                    loadFragment(HomeFragment())
//                    true
//                }
//                R.id.Bulletin -> {
//                    loadFragment(HomeFragment())
//                    true
//                }
//                else ->
//            }
//        }
    }
//    private  fun loadFragment(fragment: Fragment){
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.container,fragment)
//        transaction.commit()
//    }

}

