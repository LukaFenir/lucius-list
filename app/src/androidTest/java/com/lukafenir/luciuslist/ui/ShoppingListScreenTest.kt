package com.lukafenir.luciuslist.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
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

        // When
        composeTestRule.setContent {
            ShoppingListTheme {
                ShoppingListScreen()
            }
        }

        // Then
        composeTestRule.onNodeWithText("Your shopping list is empty. Tap + to add items.").assertIsDisplayed()
    }
}