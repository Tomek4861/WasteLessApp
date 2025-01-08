package com.example.wastelessapp.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemEvent
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemViewModel
import com.example.wastelessapp.database.entities.inventory_item.SortType
import com.example.wastelessapp.ui.components.CustomDropdownMenu
import com.example.wastelessapp.ui.components.FoodInventoryItem
import com.example.wastelessapp.ui.components.FoodItem
import com.example.wastelessapp.ui.components.PrimaryButton
import convertToLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
object FoodScreen


//private val showAddInventoryItemScreen = mutableStateOf(false)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FoodInventoryScreen(
    navController: NavHostController,
    inventoryItemViewModel: InventoryItemViewModel
) {
    val state by inventoryItemViewModel.state.collectAsState()

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
                .padding(horizontal = 12.dp, vertical = 4.dp),
        ) {
            Text(
                "Sort By:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 3.dp)

            )
            CustomDropdownMenu(
                SortType.entries.toTypedArray(),
                { inventoryItemViewModel.onEvent(InventoryItemEvent.SortProducts(it)) })
        }

        AnimatedContent(
            label = "",
            targetState = state.inventoryItems,
            transitionSpec = {
                fadeIn() + slideInHorizontally() with fadeOut() + slideOutHorizontally()
            }
        ) { animatedItems ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
                    .weight(1f)
                    .padding(10.dp)
            ) {
                items(animatedItems) { item ->
                    FoodInventoryItem(
                        item =
                        FoodItem(
                            id = item.id,
                            name = item.product,
                            quantity = item.amount,
                            unit = item.itemUnit,
                            price = item.price,
                            expiryDate = convertToLocalDateTime(item.expirationDate),
                            purchaseDate = convertToLocalDateTime(item.dateAdded),
                            iconId = item.iconResId,
                        ),
                        onCheck = {
                            inventoryItemViewModel.onEvent(
                                InventoryItemEvent.UpdateItemState(
                                    item
                                )
                            )
                        },
                        onDelete = {
                            inventoryItemViewModel.onEvent(
                                InventoryItemEvent.DeleteInventoryItem(
                                    item
                                )
                            )
                        }
                    )

                }
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
