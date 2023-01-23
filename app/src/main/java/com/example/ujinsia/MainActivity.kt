package com.example.ujinsia


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Switch
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var drawer: DrawerLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val color = ContextCompat.getColor(this, R.color.main)
//        window.decorView.setBackgroundColor(color)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        toolbar.setNavigationOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }



        drawer = this.findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(items, this)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.primary)
        }

    }

private val items = listOf(
    Item("Get aid", R.drawable.download22,"Submit a confidential report", GetAidActivity::class.java),
    Item("Learn more", R.drawable.ic_baseline_menu_book_24,"Increase your GBV awareness",LearnActivity::class.java),
    Item("Get help from professionals", R.drawable.ic_baseline_person_pin_24,"Receive certified information from veterans",ConnectActivity::class.java),
    Item("Locate nearest help center", R.drawable.ic_baseline_location_on_24,"Pinpoint available support", MainActivity::class.java)
)


    //     look at this section
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.my_menu,menu)
        val switch = menu?.findItem(R.id.switch_theme)?.actionView as Switch
        switch.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

//        switch.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            }
//            recreate()
//
//        }

        return super.onCreateOptionsMenu(menu)

    }



//     look at this section
@SuppressLint("UseSwitchCompatOrMaterialCode")
override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
        R.id.Homie -> {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            return true
        }
        R.id.about -> {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            return true
        }
        R.id.Setto -> {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            return true
        }
        R.id.switch_theme -> {
            // Handle the switch button click

        }
        R.id.menu_navigation -> {
            val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            drawerLayout.openDrawer(GravityCompat.START)


        }


        else -> return super.onOptionsItemSelected(item)
    }
    drawer.closeDrawer(GravityCompat.START)
    return true
}


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Homie -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.Setto -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.switch_theme -> {
                // Handle the switch button click

            }

        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}


//private fun toggleDarkMode() {
//    var isDarkMode = false
//
//    isDarkMode = !isDarkMode
//    AppCompatDelegate.setDefaultNightMode(
//        if (isDarkMode) {
//            AppCompatDelegate.MODE_NIGHT_YES
//        } else {
//            AppCompatDelegate.MODE_NIGHT_NO
//        }
//
//    )
//
//}

//fun switchTheme(activity: AppCompatActivity) {
//    val isDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
//    if (isDarkMode) {
//        // switch to light theme
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//    } else {
//        // switch to dark theme
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//    }
//    activity.recreate()
//}


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.my_menu, menu)
//        val myMenuItem = menu?.findItem(R.id.HomeFragment)
//        myMenuItem?.setOnMenuItemClickListener {
//            // Handle menu item click here
//            val intent = Intent(this, GetAidActivity::class.java)
//            startActivity(intent)
//            true
//        }
//        return super.onCreateOptionsMenu(menu)
//    }




