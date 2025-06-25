package com.lukafenir.luciuslist.model

import com.lukafenir.luciuslist.io.NoOpLogger
import com.lukafenir.luciuslist.persistence.ShoppingListRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ShoppingListViewModelTest {

    @MockK
    private lateinit var mockRepository: ShoppingListRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { mockRepository.loadItems() } returns emptyList()
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        coEvery { mockRepository.loadItems() } returns emptyList()
    }

    @Test
    fun `View Model initial state is empty`(){
        //given
        coEvery { mockRepository.loadItems() } returns emptyList()

        //when
        val viewModel = ShoppingListViewModel(mockRepository, NoOpLogger())

        //then
        assertThat(viewModel.items).isEmpty()
        coVerify { mockRepository.loadItems() }
    }

    @Test
    fun `If repository contains items, View Model initialising with items`(){
        val itemsList = listOf(
            ShoppingItem(1, "An Egg", 1),
            ShoppingItem(2, "Bananas", 3),
            ShoppingItem(3, "Cakery", 1)
        )
        coEvery { mockRepository.loadItems() } returns itemsList

        //when
        val viewModel = ShoppingListViewModel(mockRepository, NoOpLogger())

        assertThat(viewModel.items).hasSize(3)
        coVerify { mockRepository.loadItems() }
    }

    @Test
    fun `addItem adds an item to the empty view model`() {
        //given
        val viewModel = ShoppingListViewModel(mockRepository, NoOpLogger())

        //when
        viewModel.addItem("Cheese")

        //then
        assertThat(viewModel.items).hasSize(1)
        assertThat(viewModel.items[0]).isEqualTo(ShoppingItem(1, "Cheese", 1, "", false))
    }

    @Test
    fun `addItem multiple times adds multiple items to the view model`(){
        //given
        val viewModel = ShoppingListViewModel(mockRepository, NoOpLogger())

        //when
        viewModel.addItem("Cheese")
        viewModel.addItem("Banana")
        viewModel.addItem("Whole Wheat")

        //then
        assertThat(viewModel.items).hasSize(3)
        assertThat(viewModel.items[0]).isEqualTo(ShoppingItem(1, "Cheese", 1, "", false))
        assertThat(viewModel.items[1]).isEqualTo(ShoppingItem(2, "Banana", 1, "", false))
        assertThat(viewModel.items[2]).isEqualTo(ShoppingItem(3, "Whole Wheat", 1, "", false))
    }

    @Test
    fun `deleteItem on a list with one item, deletes the item, leaving an empty list`(){
        //given
        val viewModel = ShoppingListViewModel(mockRepository, NoOpLogger())
        viewModel.addItem("Cake")
        assertThat(viewModel.items).hasSize(1)
        var itemToDelete: ShoppingItem = viewModel.items[0]

        //when
        viewModel.deleteItem(itemToDelete)

        //then
        assertThat(viewModel.items).hasSize(0)
    }

    @Test
    fun `deleteItem on a list deletes the item`(){
        //given
        val viewModel = ShoppingListViewModel(mockRepository, NoOpLogger())
        viewModel.addItem("Cake")
        viewModel.addItem("Dog Food")
        viewModel.addItem("Batteries")
        var itemToDelete: ShoppingItem = viewModel.items[0]

        //when
        viewModel.deleteItem(itemToDelete)

        //then
        assertThat(viewModel.items).hasSize(2)
        assertThat(viewModel.items[0]).isEqualTo(ShoppingItem(2, "Dog Food", 1, "", false))
        assertThat(viewModel.items[1]).isEqualTo(ShoppingItem(3, "Batteries", 1, "", false))
    }
}