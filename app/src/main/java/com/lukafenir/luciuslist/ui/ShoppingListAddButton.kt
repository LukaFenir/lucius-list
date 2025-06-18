package com.lukafenir.luciuslist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lukafenir.luciuslist.ui.theme.ShoppingListTheme
import kotlinx.coroutines.delay

@Composable
fun ShoppingListAddButton(
    onSubmit: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // State variables
    var isInputMode by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }

    // Focus requester to automatically focus the text field
    val focusRequester = remember { FocusRequester() }

    // Keyboard controller to hide keyboard when needed
    val keyboardController = LocalSoftwareKeyboardController.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { isInputMode = true },
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Conditional rendering based on mode
            if (!isInputMode) {
                // Show button when not in input mode
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Add new item")
            } else {
                // Show text field when in input mode
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Enter text") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // When Enter/Done is pressed
                            if (inputText.isNotBlank()) {
                                onSubmit(inputText)
                                inputText = ""
                                isInputMode = false
                                keyboardController?.hide()
                            }
                        }
                    ),
                    singleLine = true
                )

                // Focus the text field when it appears
                LaunchedEffect(isInputMode) {
                    if (isInputMode) {
                        focusRequester.requestFocus()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ShoppingListAddButtonPreview(){
    val items = remember { mutableStateOf(listOf<String>()) }

    ShoppingListTheme {
        ShoppingListAddButton(
            onSubmit = { newItem ->
                items.value += newItem
            })
    }

}