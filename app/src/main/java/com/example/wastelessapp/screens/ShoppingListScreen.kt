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
import androidx.compose.foundation.layout.width
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
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemViewModel
import com.example.wastelessapp.database.entities.product.ProductViewModel
import com.example.wastelessapp.database.entities.shopping_cart.ShoppingCartEvent
import com.example.wastelessapp.database.entities.shopping_cart.ShoppingCartSortType
import com.example.wastelessapp.database.entities.shopping_cart.ShoppingCartViewModel
import com.example.wastelessapp.ui.components.CustomDropdownMenu
import com.example.wastelessapp.ui.components.PrimaryButton
import com.example.wastelessapp.ui.components.ShoppingItem
import com.example.wastelessapp.ui.components.ShoppingListItem
import kotlinx.serialization.Serializable

@Serializable
object ShoppingListScreen


val horizontalPaddingBetweenButtons = 32.dp
val buttonWidth = 150.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShoppingListScreen(
    navController: NavHostController,
    shoppingCartViewModel: ShoppingCartViewModel,
    inventoryItemViewModel: InventoryItemViewModel,
    productViewModel: ProductViewModel
) {
    val shoppingCartState by shoppingCartViewModel.state.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        )
    {
        if (shoppingCartState.isAddingItem) {
            AddShoppingListItemScreen(navController, shoppingCartViewModel, productViewModel)
        }

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
                ShoppingCartSortType.entries.toTypedArray(),
                { shoppingCartViewModel.onEvent(ShoppingCartEvent.SortProducts(it)) })


        }

        AnimatedContent(
            label = "",
            targetState = shoppingCartState.shoppingCartItems,
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
                    ShoppingListItem(
                        item =
                        ShoppingItem(
                            id = item.id,
                            name = item.product,
                            quantity = item.amount,
                            unit = item.itemUnit,
                            iconId = item.iconResId,
                        ),
                        onCheck = {
                            it.isCheckedState = !it.isCheckedState
                            println("Item checked: ${it.isCheckedState}")


                        },
                        onDelete = {
                            shoppingCartViewModel.onEvent(
                                ShoppingCartEvent.DeleteShoppingCartItem(
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
                text = "Clear List",
                onClick = {
                    shoppingCartViewModel.onEvent(ShoppingCartEvent.DeleteAllShoppingCartItems)
                },
                width = buttonWidth
            )
            Spacer(modifier = Modifier.width(horizontalPaddingBetweenButtons))
            PrimaryButton(
                text = "Add Item",
                onClick = {
                    shoppingCartViewModel.onEvent(
                        ShoppingCartEvent.ShowDialog
                    )
                },
                width = buttonWidth,
            )

        }


        Spacer(modifier = Modifier.height(16.dp))


    }
}