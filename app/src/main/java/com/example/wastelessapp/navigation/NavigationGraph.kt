package com.example.wastelessapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.example.wastelessapp.screens.VideoScreen

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
        modifier = modifier,
//        enterTransition = {
//            slideInHorizontally(
//                initialOffsetX = { fullWidth -> fullWidth },
//                animationSpec = tween(durationMillis = 300)
//            )
//        },
//        exitTransition = {
//            slideOutHorizontally(
//                targetOffsetX = { fullWidth -> -fullWidth },
//                animationSpec = tween(durationMillis = 300)
//            )
//        }
        enterTransition = {
            fadeIn(animationSpec = tween(200))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(200))
        }
    ) {
        composable<HomeScreen> {
            HomeScreen(inventoryItemViewModel, productViewModel, navController = navController)
        }
        composable<FoodScreen> {
            FoodInventoryScreen(navController = navController, inventoryItemViewModel, productViewModel)
        }
        composable<StatisticsScreen> {
            StatisticsScreen(inventoryItemViewModel)
        }
        composable<ShoppingListScreen> {
            ShoppingListScreen(navController, shoppingCartViewModel, inventoryItemViewModel, productViewModel)
        }
        composable<SettingsScreen> {
            SettingsScreen()
        }
        composable<AddInventoryItemScreen> {
            AddInventoryItemScreen(
                navController = navController,
                inventoryItemViewModel,
                productViewModel
            )
        }

        composable<AddShoppingListItemScreen> {
            AddShoppingListItemScreen(navController = navController, shoppingCartViewModel, productViewModel)
        }

        composable<VideoScreen> {
            VideoScreen(navController)
        }
    }
}
