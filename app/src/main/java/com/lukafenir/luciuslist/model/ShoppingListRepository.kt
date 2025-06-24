package com.lukafenir.luciuslist.model

interface ShoppingListRepository {
    fun saveItems(items: List<ShoppingItem>)
    fun loadItems(): List<ShoppingItem>
}