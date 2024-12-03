package com.example.wastelessapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wastelessapp.ui.components.CustomButton
import com.example.wastelessapp.ui.components.CustomTopAppBar
import com.example.wastelessapp.ui.components.PrimaryButton
import com.example.wastelessapp.ui.components.SecondaryButton
import com.example.wastelessapp.ui.components.ShoppingListItem
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Preview
@Composable
fun HomeScreen() {
    Column (

    )
    {
        CustomTopAppBar("Home Screen")

        Spacer(modifier = Modifier.height(16.dp))

        Column (
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

            Row (
                verticalAlignment = Alignment.CenterVertically
              )
            {
                Text (
                    "Progress in Last Month",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.width(40.dp))

                SecondaryButton(
                    text = "View Details...",
                    onClick = { /*TODO*/ },
                    width = 120.dp,
                )

            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Column (
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly,

                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(.475f)
                        .border(BorderStroke(1.dp, color = Color.Gray), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        "Products saved",
                        fontSize = 12.sp
                    )
                    Text(
                        "87%",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "↗10%",
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column (
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly,

                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(1f)
                        .border(BorderStroke(1.dp, color = Color.Gray), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        "Losses",
                        fontSize = 12.sp
                    )
                    Text(
                        "1",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "↙50%",
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,

                modifier = Modifier
                    .fillMaxWidth(1f)
            )
             {
                Text(
                    "Your Products",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                 SecondaryButton(
                     text = "View Details...",
                     onClick = { /*TODO*/ },
                     width = 120.dp,
                 )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,

                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(.3f)
                        .border(BorderStroke(1.dp, color = Color.Gray), shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp)
                )
                {
                    Text (
                        "Not saved",
                        fontSize = 12.sp,
                    )

                    Text (
                        "6",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Not Saved")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,

                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(.485f)
                        .border(BorderStroke(1.dp, color = Color.Gray), shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp)
                )
                {
                    Text (
                        "Expire soon",
                        fontSize = 12.sp,
                    )

                    Text (
                        "4",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(imageVector = Icons.Filled.Warning, contentDescription = "Expire soon")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,

                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(1f)
                        .border(BorderStroke(1.dp, color = Color.Gray), shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp)
                )
                {
                    Text (
                        "Expired",
                        fontSize = 12.sp,
                    )

                    Text (
                        "1",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Expired")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            ExpirationListItem(name = "Apple", expired = true, days = 2)

            Column (
                modifier = Modifier
                    .fillMaxWidth(1f)
            )
            {
                ExpirationListItem(name = "Steak", expired = false, days = 1)
                ExpirationListItem(name = "Steak", expired = false, days = 1)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Tips How to Save Food",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(BorderStroke(1.dp, color = Color.Gray), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 10.dp)
                    .verticalScroll(rememberScrollState())
            )
            {
                Text(
                    text = "To avoid food waste, plan meals for the week using ingredients " +
                            "you already have before shopping. This helps ensure nothing is wasted, " +
                            "and you only buy what you need!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
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
)
{
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        modifier = Modifier
            .fillMaxWidth(1f)
            .height(32.dp)
            .background(color = if (expired) Color.Red else Color.Yellow)
            .padding(horizontal = 8.dp)
    )
    {
        Row (
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,

            modifier = Modifier
                .width(80.dp)
        )
        {
            Icon(imageVector = Icons.Filled.Face, contentDescription = "Food")
            Text (
                text = name,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Text (
            text = if (expired) "Expired $days day(s) ago" else "Expires in $days day(s)",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }

    HorizontalDivider(modifier = Modifier
        .fillMaxWidth(),
        thickness = 1.dp,
        color = Color.Gray
    )

    Spacer(modifier = Modifier.height(4.dp))
}
