package com.lukafenir.luciuslist.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lukafenir.luciuslist.model.ShoppingListViewModel
import com.lukafenir.luciuslist.ui.theme.ShoppingListTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShoppingListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun emptyShoppingList_displaysEmptyStateMessage() {
        //given
        var viewModel = ShoppingListViewModel()

        // When
        composeTestRule.setContent {
            ShoppingListTheme {
                ShoppingListScreen(viewModel)
            }
        }

        // Then
        composeTestRule.onNodeWithText("Your shopping list is empty. Tap + to add items.").assertIsDisplayed()
    }

    @Test
    fun populatedShoppingList_isDisplayedInTheListScreen() {
        //given
        var viewModel = ShoppingListViewModel();
        viewModel.addItem("Banana")
        viewModel.addItem("Wholewheat Flour")
        viewModel.addItem("Bleach")

        //when
        composeTestRule.setContent {
            ShoppingListTheme {
                ShoppingListScreen(viewModel)
            }
        }

        //then
        composeTestRule.onNodeWithText("Wholewheat Flour").assertIsDisplayed()
        composeTestRule.onNodeWithText("Banana").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bleach").assertIsDisplayed()
        composeTestRule.onNodeWithText("Your shopping list is empty. Tap + to add items.").assertIsNotDisplayed()
    }
}