package com.example.wastelessapp.database.entities.inventory_item

import androidx.room.TypeConverter

enum class ItemState {
    ACTIVE, EXPIRED, SAVED
}

class ItemStateConverters {
    @TypeConverter
    fun fromItemState(state: ItemState): String {
        return state.name
    }

    @TypeConverter
    fun toItemState(value: String): ItemState {
        return ItemState.valueOf(value)
    }
}