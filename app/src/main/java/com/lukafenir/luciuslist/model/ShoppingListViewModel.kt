package com.lukafenir.luciuslist.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ShoppingListViewModel : ViewModel() {
    private val _items = mutableStateListOf<ShoppingItem>()
    val items: List<ShoppingItem> = _items

    fun addItem(name: String){
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

    fun deleteItem(name: String){

    }
}