package com.example.ujinsia


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ujinsia.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView : BottomNavigationView
    private lateinit var drawer: DrawerLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//bottom navbar
        bottomNavigationView = findViewById(R.id.bottomNav)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Homie -> {
                    // Handle the "Home" action
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.connect -> {
                    // Handle the action
                    val intent = Intent(this, UserCategoryActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.infooo -> {
                    // Handle the "Search" action
                    val intent = Intent(this, LearnActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }



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


//Home Page layout initialization
        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(items, this)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }


        //Status bar Colour
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.primary)
        }

    }




    private val items = listOf(
//    <a target="_blank" href="https://icons8.com/icon/34998/choice">Choice</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
    Item("Get aid", R.drawable.anrepoti,"Submit a confidential report", GetAidActivity::class.java),
//    <a target="_blank" href="https://icons8.com/icon/114259/school">School</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
    Item("Learn more", R.drawable.kitabu,"Increase your GBV awareness",LearnActivity::class.java),
//    <a target="_blank" href="https://icons8.com/icon/xx3TVZuDK27S/messages">Messages</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
    Item("Get help from professionals", R.drawable.messagess,"Receive certified information from veterans",ConnectActivity::class.java),
//    <a target="_blank" href="https://icons8.com/icon/60301/europe">Europe</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
    Item("Locate nearest help center", R.drawable.locashen,"Pinpoint available support", LocatorActivity::class.java)
)


//    Menu items inflater
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater: MenuInflater = menuInflater
//        inflater.inflate(R.menu.my_menu,menu)
//
//        return super.onCreateOptionsMenu(menu)
//
//    }



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


        else -> return super.onOptionsItemSelected(item)
    }
    drawer.closeDrawer(GravityCompat.END)
    return true
}

//Navigation drawer click listener

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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
    }
}


