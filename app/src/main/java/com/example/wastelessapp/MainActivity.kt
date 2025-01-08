package com.example.wastelessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.wastelessapp.database.WasteLessAppDatabase
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemViewModel
import com.example.wastelessapp.database.entities.product.ProductViewModel
import com.example.wastelessapp.database.entities.shopping_cart.ShoppingCartViewModel
import com.example.wastelessapp.navigation.BottomNavigationBar
import com.example.wastelessapp.navigation.BottomNavigationItem
import com.example.wastelessapp.navigation.NavigationGraph
import com.example.wastelessapp.notifications.createNotificationChannels
import com.example.wastelessapp.screens.FoodScreen
import com.example.wastelessapp.screens.HomeScreen
import com.example.wastelessapp.screens.SettingsScreen
import com.example.wastelessapp.screens.ShoppingListScreen
import com.example.wastelessapp.screens.StatisticsScreen
import com.example.wastelessapp.ui.components.CustomTopAppBar
import com.example.wastelessapp.ui.theme.WasteLessAppTheme


class MainActivity : ComponentActivity() {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE InventoryItem ADD COLUMN iconResId INTEGER NOT NULL DEFAULT 0")
        }
    }

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            WasteLessAppDatabase::class.java,
            "wastelessapp.db"
        )   .fallbackToDestructiveMigration()
            .build()
    }

    private val productViewModel by viewModels<ProductViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductViewModel(db.productDao) as T
                }
            }
        }
    )

    private val shoppingCartViewModel by viewModels<ShoppingCartViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ShoppingCartViewModel(db.shoppingCartDao, db.productDao) as T
                }
            }
        }
    )

    private val inventoryItemViewModel by viewModels<InventoryItemViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return InventoryItemViewModel(db.inventoryItemDao, db.shoppingCartDao, db.productDao) as T
                }
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannels(this)
        enableEdgeToEdge()
        setContent {
            WasteLessAppTheme {
                val navController = rememberNavController()

                val screensNameList = listOf(
                    "Home",
                    "Food",
                    "Statistics",
                    "Shopping List",
                    "Settings"
                )
                val navItems = listOf(
                    BottomNavigationItem(
                        title = "Home",
                        route = HomeScreen,
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                        hasNews = false
                    ),
                    BottomNavigationItem(
                        title = "Food",
                        route = FoodScreen,
                        selectedIcon = ImageVector.vectorResource(R.drawable.knife_fork_icon),
                        unselectedIcon = ImageVector.vectorResource(R.drawable.knife_fork_outline_icon),
                        hasNews = false
                    ),
                    BottomNavigationItem(
                        title = "Statistics",
                        route = StatisticsScreen,
                        selectedIcon = ImageVector.vectorResource(R.drawable.stats_chart_icon),
                        unselectedIcon = ImageVector.vectorResource(R.drawable.stats_chart_outline_icon),
                        hasNews = false
                    ),
                    BottomNavigationItem(
                        title = "Shopping List",
                        route = ShoppingListScreen,
                        selectedIcon = Icons.Filled.ShoppingCart,
                        unselectedIcon = Icons.Outlined.ShoppingCart,
                        hasNews = false
                    ), BottomNavigationItem(
                        title = "Settings",
                        route = SettingsScreen,
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings,
                        hasNews = false
                    )

                )

                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }

//                NavHost(
//                    navController = navController,
//                    startDestination =
//                ) { }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CustomTopAppBar(pageName = screensNameList[selectedItemIndex])
                    },
                    bottomBar = {
                        BottomNavigationBar(
                            navItems = navItems,
                            navController = navController,
                            selectedItemIndex = selectedItemIndex,
                            onItemSelected = { index, route ->
                                println("Index: $index, Route: $route")
                                selectedItemIndex = index
                                navController.navigate(route)
                            }
                        )
                    }
                ) { innerPadding ->
                    NavigationGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        productViewModel = productViewModel,
                        shoppingCartViewModel = shoppingCartViewModel,
                        inventoryItemViewModel = inventoryItemViewModel
                    )
                }
            }
        }
    }
}