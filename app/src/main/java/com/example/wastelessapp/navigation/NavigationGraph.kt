package com.example.wastelessapp.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemViewModel
import com.example.wastelessapp.database.entities.product.ProductViewModel
import com.example.wastelessapp.database.entities.shopping_cart.ShoppingCartViewModel
import com.example.wastelessapp.screens.AddInventoryItemScreen
import com.example.wastelessapp.screens.AddShoppingListItemScreen
import com.example.wastelessapp.screens.FoodInventoryScreen
import com.example.wastelessapp.screens.FoodScreen
import com.example.wastelessapp.screens.HomeScreen
import com.example.wastelessapp.screens.SettingsScreen
import com.example.wastelessapp.screens.ShoppingListScreen
import com.example.wastelessapp.screens.StatisticsScreen

//!! Send route for navHost/navController everywhere for better navigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    productViewModel: ProductViewModel,
    shoppingCartViewModel: ShoppingCartViewModel,
    inventoryItemViewModel: InventoryItemViewModel
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreen,
        modifier = modifier
    ) {
        composable<HomeScreen> {
            HomeScreen(inventoryItemViewModel, productViewModel, navController = navController)
        }
        composable<FoodScreen> {
            FoodInventoryScreen(navController = navController, inventoryItemViewModel)
        }
        composable<StatisticsScreen>{
            StatisticsScreen(inventoryItemViewModel)
        }
        composable<ShoppingListScreen> {
            ShoppingListScreen(navController, shoppingCartViewModel, inventoryItemViewModel)
        }
        composable<SettingsScreen> {
            SettingsScreen()
        }
        composable<AddInventoryItemScreen> {
            AddInventoryItemScreen(navController = navController, inventoryItemViewModel, productViewModel)
        }

        composable<AddShoppingListItemScreen> {
            AddShoppingListItemScreen(navController = navController, shoppingCartViewModel, productViewModel)
        }
    }
}
