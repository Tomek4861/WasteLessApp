package com.example.wastelessapp.database.rooms.product

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var unit: Unit,
    var amount: Float,
    var expirationDate: Date,
    var price: Float?
    // state - expired saved held
)