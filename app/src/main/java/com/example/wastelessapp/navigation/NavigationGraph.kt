package com.example.wastelessapp.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wastelessapp.screens.AddInventoryItemScreen
import com.example.wastelessapp.screens.FoodScreen
import com.example.wastelessapp.screens.FoodInventoryScreen
import com.example.wastelessapp.screens.HomeScreen
import com.example.wastelessapp.screens.SettingsScreen
import com.example.wastelessapp.screens.ShoppingListScreen
import com.example.wastelessapp.screens.StatisticsScreen

//!! Send route for navHost/navController everywhere for better navigation

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = HomeScreen,
        modifier = modifier
    ) {
        composable<HomeScreen> {
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                //TODO should switch current selection of bottom bar, now opens food screen as another screen

//                Button(onClick = {
//                    navController.navigate(FoodScreen)
//                }) {
//                    Text(text = "Go to Food Screen")
//                }
//            }
            HomeScreen()
        }
        composable<FoodScreen> {
            FoodInventoryScreen(navController = navController)
        }
        composable<StatisticsScreen>{
            StatisticsScreen()
        }
        composable<ShoppingListScreen> {
            ShoppingListScreen()
        }
        composable<SettingsScreen> {
            SettingsScreen()
        }
        composable<AddInventoryItemScreen> {
            AddInventoryItemScreen(navController = navController)
        }
    }
}
