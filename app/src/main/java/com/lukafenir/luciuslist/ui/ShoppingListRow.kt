package com.lukafenir.luciuslist.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lukafenir.luciuslist.model.ShoppingItem
import com.lukafenir.luciuslist.ui.theme.ShoppingListTheme

@Composable
fun ShoppingListRow(
    item: ShoppingItem,
    onDelete: (ShoppingItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 2.dp)
            .testTag("shopping_list_row_${item.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
//                onToggleChecked
            }) {
                Icon(
//                    imageVector = if (item.isChecked) Icons.Default.Check else Icons.Default.Check,
                    imageVector = Icons.Default.Check,
                    contentDescription = "Check Item",
//                    tint = if (item.isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            } //TODO test ShoppingListRow

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = TextDecoration.None
                )
                if (item.category.isNotEmpty()) {
                    Text(
                        text = item.category,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
//                        if (item.quantity > 1) {
//                            onQuantityChange(item.quantity - 1)
//                        }
                    }
                ) {
                    Text("-")
                }

                Text(
                    text = "${item.quantity}",
                    modifier = Modifier.padding(horizontal = 8.dp)
                        .testTag("quantity_text_${item.id}"),
                )

                IconButton(
                    onClick = {
//                        onQuantityChange(item.quantity + 1)
                    }
                ) {
                    Text("+")
                }

                IconButton(onClick = {
                    onDelete(item)
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Item"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListRowPreview(){
    ShoppingListTheme {
        ShoppingListRow(
            ShoppingItem(1, "Cheese"),
            onDelete = { Log.d("ShoppingListRow", "Pressed delete button") } // Can't delete itself, only a preview
        )
    }
}