package com.lukafenir.luciuslist.persistence

import com.lukafenir.luciuslist.model.ShoppingItem

interface ShoppingListRepository {
    fun saveItems(items: List<ShoppingItem>)
    suspend fun loadItems(): List<ShoppingItem>
}