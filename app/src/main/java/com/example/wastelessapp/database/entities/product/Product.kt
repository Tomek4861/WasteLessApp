package com.example.wastelessapp.database.entities.product

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val icon: ImageVector
)