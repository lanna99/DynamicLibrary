package hu.bme.iit.dynamicmenu.ui.dynamic

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.core.text.parseAsHtml
import androidx.core.view.forEachIndexed
import hu.bme.iit.dynamicmenu.R
import java.util.*
import kotlin.collections.HashMap

class DynamicRedFunction(menu: Menu) {
    private var map: HashMap<Int, Int> = hashMapOf() // key: menu item id, value: click count
    private var maxClickValue: Int = 0

    // Az adott menühöz tartozó összes menu elemet megkeresi és feltölti
    // az id-jával, mint key és hozzá tartozó 0 értékkel, amely a kattinstásszám értéke.
    private fun getHashMap(menu: Menu): HashMap<Int, Int> {
        menu.forEachIndexed { index, item ->
            map[item.itemId] = 0
        }
        Log.i(null, "HASHMAAP")
        return map
    }

    var clicksMap = getHashMap(menu)

    // Megváltoztatja az adott menüelem title színét
    private fun MenuItem.setTitleColor(color: Int) {
        val hexColor = Integer.toHexString(color).uppercase(Locale.getDefault()).substring(2)
        val html = "<font color='#$hexColor'>$title</font>" //<icon color='#$hexColor'>$icon</icon>"
        this.title = html.parseAsHtml()
    }

    // Kiszámolja minden menüelemre, hogy mennyire legyen piros.
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
        Log.i(null, maxClickValue.toString())
        calculateColors(menu, maxClickValue)
    }

    // LinkedHashMap tartja a sorrendet is, a sima HashMap nem!!
    // TODO: próbálkozás az activity_main_drawer.xml itemjeinek orderInCategory-jával
    fun changeOrders(menu: Menu) {
        val sortedClicksMap = clicksMap.toSortedMap(compareByDescending { it })
        sortedClicksMap.forEach {
                (key, _) ->
            menu.add(menu.findItem(key).itemId)
            //menu.add(Menu.NONE, menu.findItem(key).itemId, clicksMap[menu.findItem(key)].and(1), menu.findItem(key).title)
            menu.removeItem(menu.findItem(key).itemId)
        }
    }

    private fun MenuItem.setTitleSize(size: Float) {
        val spanString: SpannableString = SpannableString(title.toString())
        val end: Int = spanString.length
        spanString.setSpan(RelativeSizeSpan(size), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun calculateTextSizes(menu: Menu, maxClickValue: Int) {
        clicksMap.forEach {
                (key, value) ->
                    menu.findItem(key).setTitleSize(value.toFloat() / maxClickValue.toFloat())
        }
    }

    fun changeTextSize(menu: Menu) {
        maxClickValue = clicksMap.maxByOrNull { it.value }?.value!!
        calculateTextSizes(menu, maxClickValue)
    }

}