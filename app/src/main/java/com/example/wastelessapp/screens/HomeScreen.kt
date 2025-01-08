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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemViewModel
import com.example.wastelessapp.database.entities.inventory_item.ItemState
import com.example.wastelessapp.database.entities.inventory_item.ItemUnit
import com.example.wastelessapp.database.entities.product.ProductViewModel
import com.example.wastelessapp.ui.components.BottomSheet
import com.example.wastelessapp.ui.components.FoodInventoryItem
import com.example.wastelessapp.ui.components.FoodItem
import com.example.wastelessapp.ui.components.PrimaryButton
import com.example.wastelessapp.ui.components.SecondaryButton
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import kotlin.random.Random

@Serializable
object HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    inventoryItemViewModel: InventoryItemViewModel,
    productViewModel: ProductViewModel,
    navController: NavController
) {
    val state by inventoryItemViewModel.state.collectAsState()
    val productState by productViewModel.state.collectAsState()

    val savedIn30Days = kotlinx.coroutines.runBlocking {
        inventoryItemViewModel.countItemsInLast30DaysByState(ItemState.SAVED)
    }
    val lostIn30Days = kotlinx.coroutines.runBlocking {
        inventoryItemViewModel.countItemsInLast30DaysByState(ItemState.EXPIRED)
    }
    val savedAtAllTime = kotlinx.coroutines.runBlocking {
        inventoryItemViewModel.countAllItemsByState(ItemState.SAVED)
    }
    val lostAtAllTime = kotlinx.coroutines.runBlocking {
        inventoryItemViewModel.countAllItemsByState(ItemState.EXPIRED)
    }
    val tips = listOf("Organize your fridge by grouping similar items together and keeping frequently used foods in front. This way, you’ll avoid forgetting about items in the back and create an efficient system that helps reduce waste and saves money over time.",
        "Cooking meals in batches not only saves time but also minimizes waste. Prepare larger portions, store them in reusable containers, and enjoy fresh, homemade meals throughout the week without the hassle of cooking every day!",
        "Repurpose your food scraps creatively—use vegetable peels for broth, stale bread for croutons or breadcrumbs, and overripe fruits for smoothies or baking. Small creative steps like these can reduce waste and add flavor to your meals!",
        "Avoid impulse buys by planning your shopping trips carefully. Stick to a well-prepared list and resist the urge to overstock your pantry. This keeps your budget under control, ensures you use what you already have, and prevents unnecessary waste.",
        "Track what’s already in your fridge and pantry before making a shopping list. By knowing what you have, you’ll avoid buying duplicates, keep your food rotation system effective, and make sure nothing goes unused or forgotten.",
        "Smart storage makes a big difference! Store fruits and vegetables separately, and keep items like herbs in water or wrapped in damp paper towels. Proper storage extends freshness and ensures nothing goes to waste unnecessarily.",
        "Be mindful of portion sizes while cooking. Preparing just enough ensures everyone is satisfied without leaving excess food behind. If there are leftovers, store them properly for the next meal to enjoy without letting them go to waste.",
        "To avoid food waste, plan meals for the week using ingredients you already have before shopping. This helps ensure nothing is wasted, and you only buy what you need!",
    )

    Column(

    )
    {


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
                text = "Add New Product",
                onClick = { /*TODO*/ },
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
                        text = (if(lostIn30Days+savedIn30Days > 0) {
                            BigDecimal(savedIn30Days*100/(lostIn30Days+savedIn30Days))
                                .setScale(0, RoundingMode.HALF_EVEN)
                        }
                        else 0.0
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
                    Icon(imageVector = Icons.Filled.ThumbUp, contentDescription = "Thumb Down", modifier = Modifier.rotate(180f))
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
                        "Not saved",
                        fontSize = 12.sp,
                    )

                    Text(
                        lostAtAllTime.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Not Saved")
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
                        "4", // TODO Change to actual value
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(imageVector = Icons.Filled.Warning, contentDescription = "Expire soon")
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
                        "All items",
                        fontSize = 12.sp,
                    )

                    Text(
                        state.inventoryItems.size.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(imageVector = Icons.Filled.Check, contentDescription = "Expired")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

//            FoodInventoryItem(
//                FoodItem(
//                    id = 1,
//                    name = "Apple",
//                    quantity = 2f,
//                    unit = ItemUnit.PIECES,
//                    price = 5.50f,
//                    expiryDate = LocalDateTime.now(),
//                    purchaseDate = LocalDateTime.now(),
//                    iconId = 2131165290,
//                ),
//                onCheck = {},
//                onDelete = {}
//            )
//
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth(1f)
//            )
//            {
//                FoodInventoryItem(
//                    FoodItem(
//                        id = 2,
//                        name = "Steak",
//                        quantity = 1f,
//                        unit = ItemUnit.PIECES,
//                        price = 27.30f,
//                        expiryDate = LocalDateTime.now(),
//                        purchaseDate = LocalDateTime.now(),
//                        iconId = 2131165290,
//                    ),
//                    onCheck = {},
//                    onDelete = {}
//                )
//                FoodInventoryItem(
//                    FoodItem(
//                        id = 2,
//                        name = "Steak",
//                        quantity = 1f,
//                        unit = ItemUnit.PIECES,
//                        price = 27.30f,
//                        expiryDate = LocalDateTime.now(),
//                        purchaseDate = LocalDateTime.now(),
//                        iconId = 2131165290,
//                    ),
//                    onCheck = {},
//                    onDelete = {}
//                )
//            }

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
                    .height(150.dp)
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
