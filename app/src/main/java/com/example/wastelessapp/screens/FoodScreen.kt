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
import com.example.wastelessapp.ui.components.FoodInventoryItem
import com.example.wastelessapp.ui.components.FoodItem
import com.example.wastelessapp.ui.components.FoodUnit
import com.example.wastelessapp.ui.components.PrimaryButton
import com.example.wastelessapp.ui.components.SecondaryButton
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

@Serializable
object FoodScreen


fun getRandomExpiryDate(): LocalDateTime {
    // random date for testing purposes
    // gonna be removed in production
    val now = LocalDateTime.now()
    val randomDays = Random.nextInt(-3, 4)
    return now.plus(randomDays.toLong(), ChronoUnit.DAYS)
}

val sortWidthButton = 150.dp

@Preview
@Composable
fun FoodInventoryScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,


        )
    {
        CustomTopAppBar(pageName = "Food Inventory")
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            SecondaryButton(
                text = "Sort by Category",
                onClick = { /*TODO*/ },
                width = sortWidthButton
            )
            Spacer(modifier = Modifier.width(horizontalPaddingBetweenButtons))
            SecondaryButton(
                text = "Sort by Expiration",
                onClick = { /*TODO*/ },
                width = sortWidthButton
            )

        }


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(10.dp)

        ) {
            items(50) { item ->
                FoodInventoryItem(
                    item =
                    FoodItem(
                        id = item,
                        name = "apple",
                        quantity = 500,
                        unit = FoodUnit.GRAM,
                        price = 1.0,
                        expiryDate = getRandomExpiryDate(),
                        purchaseDate = LocalDateTime.now()
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
                text = "Add Item",
                onClick = { /*TODO*/ },
                width = 200.dp,
            )

        }


        Spacer(modifier = Modifier.height(16.dp))


    }
}
