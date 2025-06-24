package com.lukafenir.luciuslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lukafenir.luciuslist.model.DefaultShoppingListRepository
import com.lukafenir.luciuslist.model.ShoppingListViewModel
import com.lukafenir.luciuslist.model.ShoppingListViewModelFactory
import com.lukafenir.luciuslist.ui.ShoppingListScreen
import com.lukafenir.luciuslist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {
    private lateinit var shoppingListViewModel: ShoppingListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingListTheme(true) {
                Surface(
                    modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val repository = remember { DefaultShoppingListRepository(this@MainActivity) }
                    val viewModelFactory = remember { ShoppingListViewModelFactory(repository) }

                    shoppingListViewModel = viewModel(factory = viewModelFactory)

                    ShoppingListScreen(shoppingListViewModel)
                }
            }
        }
    }
}