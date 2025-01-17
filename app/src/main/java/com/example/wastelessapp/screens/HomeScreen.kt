package com.example.wastelessapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wastelessapp.R
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemEvent
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemViewModel
import com.example.wastelessapp.database.entities.inventory_item.ItemState
import com.example.wastelessapp.database.entities.product.ProductViewModel
import com.example.wastelessapp.ui.components.PrimaryButton
import com.example.wastelessapp.ui.components.SecondaryButton
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random

@Serializable
object HomeScreen

@Composable
fun HomeScreen(
    inventoryItemViewModel: InventoryItemViewModel,
    productViewModel: ProductViewModel,
    navController: NavHostController
) {
    val state by inventoryItemViewModel.state.collectAsState()

    val savedIn30Days = kotlinx.coroutines.runBlocking {
        inventoryItemViewModel.countItemsInLast30DaysByState(ItemState.SAVED)
    }
    val lostIn30Days = kotlinx.coroutines.runBlocking {
        inventoryItemViewModel.countItemsInLast30DaysByState(ItemState.EXPIRED)
    }
    val activeProducts = kotlinx.coroutines.runBlocking {
        inventoryItemViewModel.countAllItemsByState(ItemState.ACTIVE)
    }
    val expiringSoonItems = kotlinx.coroutines.runBlocking {
        inventoryItemViewModel.getItemsExpiringSoon()
    }
    val expiredActiveItems = kotlinx.coroutines.runBlocking {
        inventoryItemViewModel.getExpiredActiveItems()
    }
    val tips = listOf(
        "Organize your fridge by grouping similar items together and keeping frequently used foods in front. This way, you’ll avoid forgetting about items in the back and create an efficient system that helps reduce waste and saves money over time.",
        "Cooking meals in batches not only saves time but also minimizes waste. Prepare larger portions, store them in reusable containers, and enjoy fresh, homemade meals throughout the week without the hassle of cooking every day!",
        "Repurpose your food scraps creatively—use vegetable peels for broth, stale bread for croutons or breadcrumbs, and overripe fruits for smoothies or baking. Small creative steps like these can reduce waste and add flavor to your meals!",
        "Avoid impulse buys by planning your shopping trips carefully. Stick to a well-prepared list and resist the urge to overstock your pantry. This keeps your budget under control, ensures you use what you already have, and prevents unnecessary waste.",
        "Track what’s already in your fridge and pantry before making a shopping list. By knowing what you have, you’ll avoid buying duplicates, keep your food rotation system effective, and make sure nothing goes unused or forgotten.",
        "Smart storage makes a big difference! Store fruits and vegetables separately, and keep items like herbs in water or wrapped in damp paper towels. Proper storage extends freshness and ensures nothing goes to waste unnecessarily.",
        "Be mindful of portion sizes while cooking. Preparing just enough ensures everyone is satisfied without leaving excess food behind. If there are leftovers, store them properly for the next meal to enjoy without letting them go to waste.",
        "To avoid food waste, plan meals for the week using ingredients you already have before shopping. This helps ensure nothing is wasted, and you only buy what you need!",
        "Use labels and markers to write the date of purchase or preparation on containers and leftovers. This helps you track freshness and prioritize eating items before they expire.",
        "Organize your fridge and pantry with the FIFO (First In, First Out) method: move older items to the front and new ones to the back. This way, you’ll use up older products first, reducing the chance of spoilage.",
        "If you’ve made too much or can’t finish certain items, freeze them in portioned containers. Freezing preserves food longer and allows you to enjoy it later without waste.",
        "Choose 'imperfect' fruits and vegetables at the store or market. These items taste the same but often go unsold, contributing to waste. Plus, they’re often cheaper!",
        "Dedicate one day a week to using up leftovers and items nearing expiration. Get creative by making soups, stir-fries, or casseroles with whatever you have on hand.",
        "Set up a compost bin for peels, cores, and other scraps you can’t use. Composting reduces waste and creates nutrient-rich soil for gardening.",
        "Learn simple preservation techniques like pickling, drying, or fermenting. For example, turn cucumbers into pickles or make dried herbs to store longer.",
        "Divide large snack packages into smaller, reusable containers or bags to control portions and prevent overeating. This minimizes leftovers and keeps food fresh.",
        "If you have too much of something, share it with friends, family, or neighbors. Sharing helps ensure food doesn’t go to waste and fosters community bonds.",
        "Set your fridge to 37°F (3°C) and freezer to 0°F (-18°C) to keep food fresh longer. Proper temperatures prevent spoilage and ensure safety.",
        "Check expiration dates regularly and prioritize using items that are closest to their best-before date. Plan recipes around these items to reduce waste.",
        "Bring reusable bags, jars, and containers for bulk shopping. This minimizes packaging waste and helps you buy just the right amount of what you need.",
        "Transform leftovers into something new! For example, use last night’s roasted chicken in a sandwich or salad, or turn rice into fried rice with veggies.",
        "Look for zero-waste recipe ideas that use every part of an ingredient. For instance, make pesto from carrot tops or chips from potato peels.",
        "If you find yourself with more food than you can use, donate non-perishable items to food banks or shelters to help others while reducing waste."
    )

    Column(

    )
    {
        if (state.isAddingItem) {
            AddInventoryItemScreen(navController, inventoryItemViewModel, productViewModel)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        )
        {

            Text(
                "Welcome back, Food Saver!",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))


            PrimaryButton(
                text = "Add Item",
                onClick = {
                    inventoryItemViewModel.onEvent(
                        InventoryItemEvent.ShowDialog
                    )
                },
                width = 400.dp,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    "Progress in Last Month",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(Modifier.weight(1f))

                SecondaryButton(
                    text = "View Details",
                    onClick = { navController.navigate(StatisticsScreen) },
                    width = 120.dp,
                )

            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly,

                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(.475f)
                        .border(
                            BorderStroke(1.dp, color = Color.Gray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        "Products saved",
                        fontSize = 12.sp
                    )
                    Text(
                        text = (if (lostIn30Days + savedIn30Days > 0) {
                            BigDecimal(savedIn30Days * 100 / (lostIn30Days + savedIn30Days))
                                .setScale(0, RoundingMode.HALF_EVEN)
                        } else 0.0
                                ).toString() + "%",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
//                    Text(
//                        "↗10%",
//                        fontSize = 15.sp
//                    )
                    Icon(imageVector = Icons.Filled.ThumbUp, contentDescription = "Thumb Up")
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly,

                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(1f)
                        .border(
                            BorderStroke(1.dp, color = Color.Gray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        "Losses",
                        fontSize = 12.sp
                    )
                    Text(
                        lostIn30Days.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
//                    Text(
//                        "↙50%",
//                        fontSize = 15.sp
//                    )
                    Icon(
                        imageVector = Icons.Filled.ThumbUp,
                        contentDescription = "Thumb Down",
                        modifier = Modifier.rotate(180f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,

                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                Text(
                    "Your Products",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )


                SecondaryButton(
                    text = "View Details",
                    onClick = { navController.navigate(FoodScreen) },
                    width = 120.dp,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,

                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(.3f)
                        .border(
                            BorderStroke(1.dp, color = Color.Gray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(vertical = 8.dp)
                )
                {
                    Text(
                        "Total Items",
                        fontSize = 12.sp,
                    )

                    Text(
                        activeProducts.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.knife_fork_icon),
                        contentDescription = "Total items",
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,

                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(.485f)
                        .border(
                            BorderStroke(1.dp, color = Color.Gray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(vertical = 8.dp)
                )
                {
                    Text(
                        "Expire soon",
                        fontSize = 12.sp,
                    )

                    Text(
                        expiringSoonItems.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = "Expire soon",
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,

                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(1f)
                        .border(
                            BorderStroke(1.dp, color = Color.Gray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(vertical = 8.dp)
                )
                {
                    Text(
                        "Expired",
                        fontSize = 12.sp,
                    )

                    Text(
                        expiredActiveItems.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.recycle_bin_icon),
                        contentDescription = "Expired",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))


            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Food saving Tip",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .border(
                        BorderStroke(1.dp, color = Color.Gray),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 10.dp)
//                    .verticalScroll(rememberScrollState())
            )
            {
                Text(
                    text = tips[Random.nextInt(0, tips.size)],
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            )
            {
                SecondaryButton(
                    text = "Open Educational Video", onClick = {
                        navController.navigate(VideoScreen)
                    },
                    width = 300.dp
                )
            }

        }

    }
}

@Composable
fun ExpirationListItem(
    name: String,
    expired: Boolean,
    days: Int,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        modifier = Modifier
            .fillMaxWidth(1f)
            .height(32.dp)
            .background(color = if (expired) Color.Red else Color.Yellow)
            .padding(horizontal = 8.dp)
    )
    {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,

            modifier = Modifier
                .width(80.dp)
        )
        {
            Icon(imageVector = Icons.Filled.Face, contentDescription = "Food")
            Text(
                text = name,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            text = if (expired) "Expired $days day(s) ago" else "Expires in $days day(s)",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth(),
        thickness = 1.dp,
        color = Color.Gray
    )

    Spacer(modifier = Modifier.height(4.dp))
}
