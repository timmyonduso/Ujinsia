package com.example.ujinsia

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class CCSContactActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ccscontact)



        val counties = arrayOf("Isiolo", "Samburu", "Marsabit", "Garissa", "Wajir", "Mandera", "Kakamega", "Busia", "Bungoma", "West Pokot", "Homabay", "Migori", "Nandi", "Narok", "Baringo", "Bomet", "Kisii", "Kilifi", "Elgeyo-Marakwet", "Murang'a", "Tana River" , "Turkana", "Kwale", "Kitui", "Kajiado", "Taita Taveta", "Laikipia", "Tharaka Nithi")
        val spinner = findViewById<Spinner>(R.id.county_spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, counties)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedCounty = counties[position]
                val contactDetails = when (selectedCounty) {
                    "Isiolo" -> "Contact Person: Njeru Mugo\nPhone: 0722559300"
                    "Samburu" -> "Contact Person: Jane Kabiro\nPhone: 0727349140"
                    "Marsabit" -> "Contact Person: Simeon Ogao\nPhone: 0710780471"
                    "Garissa" -> "Contact Person: Mohammed Hussein\nPhone: 0720402264"
                    "Wajir" -> "Contact Person: Abdinoor Sheikh\nPhone: 0720938187"
                    "Mandera" -> "Contact Person: Abdikadir Abdinoor\nPhone: 0717337030"
                    "Kakamega" -> "Contact Person: Richard Masika\nPhone: 0726018528"
                    "Busia" -> "Contact Person: Esther Wasige\nPhone: 0722455146"
                    "Bungoma" -> "Contact Person: Nabakwe Maube\nPhone: 0728529011"
                    "West Pokot" -> "Contact Person: Mugun Kaan\nPhone: 0724952369"
                    "Homabay" -> "Contact Person: Peter Kutere\nPhone: 0729795971"
                    "Migori" -> "Contact Person: John Odinya\nPhone: 0722690101"
                    "Nandi" -> "Contact Person: Hellen Wanyama\nPhone: 0714746381"
                    "Narok" -> "Contact Person: Fred Simi\nPhone: 0717180352"
                    "Baringo" -> "Contact Person: Omuse Otijom\nPhone: 0727636879"
                    "Bomet" -> "Contact Person: Duncan Ng'eno\nPhone: 0728290640"
                    "Kisii" -> "Contact Person: Beatrice Obutu\nPhone: 0720712513"
                    "Kilifi" -> "Contact Person: George Migosi\nPhone: 0721250594"
                    "Elgeyo-Marakwet" -> "Contact Person: Richard Mugata\nPhone: 0700937883"
                    "Murang'a" -> "Contact Person: Rhoda Mwikya\nPhone: 0726315946"
                    "Tana River" -> "Contact Person: Daniel Waiti\nPhone: 0721282543"
                    "Turkana" -> "Contact Person: Julis Yator\nPhone: 0725982702"
                    "Kwale" -> "Contact Person: Stephen Gitau\nPhone: 0721238558"
                    "Kitui" -> "Contact Person: Daniel Musembi\nPhone: 0727587813"
                    "Kajiado" -> "Contact Person: Samwel Masese\nPhone: 0720325497"
                    "Taita Taveta" -> "Contact Person: Masiwa Juma Boga\nPhone: 0711287804"
                    "Laikipia" -> "Contact Person: Ezekiel Omwansa\nPhone: 0721514841"
                    "Tharaka Nithi" -> "Contact Person: Julius Kiragu\nPhone: 0722396618"
                    else -> "No contact information available"
                }
                // display contactDetails in a TextView or similar widget
                val textView = findViewById<TextView>(R.id.contact_details_text_view)
                textView.text = contactDetails
                textView.setOnClickListener {
                    val phoneNumber = when (selectedCounty) {
                        "Isiolo" -> "0722559300"
                        "Samburu" -> "0727349140"
                        "Marsabit" -> "0710780471"
                        "Garissa" -> "0720402264"
                        "Wajir" -> "0720938187"
                        "Mandera" -> "0717337030"
                        "Kakamega" -> "0726018528"
                        "Busia" -> "0722455146"
                        "Bungoma" -> "0728529011"
                        "West Pokot" -> "0724952369"
                        "Homabay" -> "0729795971"
                        "Migori" -> "0722690101"
                        "Nandi" -> "0714746381"
                        "Narok" -> "0717180352"
                        "Baringo" -> "0727636879"
                        "Bomet" -> "0728290640"
                        "Kisii" -> "0720712513"
                        "Kilifi" -> "0721250594"
                        "Elgeyo-Marakwet" -> "0700937883"
                        "Murang'a" -> "0726315946"
                        "Tana River" -> "0721282543"
                        "Turkana" -> "0725982702"
                        "Kwale" -> "0721238558"
                        "Kitui" -> "0727587813"
                        "Kajiado" -> "0720325497"
                        "Taita Taveta" -> "0711287804"
                        "Laikipia" -> "0721514841"
                        "Tharaka Nithi" -> "0722396618"
                        else -> ""
                    }
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$phoneNumber")
                    startActivity(intent)

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // do nothing
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
                val intent = Intent(this, ProfileActivity::class.java)
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
                val intent = Intent(this, ProfileActivity::class.java)
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





