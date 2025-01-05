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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wastelessapp.ui.components.CustomTopAppBar
import com.example.wastelessapp.ui.components.CustomDropdownMenu
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

//private val showAddInventoryItemScreen = mutableStateOf(false)

@Composable
fun FoodInventoryScreen(navController: NavHostController) {

//    if(showAddInventoryItemScreen.value){
//        AddInventoryItemScreen()
//    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,


        )
    {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp)
            ,
        ){
            Text(
                "Sort By:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 3.dp)

                )
            CustomDropdownMenu(listOf("Expiration", "Alphabetical",), { /*TODO*/ })

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
                onClick = {
                    //showAddInventoryItemScreen.value = true
                    navController.navigate(AddInventoryItemScreen)
                },
                width = 200.dp,
            )

        }


        Spacer(modifier = Modifier.height(16.dp))


    }
}
