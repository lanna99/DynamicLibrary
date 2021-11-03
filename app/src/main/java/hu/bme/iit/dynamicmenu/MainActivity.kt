package hu.bme.iit.dynamicmenu

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
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
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    private var clicksMap: HashMap<Int, Int> = hashMapOf(R.id.nav_home to 0,
                                                         R.id.nav_gallery to 0,
                                                         R.id.nav_item to 0)

    private var maxClickValue: Int = 0

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
                        clicksMap.computeIfPresent(R.id.nav_home) { _, v -> v + 1 }
                        changeNavMenuItemsColor()
                        drawerLayout.closeDrawers()
                    }
                    R.id.nav_gallery -> {
                        clicksMap.computeIfPresent(R.id.nav_gallery) { _, v -> v + 1 }
                        changeNavMenuItemsColor()
                        drawerLayout.closeDrawers()
                    }
                    R.id.nav_item -> {
                        clicksMap.computeIfPresent(R.id.nav_item) { _, v -> v + 1 }
                        changeNavMenuItemsColor()
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

    // Megváltoztatja az adott nav menü elem title színét
    fun MenuItem.setTitleColor(color: Int) {
        val hexColor = Integer.toHexString(color).uppercase(Locale.getDefault()).substring(2)
        val html = "<font color='#$hexColor'>$title</font>"
        this.title = html.parseAsHtml()
    }

    // Kiszámolja minden navMenu elemre, hogy mennyire legyen piros.
    // A függvény paraméterben megkapja a legtöbb kattintást, ez alapján számol.
    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateColors(maxClickValue: Int) {
        val navView: NavigationView = binding.navView
        val navMenu: Menu = navView.menu
        clicksMap.forEach {
                (key, value) ->
            navMenu.findItem(key)
                .setTitleColor(Color.valueOf((value.toFloat() / maxClickValue) * 1.0f,
                                             0.0f,
                                             0.0f).toArgb())
        }
    }

    // Egymástól függően egyre pirosabbak a gyakran használt elemek (csúnya megoldás)
    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun changeNavMenuItemsColor() {
        maxClickValue = clicksMap.maxByOrNull { it.value }?.value!!
        calculateColors(maxClickValue)
    }

}