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
        var viewModel = ShoppingListViewModel()

        //when
        viewModel.addItem("Cheese")

        assertThat(viewModel.items).hasSize(1)
        assertThat(viewModel.items[0]).isEqualTo(ShoppingItem(1, "Cheese", 1, "", false))
    }

    @Test
    fun `addItem multiple times adds multiple items to the view model`(){
        var viewModel = ShoppingListViewModel()

        //when
        viewModel.addItem("Cheese")
        viewModel.addItem("Banana")
        viewModel.addItem("Whole Wheat")

        assertThat(viewModel.items).hasSize(3)
        assertThat(viewModel.items[0]).isEqualTo(ShoppingItem(1, "Cheese", 1, "", false))
        assertThat(viewModel.items[1]).isEqualTo(ShoppingItem(2, "Banana", 1, "", false))
        assertThat(viewModel.items[2]).isEqualTo(ShoppingItem(3, "Whole Wheat", 1, "", false))
    }
}