package com.lukafenir.luciuslist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lukafenir.luciuslist.model.ShoppingListViewModel
import com.lukafenir.luciuslist.ui.theme.ShoppingListTheme

@Composable
fun ShoppingListScreen(
    shoppingListViewModel: ShoppingListViewModel
) {
    if(shoppingListViewModel.items.isEmpty()){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Your shopping list is empty. Tap + to add items.")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(shoppingListViewModel.items) { item ->
                ShoppingListRow(item)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListScreenPreview() {
    var viewModel = ShoppingListViewModel()
    viewModel.addItem("Cheese")
    viewModel.addItem("Wholewheat Pasta")
    viewModel.addItem("Bleach")
    ShoppingListTheme {
        ShoppingListScreen(viewModel)
    }
}