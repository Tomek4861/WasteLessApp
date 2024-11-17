package com.example.wastelessapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wastelessapp.ui.components.CustomTopAppBar
import com.example.wastelessapp.ui.components.FoodUnit
import com.example.wastelessapp.ui.components.PrimaryButton
import com.example.wastelessapp.ui.components.SecondaryButton
import com.example.wastelessapp.ui.components.ShoppingItem
import com.example.wastelessapp.ui.components.ShoppingListItem
import kotlinx.serialization.Serializable

@Serializable
object ShoppingListScreen


val horizontalPaddingBetweenButtons = 32.dp
val buttonWidth = 150.dp

@Preview // allows to preview without compiling the app
@Composable
fun ShoppingListScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,


        )
    {
        CustomTopAppBar(pageName = "Shopping List")
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            SecondaryButton(
                text = "Sort by Type",
                onClick = { /*TODO*/ },
                width = buttonWidth
            )
            Spacer(modifier = Modifier.width(horizontalPaddingBetweenButtons))
            SecondaryButton(
                text = "Sort A-Z",
                onClick = { /*TODO*/ },
                width = buttonWidth
            )

        }


        LazyColumn(
//            contentPadding = PaddingValues(bottom = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(10.dp)

        ) {
            items(50) { item ->
                ShoppingListItem(
                    item =
                    ShoppingItem(
                        id = item,
                        name = "apple",
                        quantity = 5,
                        unit = FoodUnit.PCS
                    )
                )

            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            PrimaryButton(
                text = "Clear List",
                onClick = { /*TODO*/ },
                width = buttonWidth
            )
            Spacer(modifier = Modifier.width(horizontalPaddingBetweenButtons))
            PrimaryButton(
                text = "Add Item",
                onClick = { /*TODO*/ },
                width = buttonWidth
            )

        }


        Spacer(modifier = Modifier.height(16.dp))


    }
}