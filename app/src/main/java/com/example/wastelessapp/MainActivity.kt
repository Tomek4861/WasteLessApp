package com.example.wastelessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.wastelessapp.navigation.BottomNavigationBar
import com.example.wastelessapp.navigation.BottomNavigationItem
import com.example.wastelessapp.navigation.NavigationGraph
import com.example.wastelessapp.screens.FoodScreen
import com.example.wastelessapp.screens.HomeScreen
import com.example.wastelessapp.screens.SettingsScreen
import com.example.wastelessapp.screens.ShoppingListScreen
import com.example.wastelessapp.screens.StatisticsScreen
import com.example.wastelessapp.ui.theme.WasteLessAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WasteLessAppTheme {
                val navController = rememberNavController()
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
                        selectedIcon = Icons.Filled.Menu,
                        unselectedIcon = Icons.Outlined.Menu,
                        hasNews = true,
                        badgeCount = 5
                    ),
                    BottomNavigationItem(
                        title = "Statistics",
                        route = StatisticsScreen,
                        selectedIcon = Icons.Filled.CheckCircle,
                        unselectedIcon = Icons.Outlined.CheckCircle,
                        hasNews = false
                    ),
                    BottomNavigationItem(
                        title = "Shopping List",
                        route = ShoppingListScreen,
                        selectedIcon = Icons.Filled.ShoppingCart,
                        unselectedIcon = Icons.Outlined.ShoppingCart,
                        hasNews = true
                    )
                    ,BottomNavigationItem(
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
                    bottomBar = {
                        BottomNavigationBar(
                            navItems = navItems,
                            navController = navController,
                            selectedItemIndex = selectedItemIndex,
                            onItemSelected = {index, route ->
                                selectedItemIndex = index
                                navController.navigate(route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavigationGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}