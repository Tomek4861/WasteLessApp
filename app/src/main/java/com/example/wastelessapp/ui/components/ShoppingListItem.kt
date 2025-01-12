package com.example.wastelessapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wastelessapp.database.entities.inventory_item.ItemUnit


data class ShoppingItem(
    override val id: Int,
    override val name: String,
    override val quantity: Float,
    override val unit: ItemUnit,
    override val iconId: Int,
    var isChecked: Boolean = false,
) : BaseItem(id, name, quantity, unit, iconId){
    var isCheckedState by mutableStateOf(isChecked)

}


@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onCheck: (ShoppingItem) -> Unit,
    onDelete: (ShoppingItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .bottomBorder()
            .padding(4.dp)
            .background(if (item.isCheckedState) Color.Gray.copy(alpha = 0.5f) else Color.Transparent),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        val iconObj: ImageVector = ImageVector.vectorResource(id = item.iconId)
        Icon(
            imageVector = iconObj,
            contentDescription = "Item Icon",

            modifier = Modifier.size(48.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = item.name,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium, // slight boldness
                modifier = Modifier
            )
            Text(
                text = item.getQtyString(),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,

                modifier = Modifier
                    .alpha(0.5f)   // changing opacity

            )
        }
        Spacer(Modifier.weight(1f))

        IconButton(
            onClick = { onCheck(item) },
            modifier = Modifier.size(48.dp) // default size
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Check",
                tint = Color.White,
                modifier = Modifier.background(
                    Color.Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp)
                )
            )
        }


        IconButton(
            onClick = { onDelete(item) },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.Red,
                modifier = Modifier.background(
                    Color.Red.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                )
            )

        }


    }


}