package hu.bme.iit.dynamicmenu

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.annotation.RequiresApi
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import hu.bme.iit.dynamicmenu.databinding.ActivityMainBinding
import hu.bme.iit.dynamicmenu.ui.dynamic.DynamicFunction

class MainActivity : AppCompatActivity() {

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

        val navMenu: Menu = navView.menu

        val function = DynamicFunction(navMenu)
        Log.i(null, "FUNCTION")

        // Kiválasztott menüelemre listener
        navView.setNavigationItemSelectedListener {
            menuItem ->
                when(menuItem.itemId) {
                    R.id.nav_home -> {
                        function.clicksMap.computeIfPresent(R.id.nav_home) { _, v -> v + 1 }
                        function.changeMenuItemsColor(navMenu)
                        //function.changeOrders(navMenu)
                        function.changeTextSize(navMenu)
                        drawerLayout.closeDrawers()
                    }
                    R.id.nav_gallery -> {
                        function.clicksMap.computeIfPresent(R.id.nav_gallery) { _, v -> v + 1 }
                        function.changeMenuItemsColor(navMenu)
                        //function.changeOrders(navMenu)
                        function.changeTextSize(navMenu)
                        drawerLayout.closeDrawers()
                    }
                    R.id.nav_item -> {
                        function.clicksMap.computeIfPresent(R.id.nav_item) { _, v -> v + 1 }
                        function.changeMenuItemsColor(navMenu)
                        //function.changeOrders(navMenu)
                        function.changeTextSize(navMenu)
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

}