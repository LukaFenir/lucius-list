package com.lukafenir.luciuslist.model

data class ShoppingItem(
    val id: Int,
    val name: String,
    val quantity: Int = 1,
    val category: String = "",
    val isChecked: Boolean = false
)