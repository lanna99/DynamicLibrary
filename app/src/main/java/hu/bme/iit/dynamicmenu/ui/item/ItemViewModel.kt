package hu.bme.iit.dynamicmenu.ui.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is item Fragment"
    }
    val text: LiveData<String> = _text
}