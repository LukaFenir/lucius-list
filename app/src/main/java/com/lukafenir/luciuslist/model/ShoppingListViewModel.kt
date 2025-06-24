package com.lukafenir.luciuslist.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukafenir.luciuslist.io.AndroidLogger
import com.lukafenir.luciuslist.io.Logger
import kotlinx.coroutines.launch

class ShoppingListViewModel(
    private val repository: ShoppingListRepository,
    private val logger: Logger = AndroidLogger()
) : ViewModel() {

    private val _items = mutableStateListOf<ShoppingItem>()
    val items: List<ShoppingItem> = _items

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        loadItems()
    }

    fun addItem(name: String){
        logger.d("ShoppingListView", "Added item $name")
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
        logger.d("ShoppingListView", "Deleted item ${item.name}")
        _items.remove(item);
    }

    private fun loadItems() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val loadedItems = repository.loadItems()
                _items.clear()
                _items.addAll(loadedItems)
            } finally {
                _isLoading.value = false
            }
        }
    }
}