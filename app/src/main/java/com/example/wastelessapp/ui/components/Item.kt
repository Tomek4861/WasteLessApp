package com.example.wastelessapp.ui.components

import com.example.wastelessapp.database.entities.inventory_item.ItemUnit


open class BaseItem(
    open val id: Int,
    open val name: String,
    open val quantity: Float,
    open val unit: ItemUnit,
    open val iconId: Int,

    ) {
    fun getQtyString(): String {

        return when (unit) {
            ItemUnit.GRAMS -> "${quantity}g"
            ItemUnit.KILOGRAMS -> "${quantity}kg"
            ItemUnit.LITERS -> "${quantity}l"
            ItemUnit.PIECES -> "$quantity pcs"
        }
    }

}
