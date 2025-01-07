package com.example.wastelessapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wastelessapp.R
import com.example.wastelessapp.database.entities.product.ProductEvent
import com.example.wastelessapp.database.entities.product.ProductState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    sheetState: SheetState,
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    state: ProductState,
    onEvent: (ProductEvent) -> Unit

) {
    if (isOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                onDismissRequest()
                onEvent(ProductEvent.HideDialog)
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 16.dp)
            ) {
                Text(
                    "Add New Product",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start,
                )
                Spacer(modifier = Modifier.height(24.dp))


                TextField(
                    value = state.name,
                    onValueChange = { onEvent(ProductEvent.SetName(it)) },
                    label = { Text("Product Name") },
                    placeholder = { Text("Enter product name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Select Icon",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start,
                )


                val icons = listOf(
                    R.drawable.apple_fruits_icon,
                    R.drawable.banana_fruit_icon,
                    R.drawable.cherry_fruit_icon,
                    R.drawable.orange_lemon_icon,
                    R.drawable.peanut_icon,
                    R.drawable.pear_papaya_icon,
                    R.drawable.pineapple_icon,
                    R.drawable.pomegranate_icon,
                    R.drawable.grapes_icon,
                    R.drawable.watermelon_icon,
                    R.drawable.strawberry_icon,

                    R.drawable.carrot_vegetable_icon,
                    R.drawable.corn_vegetable_icon,
                    R.drawable.cucumber_vegetable_icon,
                    R.drawable.eggplant_vegetable_icon,
                    R.drawable.garlic_vegetable_icon,
                    R.drawable.tomato_vegetable_icon,
                    R.drawable.mushroom_icon,
                    R.drawable.pumpkin_black_icon,

                    R.drawable.chicken_leg_icon,
                    R.drawable.chicken_rooster_icon,
                    R.drawable.crab_icon,
                    R.drawable.fish_icon,
                    R.drawable.steak_icon,

                    R.drawable.cheese_icon,
                    R.drawable.egg_icon,
                    R.drawable.milk_bottle_icon,

                    R.drawable.bread_icon,
                    R.drawable.grain_wheat_icon,
                    R.drawable.pretzel_icon,

                    R.drawable.bone_dog_icon,
                    R.drawable.chocolate_icon,
                    R.drawable.cookies_icon,
                    R.drawable.donut_icon,
                    R.drawable.popcorn_icon,
                    R.drawable.snacks_icon,

                    R.drawable.cola_drink_plastic_icon,
                    R.drawable.plastic_takeaway_coffee_icon,

                    R.drawable.burger_icon,
                    R.drawable.pizza_food_icon,
                    R.drawable.sandwich_icon,
                    R.drawable.sushi_icon,
                    R.drawable.taco_icon,
                    R.drawable.food_icon,

                    R.drawable.cake_cup_icon,
                    R.drawable.wedding_cake_icon,
                    R.drawable.pie_dish_food_icon,

                    R.drawable.bowl_and_spoon_icon,
                    R.drawable.dish_cap_icon,
                    R.drawable.dish_spoon_knife_icon,
                    R.drawable.food_restaurant_icon,
                    R.drawable.salt_seasoning_icon
                )

                ScrollableIconRow(
                    icons = icons,
                    onIconSelected = {
                        onEvent(ProductEvent.SetIconResId(it))
                    },
                    selectedIcon = state.iconResId
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PrimaryButton(
                        text = "Add Product",
                        onClick = {
                            onEvent(ProductEvent.SaveProduct)
                            onDismissRequest()
                        },
                        width = 220.dp
                    )
                }


            }
        }
    }
}


@Composable
fun ScrollableIconRow(
    icons: List<Int>,
    onIconSelected: (Int) -> Unit,
    selectedIcon: Int,
    iconSize: Dp = 48.dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        icons.forEach { iconResId ->
            IconButton(
                onClick = { onIconSelected(iconResId) },
                modifier = Modifier
                    .size(iconSize)
                    .background(
                        color = if (iconResId == selectedIcon) MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.3f
                        ) else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = if (iconResId == selectedIcon) 2.dp else 1.dp,
                        color = if (iconResId == selectedIcon) MaterialTheme.colorScheme.primary else Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}