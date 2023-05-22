package com.example.ujinsia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ujinsia.databinding.ActivityCpacontactBinding
import com.google.android.material.navigation.NavigationView

class CPAContactActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var binding:ActivityCpacontactBinding
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCpacontactBinding.inflate(layoutInflater)
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

        // set onClick listener for each cell in the middle column
        binding.cell12.setOnClickListener { showNumberDialog(getString(R.string.police_hotline), getString(R.string.numberssss)) }
        binding.cell22.setOnClickListener { showNumberDialog(getString(R.string.redcross), getString(R.string.numbers2)) }
        binding.cell32.setOnClickListener { showNumberDialog(getString(R.string.national_hotline_gbv_victims), getString(R.string.numbers3)) }
        binding.cell42.setOnClickListener { showNumberDialog(getString(R.string.child_line_kenya), getString(R.string.numbers4)) }
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


    private fun showNumberDialog(orgName: String, numbers: String) {
        val numbersArray = numbers.split(",")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose a number to call for $orgName")
        builder.setItems(numbersArray.toTypedArray()) { _, which ->
            val selectedNumber = numbersArray[which].replace("\\D".toRegex(), "")
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:$selectedNumber")
            startActivity(callIntent)
        }
        builder.show()
    }
}
