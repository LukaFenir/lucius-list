package com.lukafenir.luciuslist.persistence

import android.content.Context
import com.google.gson.Gson
import com.lukafenir.luciuslist.io.FileOperations
import com.lukafenir.luciuslist.io.NoOpLogger
import com.lukafenir.luciuslist.model.ShoppingItem
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.IOException

class DefaultShoppingListRepositoryTest {

    @MockK
    private lateinit var mockContext: Context
    @MockK
    private lateinit var mockFileOperations: FileOperations

    private lateinit var repository: DefaultShoppingListRepository
    private lateinit var mockFilesDir: File
    private lateinit var gson: Gson

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        gson = Gson()
        mockFilesDir = File(System.getProperty("java.io.tmpdir"))
        every { mockContext.filesDir } returns mockFilesDir

        repository = DefaultShoppingListRepository(mockContext, mockFileOperations, NoOpLogger())
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
        val fileSlot = slot<File>()

        every { mockFileOperations.writeToFile(capture(fileSlot), expectedJson) } just runs

        //when
        repository.saveItems(shoppingList)

        //then
        verify { mockFileOperations.writeToFile(capture(fileSlot), expectedJson) }
        assertThat(fileSlot.captured.name).isEqualTo("shopping_list.json")
    }

    @Test
    fun `saveItems saves an empty shopping list to a JSON file`(){
        //given
        val emptyList = emptyList<ShoppingItem>()
        val expectedJson = "[]"
        val fileSlot = slot<File>()

        every { mockFileOperations.writeToFile(capture(fileSlot), expectedJson) } just runs

        //when
        repository.saveItems(emptyList)

        //then
        verify { mockFileOperations.writeToFile(capture(fileSlot), expectedJson) }
        assertThat(fileSlot.captured.name).isEqualTo("shopping_list.json")
    }

    @Test
    fun `saveItems does not throw an exception when write to file fails`(){
        //given
        every { mockFileOperations.writeToFile(any(), any()) } throws IOException("Write failure")

        //when
        assertThatNoException().isThrownBy { repository.saveItems(emptyList()) }

        //then
        verify { mockFileOperations.writeToFile(any(), any()) }
    }

    @Test
    fun `when a file does not exist, loadItems does not read file and return an empty list`() = runTest {
        //given
        every { mockFileOperations.fileExists(any()) } returns false

        //when
        val itemsList = repository.loadItems()

        //then
        assertThat(itemsList).isEmpty()
        verify { mockFileOperations.fileExists(any()) }
        confirmVerified(mockFileOperations)
    }

    @Test
    fun `when a file with an empty array exists, loadItems returns an empty list`() = runTest {
        //given
        every { mockFileOperations.fileExists(any()) } returns true
        every { mockFileOperations.readFromFile(any()) } returns "[]"

        //when
        val itemsList = repository.loadItems()

        //then
        assertThat(itemsList).isEmpty()
        verify { mockFileOperations.fileExists(any()) }
        verify { mockFileOperations.readFromFile(any()) }
    }

    @Test
    fun `when a file with data exists, loadItems returns a list of shopping items`() = runTest {
        //given
        val fileContents = """[ {
  "id" : 1,
  "name" : "Duck",
  "quantity" : 1,
  "category" : "",
  "isChecked" : false
}, {
  "id" : 2,
  "name" : "Goose",
  "quantity" : 1,
  "category" : "",
  "isChecked" : true
}, {
  "id" : 69,
  "name" : "Icecream Sundae",
  "quantity" : 3,
  "category" : "",
  "isChecked" : false
} ]"""
        every { mockFileOperations.fileExists(any()) } returns true
        every { mockFileOperations.readFromFile(any()) } returns fileContents

        //when
        val itemsList = repository.loadItems()

        //then
        assertThat(itemsList).isNotEmpty()
        verify { mockFileOperations.fileExists(any()) }
        verify { mockFileOperations.readFromFile(any()) }
    }

    @Test
    fun `loadItems does not throw an exception when read file fails`() = runTest {
        //given
        every { mockFileOperations.fileExists(any()) } returns true
        every { mockFileOperations.writeToFile(any(), any()) } throws IOException("Write failure")

        //when
        val result = runCatching { repository.loadItems() }

        //then
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEmpty()
        verify { mockFileOperations.fileExists(any()) }
        verify { mockFileOperations.readFromFile(any()) }
    }
}