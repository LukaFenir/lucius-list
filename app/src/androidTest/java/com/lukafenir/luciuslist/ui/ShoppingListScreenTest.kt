package com.lukafenir.luciuslist.ui

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lukafenir.luciuslist.model.ShoppingItem
import com.lukafenir.luciuslist.model.ShoppingListViewModel
import com.lukafenir.luciuslist.onAllNodesWithTagPattern
import com.lukafenir.luciuslist.persistence.FakeShoppingListRepository
import com.lukafenir.luciuslist.persistence.ShoppingListRepository
import com.lukafenir.luciuslist.ui.theme.ShoppingListTheme
import kotlinx.coroutines.delay
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
        var viewModel = ShoppingListViewModel(FakeShoppingListRepository())

        //when
        composeTestRule.setContent {
            ShoppingListTheme {
                ShoppingListScreen(viewModel)
            }
        }

        //then
        composeTestRule.onNodeWithText("Your shopping list is empty. Tap + to add items.").assertIsDisplayed()
    }

    @Test
    fun emptyStateMessage_notDisplayedWhileLoading() {
        // Given
        val slowRepository = FakeSlowShoppingListRepository()
        val viewModel = ShoppingListViewModel(slowRepository)

        // When
        composeTestRule.setContent {
            ShoppingListTheme {
                ShoppingListScreen(viewModel)
            }
        }

        // Then - empty message should not be displayed while loading
        composeTestRule.onNodeWithText("Your shopping list is empty. Tap + to add items.")
            .assertDoesNotExist()

        // Wait for loading to complete
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            !viewModel.isLoading.value
        }

        // Then - empty message should appear after loading completes
        composeTestRule.onNodeWithText("Your shopping list is empty. Tap + to add items.")
            .assertIsDisplayed()
    }

    @Test
    fun populatedShoppingList_isDisplayedInTheListScreen() {
        //given
        val testItems = listOf(
            ShoppingItem(1, "Banana", 1),
            ShoppingItem(2, "Wholewheat Flour", 1),
            ShoppingItem(3, "Bleach", 1))
        val viewModel = ShoppingListViewModel(FakeShoppingListRepository(testItems));

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

    @Test
    fun addingAnItemToAnEmptyList_displaysTheNewItemOnTheListScreen() {
        //given
        val viewModel = ShoppingListViewModel(FakeShoppingListRepository());

        composeTestRule.setContent {
            ShoppingListTheme {
                ShoppingListScreen(viewModel)
            }
        }
        composeTestRule.onNodeWithText("Your shopping list is empty. Tap + to add items.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add new item").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Item").assertIsNotDisplayed()

        //when
        composeTestRule.onNodeWithContentDescription("Add").performClick()
        composeTestRule.onNode(hasSetTextAction()).performTextInput("Test Item")
        composeTestRule.onNode(hasSetTextAction()).performImeAction()

        //then
        composeTestRule.onNodeWithText("Test Item").assertIsDisplayed()
        composeTestRule.onNodeWithText("Your shopping list is empty. Tap + to add items.").assertIsNotDisplayed()
    }

    @Test
    fun addingBlankItemToAnEmptyList_displaysEmptyListMessage() {
        //given
        val viewModel = ShoppingListViewModel(FakeShoppingListRepository());

        composeTestRule.setContent {
            ShoppingListTheme {
                ShoppingListScreen(viewModel)
            }
        }
        composeTestRule.onNodeWithText("Your shopping list is empty. Tap + to add items.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add new item").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Item").assertIsNotDisplayed()

        //when
        composeTestRule.onNodeWithContentDescription("Add").performClick()
        composeTestRule.onNode(hasSetTextAction()).performTextInput("     ")
        composeTestRule.onNode(hasSetTextAction()).performImeAction()

        //then
        composeTestRule.onNodeWithText("Your shopping list is empty. Tap + to add items.").assertIsDisplayed()
        composeTestRule.onNode(hasContentDescription("Check Item")).isNotDisplayed()
    }

    @Test
    fun addingAnItemToAnAlreadyPopulatedList_displaysTheNewItemAfterTheOlderItems() {
        //given
        val testItems = listOf(
            ShoppingItem(1, "Banana", 1),
            ShoppingItem(2, "Wholewheat Flour", 1),
            ShoppingItem(3, "Bleach", 1))
        val viewModel = ShoppingListViewModel(FakeShoppingListRepository(testItems));

        composeTestRule.setContent {
            ShoppingListTheme {
                ShoppingListScreen(viewModel)
            }
        }
        composeTestRule.onNodeWithText("Add new item").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Item").assertIsNotDisplayed()

        //when
        composeTestRule.onNodeWithContentDescription("Add").performClick()
        composeTestRule.onNode(hasSetTextAction()).performTextInput("Test Item")
        composeTestRule.onNode(hasSetTextAction()).performImeAction()

        //then
        composeTestRule.onNodeWithText("Banana").assertIsDisplayed()
        composeTestRule.onNodeWithText("Wholewheat Flour").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bleach").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Item").assertIsDisplayed()
    }

    @Test
    fun addingBlankItemToAnAlreadyPopulatedList_displaysEmptyListMessage() {
        //given
        val testItems = listOf(
            ShoppingItem(1, "Banana", 1),
            ShoppingItem(2, "Wholewheat Flour", 1),
            ShoppingItem(3, "Bleach", 1))
        val viewModel = ShoppingListViewModel(FakeShoppingListRepository(testItems));

        composeTestRule.setContent {
            ShoppingListTheme {
                ShoppingListScreen(viewModel)
            }
        }

        //when
        composeTestRule.onNodeWithContentDescription("Add").performClick()
        composeTestRule.onNode(hasSetTextAction()).performTextInput("     ")
        composeTestRule.onNode(hasSetTextAction()).performImeAction()

        //then
        composeTestRule.onAllNodesWithTagPattern("shopping_list_row_.*").assertCountEquals(3)
    }

    @Test
    fun anItemDeletedFromTheList_isNotDisplayedInTheListScreen() {
        //given
        val testItems = listOf(
            ShoppingItem(1, "Banana", 1),
            ShoppingItem(2, "Wholewheat Flour", 1),
            ShoppingItem(3, "Bleach", 1))
        val viewModel = ShoppingListViewModel(FakeShoppingListRepository(testItems));

        composeTestRule.setContent {
            ShoppingListTheme {
                ShoppingListScreen(viewModel)
            }
        }
        composeTestRule.onNodeWithText("Banana").assertIsDisplayed()
        composeTestRule.onNodeWithText("Wholewheat Flour").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bleach").assertIsDisplayed()

        //when
        composeTestRule.onNode(hasContentDescription("Delete Item") and hasAnySibling(hasText("Wholewheat Flour"))).performClick()

        //then
        composeTestRule.onNodeWithText("Banana").assertIsDisplayed()
        composeTestRule.onNodeWithText("Wholewheat Flour").assertIsNotDisplayed()
        composeTestRule.onNodeWithText("Bleach").assertIsDisplayed()
    }

    @Test
    fun whenTheLastItemIsDeletedFromTheList_theListIsEmptyMessageIsDisplayed() {
        //given
        val testItems = listOf(
            ShoppingItem(1, "Cranberries", 1))
        val viewModel = ShoppingListViewModel(FakeShoppingListRepository(testItems));

        composeTestRule.setContent {
            ShoppingListTheme {
                ShoppingListScreen(viewModel)
            }
        }
        composeTestRule.onNodeWithText("Cranberries").assertIsDisplayed()
        composeTestRule.onNodeWithText("Your shopping list is empty. Tap + to add items.").assertIsNotDisplayed()

        //when
        composeTestRule.onNode(hasContentDescription("Delete Item") and hasAnySibling(hasText("Cranberries"))).performClick()

        //then
        composeTestRule.onNodeWithText("Cranberries").assertIsNotDisplayed()
        composeTestRule.onNodeWithText("Your shopping list is empty. Tap + to add items.").assertIsDisplayed()
    }

    class FakeSlowShoppingListRepository : ShoppingListRepository {
        private var items = mutableListOf<ShoppingItem>()

        override fun saveItems(items: List<ShoppingItem>) {
            this.items.clear()
            this.items.addAll(items)
        }

        override suspend fun loadItems(): List<ShoppingItem> {
            // Simulate network delay
            delay(1000)
            return items.toList()
        }
    }
}