package com.example.wastelessapp.database.entities.inventory_item

import androidx.room.TypeConverter

enum class ItemUnit {
    PIECES, GRAMMS, KILOGRAMMS, LITERS
}

class ItemUnitConverters {
    @TypeConverter
    fun fromItemState(state: ItemState): String {
        return state.name
    }

    @TypeConverter
    fun toItemState(value: String): ItemState {
        return ItemState.valueOf(value)
    }
}