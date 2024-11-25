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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.wastelessapp.ui.components.ShoppingListItem
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Preview
@Composable
fun HomeScreen() {
    Column (
        modifier = Modifier
            .background(color = Color.White)
    )
    {
        CustomTopAppBar("Home Screen")

        Spacer(modifier = Modifier.height(16.dp))

        Column (
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
            )
        {


            Text(
                "Welcome back, Food Saver!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomButton(
                text = "Add New Product",
                onClick = { /*TODO*/ },
                backgroundColor = Color.Black,
                contentColor = Color.White,
                width = 400.dp,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically
              )
            {
                Text (
                    "Your Progress in Last Month",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.width(12.dp))

                CustomButton(
                    text = "View Details...",
                    onClick = { /*TODO*/ },
                    backgroundColor = Color.White,
                    contentColor = Color.Black,
                    width = 120.dp,
                    fontSize = 10.sp
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
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Text(
                        "87%",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "↗10%",
                        color = Color.Black,
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
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Text(
                        "1",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "↙50%",
                        color = Color.Black,
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
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                 CustomButton(
                     text = "View Details...",
                     onClick = { /*TODO*/ },
                     backgroundColor = Color.White,
                     contentColor = Color.Black,
                     width = 120.dp,
                     fontSize = 10.sp
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
                        color = Color.Gray,
                        fontSize = 12.sp,
                    )

                    Text (
                        "6",
                        color = Color.Black,
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
                        color = Color.Gray,
                        fontSize = 12.sp,
                    )

                    Text (
                        "4",
                        color = Color.Black,
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
                        color = Color.Gray,
                        fontSize = 12.sp,
                    )

                    Text (
                        "1",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Expired")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            ExpirationListItem(name = "Apple", expired = true, days = 2)

            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth(1f)
            )
            {
                items(2) { item ->
                    ExpirationListItem(name = "Steak", expired = false, days = 1)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Tips How to Save Food",
                color = Color.Black,
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
            )
            {
                Text(
                    text = "To avoid food waste, plan meals for the week using ingredients " +
                            "you already have before shopping. This helps ensure nothing is wasted, " +
                            "and you only buy what you need!",
                    color = Color.Black,
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
