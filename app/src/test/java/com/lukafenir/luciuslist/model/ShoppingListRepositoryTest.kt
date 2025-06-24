package com.lukafenir.luciuslist.model

import android.content.Context
import com.google.gson.Gson
import com.lukafenir.luciuslist.io.FileOperations
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import java.io.File
import java.io.IOException

class ShoppingListRepositoryTest {

    @Mock
    private lateinit var mockContext: Context
    @Mock
    private lateinit var mockFileOperations: FileOperations

    private lateinit var repository: ShoppingListRepository
    private lateinit var mockFilesDir: File
    private lateinit var gson: Gson

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        gson = Gson()
        mockFilesDir = File(System.getProperty("java.io.tmpdir"))
        `when`(mockContext.filesDir).thenReturn(mockFilesDir)

        repository = ShoppingListRepository(mockContext, mockFileOperations)
    }

    @Test
    fun `saveItems saves a shopping list to a JSON file`(){
        //given
        val shoppingList = listOf(
            ShoppingItem(1, "Cheese", 1),
            ShoppingItem(2, "Banana", 1),
            ShoppingItem(69, "Cake", 1)
        )
        val expectedJson = gson.toJson(shoppingList)

        //when
        repository.saveItems(shoppingList)

        //then
        argumentCaptor <File>().apply {
            verify(mockFileOperations).writeToFile(capture(), eq(expectedJson))
            assertThat(firstValue.name).isEqualTo("shopping_list.json")
        }
    }

    @Test
    fun `saveItems saves an empty shopping list to a JSON file`(){
        //given
        val emptyList = emptyList<ShoppingItem>()
        val expectedJson = "[]"

        //when
        repository.saveItems(emptyList)

        //then
        argumentCaptor <File>().apply {
            verify(mockFileOperations).writeToFile(capture(), eq(expectedJson))
            assertThat(firstValue.name).isEqualTo("shopping_list.json")
        }
    }

    @Test //TODO implement
    fun `saveItems does not throw an exception when write to file fails`(){
        //given
        `when`(mockFileOperations.writeToFile(any(), anyString())).thenThrow(IOException("Write failure"))

        //when
        assertThatNoException().isThrownBy { repository.saveItems(emptyList()) }

        //then
        verify(mockFileOperations).writeToFile(any(), anyString())
    }
}