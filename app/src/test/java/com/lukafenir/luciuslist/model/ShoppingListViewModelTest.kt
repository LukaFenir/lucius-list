package com.lukafenir.luciuslist.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ShoppingListViewModelTest {

    @Test
    fun `View Model initial state is empty`(){
        assertThat(ShoppingListViewModel().items).isEmpty()
    }

    @Test
    fun `addItem adds an item to the empty view model`(){
        //given
        var viewModel = ShoppingListViewModel()

        //when
        viewModel.addItem("Cheese")

        //then
        assertThat(viewModel.items).hasSize(1)
        assertThat(viewModel.items[0]).isEqualTo(ShoppingItem(1, "Cheese", 1, "", false))
    }

    @Test
    fun `addItem multiple times adds multiple items to the view model`(){
        //given
        var viewModel = ShoppingListViewModel()

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
        var viewModel = ShoppingListViewModel()
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
        var viewModel = ShoppingListViewModel()
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