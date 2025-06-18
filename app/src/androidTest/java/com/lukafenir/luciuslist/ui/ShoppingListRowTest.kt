package com.lukafenir.luciuslist.ui

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lukafenir.luciuslist.model.ShoppingItem
import com.lukafenir.luciuslist.ui.theme.ShoppingListTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShoppingListRowTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun aShoppingItem_isDisplayedInTheShoppingListRow(){
        composeTestRule.setContent {
            ShoppingListTheme {
                ShoppingListRow(
                    ShoppingItem(1, "Eggs", 6),
                    onDelete = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Check Item").isDisplayed()
        composeTestRule.onNodeWithText("Eggs").isDisplayed()
        composeTestRule.onNodeWithText("-").isDisplayed()
        composeTestRule.onNodeWithTag("quantity_text_1").assertTextEquals("6")
        composeTestRule.onNodeWithText("+").isDisplayed()
        composeTestRule.onNodeWithContentDescription("Delete Item").isDisplayed()
    }
}