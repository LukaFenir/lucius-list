package com.lukafenir.luciuslist.model

import android.content.Context
import com.google.gson.Gson
import com.lukafenir.luciuslist.io.DefaultFileOperations
import com.lukafenir.luciuslist.io.FileOperations
import java.io.File

class ShoppingListRepository(
    private val context: Context,
    private val fileOperations: FileOperations = DefaultFileOperations()
) {
    private val fileName = "shopping_list.json"
    private val gson = Gson()

    fun saveItems(items: List<ShoppingItem>){
        fileOperations.writeToFile(File(context.filesDir, fileName), gson.toJson(items))
    }

    fun loadItems(): List<ShoppingItem> {
        return emptyList()
    }


}
