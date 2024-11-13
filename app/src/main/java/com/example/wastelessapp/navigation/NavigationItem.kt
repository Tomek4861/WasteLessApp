package com.example.wastelessapp.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val route: Any,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    var hasNews: Boolean,
    var badgeCount: Int? = null
)


