package com.example.ujinsia

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class GetAidActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_aid)

//ListView initialization
        val listView= findViewById<ListView>(R.id.listView)
        val list= mutableListOf<Model>()


//adding images
        list.add(Model("Submit detailed report","",R.drawable.ic_baseline_local_police_24))
        list.add(Model("Connect with certified professional","",R.drawable.ic_baseline_contacts_24))

//Onclick listener to each of the items in the list
        listView.adapter= Adapter(this,R.layout.row,list)
        listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position:Int, id:Long ->

            if (position== 0) {
                val intent = Intent(this, FurtherAid::class.java)
                startActivity(intent)
            }
            if (position==1){
                val phone = "911"

                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))

                startActivity(intent)
            }

        }


        //code for toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }


        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        //Navigation drawer
        drawerLayout = this.findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //     Menu items click listener
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Homie -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.Setto -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_view -> {
                val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
                drawerLayout.openDrawer(GravityCompat.START)
            }
//        R.id.switch_theme -> {
//            // Handle the switch button click
//
//        }

            else -> return super.onOptionsItemSelected(item)
        }
        drawerLayout.closeDrawer(GravityCompat.END)
        return true
    }

    //Navigation drawer click listener
    @SuppressLint("UseSwitchCompatOrMaterialCode")
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
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}








