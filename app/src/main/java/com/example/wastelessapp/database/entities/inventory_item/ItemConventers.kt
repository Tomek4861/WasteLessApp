package com.example.wastelessapp.database.entities.inventory_item

import androidx.room.TypeConverter

class ItemConverters {

    @TypeConverter
    fun fromItemState(value: ItemState): String {
        return value.name
    }

    @TypeConverter
    fun toItemState(value: String): ItemState {
        return ItemState.valueOf(value)
    }

    @TypeConverter
    fun fromItemUnit(value: ItemUnit): String {
        return value.name
    }

    @TypeConverter
    fun toItemUnit(value: String): ItemUnit {
        return ItemUnit.valueOf(value)
    }
}
