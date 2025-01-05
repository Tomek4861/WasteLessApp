package com.example.wastelessapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wastelessapp.database.entities.product.ProductEvent
import com.example.wastelessapp.database.entities.product.ProductState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    sheetState: SheetState,
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    state: ProductState,
    onEvent: (ProductEvent) -> Unit

) {
    if (isOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                onDismissRequest()
                onEvent(ProductEvent.HideDialog)
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 16.dp)
            ) {
                Text(
                    "Add New Product",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start,
                )
                Spacer(modifier = Modifier.height(24.dp))


                TextField(
                    value = state.name,
                    onValueChange = { onEvent(ProductEvent.SetName(it)) },
                    label = { Text("Product Name") },
                    placeholder = { Text("Enter product name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Select Icon",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start,
                )


                val icons = listOf(
                    Icons.Default.Home,
                    Icons.Default.Settings,
                    Icons.Default.ShoppingCart,
                    Icons.Default.Menu,
                    Icons.Default.CheckCircle,
                    Icons.Default.Check,
                    Icons.Default.Face,

                    )
                ScrollableIconRow(
                    icons = icons,
                    onIconSelected = { onEvent(ProductEvent.SetIcon(it)) },
                    selectedIcon = state.icon
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PrimaryButton(
                        text = "Add Product",
                        onClick = {
                            onEvent(ProductEvent.SaveProduct)
                            onDismissRequest()
                        },
                        width = 220.dp
                    )
                }


            }
        }
    }
}


@Composable
fun ScrollableIconRow(
    icons: List<ImageVector>,
    onIconSelected: (ImageVector) -> Unit,
    selectedIcon: ImageVector,
    iconSize: Dp = 48.dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        icons.forEach { icon ->
            IconButton(
                onClick = { onIconSelected(icon) },
                modifier = Modifier
                    .size(iconSize)
                    .background(
                        color = if (icon == selectedIcon) MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.3f
                        ) else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = if (icon == selectedIcon) 2.dp else 1.dp,
                        color = if (icon == selectedIcon) MaterialTheme.colorScheme.primary else Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}