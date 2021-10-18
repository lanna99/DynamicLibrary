package hu.bme.iit.dynamicmenu

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.parseAsHtml
import hu.bme.iit.dynamicmenu.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private var homeClickCounter = 0
    private var galleryClickCounter = 0
    private var itemClickCounter = 0

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_item
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Kiválasztott menüelemre listener
        navView.setNavigationItemSelectedListener {
            menuItem ->
                when(menuItem.itemId) {
                    R.id.nav_home -> {
                        homeClickCounter++
                        changeNavMenuItems()
                        drawerLayout.closeDrawers()
                    }
                    R.id.nav_gallery -> {
                        galleryClickCounter++
                        changeNavMenuItems()
                        drawerLayout.closeDrawers()
                    }
                    R.id.nav_item -> {
                        itemClickCounter++
                        changeNavMenuItems()
                        drawerLayout.closeDrawers()
                    }
                }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun MenuItem.setTitleColor(color: Int) {
        val hexColor = Integer.toHexString(color).uppercase(Locale.getDefault()).substring(2)
        val html = "<font color='#$hexColor'>$title</font>"
        this.title = html.parseAsHtml()
    }

    /*
    @RequiresApi(Build.VERSION_CODES.O)
    private fun changeNavMenuItems(id: Int) {
        val navView: NavigationView = binding.navView
        val navMenu: Menu = navView.menu
        var counter: Int = 0

        when (id) {
            R.id.nav_home -> {
                counter = homeClickCounter
            }
            R.id.nav_gallery -> {
                counter = galleryClickCounter
            }
            R.id.nav_item -> {
                counter = itemClickCounter
            }
        }

        navMenu.findItem(id).setTitleColor(Color.valueOf(counter * 0.1f, 0.0f, 0.0f).toArgb())
    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    private fun changeNavMenuItems() {
        val navView: NavigationView = binding.navView
        val navMenu: Menu = navView.menu

        val counterList = listOf(homeClickCounter, galleryClickCounter, itemClickCounter)
        when (counterList.maxOrNull()) {
            homeClickCounter -> {
                navMenu.findItem(R.id.nav_home).setTitleColor(Color.RED)
                navMenu.findItem(R.id.nav_gallery)
                    .setTitleColor(Color.valueOf((galleryClickCounter.toFloat() / homeClickCounter) * 1.0f,
                        0.0f,
                        0.0f).toArgb())
                navMenu.findItem(R.id.nav_item)
                    .setTitleColor(Color.valueOf((itemClickCounter.toFloat() / homeClickCounter) * 1.0f,
                        0.0f,
                        0.0f).toArgb())
            }
            galleryClickCounter -> {
                navMenu.findItem(R.id.nav_gallery).setTitleColor(Color.RED)
                navMenu.findItem(R.id.nav_home)
                    .setTitleColor(Color.valueOf((homeClickCounter.toFloat() / galleryClickCounter) * 1.0f,
                        0.0f,
                        0.0f).toArgb())
                navMenu.findItem(R.id.nav_item)
                    .setTitleColor(Color.valueOf((itemClickCounter.toFloat() / galleryClickCounter) * 1.0f,
                        0.0f,
                        0.0f).toArgb())
            }
            itemClickCounter -> {
                navMenu.findItem(R.id.nav_item).setTitleColor(Color.RED)
                navMenu.findItem(R.id.nav_home)
                    .setTitleColor(Color.valueOf((homeClickCounter.toFloat() / itemClickCounter) * 1.0f,
                        0.0f,
                        0.0f).toArgb())
                navMenu.findItem(R.id.nav_gallery)
                    .setTitleColor(Color.valueOf((galleryClickCounter.toFloat() / itemClickCounter) * 1.0f,
                        0.0f,
                        0.0f).toArgb())
            }
        }

    }

}