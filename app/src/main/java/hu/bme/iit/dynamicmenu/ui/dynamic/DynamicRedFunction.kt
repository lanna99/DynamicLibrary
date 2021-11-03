package hu.bme.iit.dynamicmenu.ui.dynamic

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.core.text.parseAsHtml
import java.util.*
import kotlin.collections.HashMap

class DynamicRedFunction(map: HashMap<Int, Int>) {
    var clicksMap: HashMap<Int, Int> = map
    var maxClickValue: Int = 0

    // Megváltoztatja az adott menü elem title színét
    private fun MenuItem.setTitleColor(color: Int) {
        val hexColor = Integer.toHexString(color).uppercase(Locale.getDefault()).substring(2)
        val html = "<font color='#$hexColor'>$title</font>"
        this.title = html.parseAsHtml()
    }

    // Kiszámolja minden menu elemre, hogy mennyire legyen piros.
    // A függvény paraméterben megkapja a legtöbb kattintást, ez alapján számol.
    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateColors(menu: Menu, maxClickValue: Int) {
        clicksMap.forEach {
                (key, value) ->
            menu.findItem(key)
                .setTitleColor(Color.valueOf((value.toFloat() / maxClickValue) * 1.0f,
                    0.0f,
                    0.0f).toArgb())
        }
    }

    // Egymástól függően egyre pirosabbak a gyakran használt elemek
    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.O)
    fun changeMenuItemsColor(menu: Menu) {
        maxClickValue = clicksMap.maxByOrNull { it.value }?.value!!
        calculateColors(menu, maxClickValue)
    }
}