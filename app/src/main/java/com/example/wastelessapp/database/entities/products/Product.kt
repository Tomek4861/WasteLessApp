package com.example.wastelessapp.database.entities.products

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)