package com.lukafenir.luciuslist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lukafenir.luciuslist.model.FakeShoppingListRepository
import com.lukafenir.luciuslist.model.ShoppingItem
import com.lukafenir.luciuslist.model.ShoppingListViewModel
import com.lukafenir.luciuslist.ui.theme.ShoppingListTheme

@Composable
fun ShoppingListScreen(
    shoppingListViewModel: ShoppingListViewModel
) {
    val addItemFunction: (String) -> Unit = { newItem -> shoppingListViewModel.addItem(newItem) }
    val deleteItemFunction: (ShoppingItem) -> Unit = { item -> shoppingListViewModel.deleteItem(item) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        if(shoppingListViewModel.items.isEmpty()) {
            item {
                ShoppingListAddButton(
                    onSubmit = addItemFunction
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Your shopping list is empty. Tap + to add items.")
                }
            }
        } else {
            items(shoppingListViewModel.items) { item ->
                ShoppingListRow(
                    item,
                    onDelete = deleteItemFunction
                )
            }
            item {
                ShoppingListAddButton(
                    onSubmit = addItemFunction
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListScreenPreview() {
    val testItems = listOf(
        ShoppingItem(1, "Cheese", 1),
        ShoppingItem(2, "Wholewheat Flour", 1),
        ShoppingItem(3, "Bleach", 1))
    ShoppingListTheme {
        ShoppingListScreen(ShoppingListViewModel(FakeShoppingListRepository(testItems)))
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListScreenEmptyPreview() {
    ShoppingListTheme {
        ShoppingListScreen(ShoppingListViewModel(FakeShoppingListRepository()))
    }
}