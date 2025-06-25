package com.lukafenir.luciuslist.persistence

import android.content.Context
import com.google.gson.Gson
import com.lukafenir.luciuslist.io.AndroidLogger
import com.lukafenir.luciuslist.io.DefaultFileOperations
import com.lukafenir.luciuslist.io.FileOperations
import com.lukafenir.luciuslist.io.Logger
import com.lukafenir.luciuslist.model.ShoppingItem
import com.lukafenir.luciuslist.model.ShoppingItemListType
import java.io.File

class DefaultShoppingListRepository(
    private val context: Context,
    private val fileOperations: FileOperations = DefaultFileOperations(),
    private val logger: Logger = AndroidLogger()
) : ShoppingListRepository {
    private val LOGGER_NAME = "ShoppingListRepository"
    private val fileName = "shopping_list.json"
    private val gson = Gson()

    override fun saveItems(items: List<ShoppingItem>){
        try {
            fileOperations.writeToFile(File(context.filesDir, fileName), gson.toJson(items))
            logger.d(LOGGER_NAME, "Shopping list saved")
        } catch (e: Exception) {
            logger.e(LOGGER_NAME, "Error saving shopping list", e)
        }
    }

    override fun loadItems(): List<ShoppingItem> {
        return try {
            if(!fileOperations.fileExists(File(context.filesDir, fileName))){
                logger.d(LOGGER_NAME, "No save file found")
                return emptyList()
            }
            val shoppingItems: List<ShoppingItem> = gson.fromJson(
                fileOperations.readFromFile(File(context.filesDir, fileName)),
                ShoppingItemListType().type);
            logger.d(LOGGER_NAME, "Shopping list loaded")
            shoppingItems
        } catch (e: Exception){
            logger.e(LOGGER_NAME, "Error loading shopping list", e)
            emptyList()
        }
    }
}
