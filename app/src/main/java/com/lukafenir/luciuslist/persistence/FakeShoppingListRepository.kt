package com.lukafenir.luciuslist.persistence

import com.lukafenir.luciuslist.model.ShoppingItem

//For previews and tests only
class FakeShoppingListRepository(
    initialItems: List<ShoppingItem> = emptyList()
) : ShoppingListRepository {
    private val items = mutableListOf<ShoppingItem>()

    init {
        items.addAll(initialItems)
    }

    override fun saveItems(items: List<ShoppingItem>) {
        this.items.clear()
        this.items.addAll(items)
    }

    override suspend fun loadItems(): List<ShoppingItem> {
        return items.toList()
    }
}