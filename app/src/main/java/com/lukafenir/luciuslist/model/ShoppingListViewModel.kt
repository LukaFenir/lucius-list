package com.lukafenir.luciuslist.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import android.util.Log

class ShoppingListViewModel : ViewModel() {
    private val _items = mutableStateListOf<ShoppingItem>()
    val items: List<ShoppingItem> = _items

    fun addItem(name: String){
        Log.d("ShoppingListView", "Added item $name")
        _items.add(
            ShoppingItem(
                id = _items.size + 1,
                name = name,
                quantity = 1,
                category = "",
                isChecked = false
            )
        )
    }

    fun deleteItem(item: ShoppingItem){
        Log.d("ShoppingListView", "Deleted item ${item.name}")
        _items.remove(item);
    }
}