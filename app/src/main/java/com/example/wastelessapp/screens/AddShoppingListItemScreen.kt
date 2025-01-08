package com.example.wastelessapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.example.wastelessapp.database.entities.inventory_item.ItemUnit
import com.example.wastelessapp.database.entities.product.ProductState
import com.example.wastelessapp.database.entities.product.ProductViewModel
import com.example.wastelessapp.database.entities.shopping_cart.ShoppingCartEvent
import com.example.wastelessapp.database.entities.shopping_cart.ShoppingCartViewModel
import com.example.wastelessapp.ui.components.BottomSheet
import com.example.wastelessapp.ui.components.PrimaryButton
import com.example.wastelessapp.ui.components.SquareIconButton
import kotlinx.serialization.Serializable

@Serializable
object AddShoppingListItemScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddShoppingListItemScreen(
    navController: NavHostController,
    shoppingCartViewModel: ShoppingCartViewModel,
    productViewModel: ProductViewModel,

) {
    val state by shoppingCartViewModel.state.collectAsState()
    val productState by productViewModel.state.collectAsState()
    val onEvent = shoppingCartViewModel::onEvent

//    var selectedDate by remember { mutableStateOf("") }
//    var productName by remember { mutableStateOf("") }
    var productAmountTextState by remember { mutableStateOf(state.amount.toString()) }
    var errorMessage by remember { mutableStateOf("") }
    var selectedItemUnit by remember { mutableStateOf(state.itemUnit) }

    val products = productState.products
    val productNames: List<String> = products.map { it.name }

    val pattern = remember { Regex("^\\d*\\.?\\d*\$") }

    Column (){

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Add new Shopping List Item",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Search for a Product",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(0.dp)

            ) {

                AutoCompleteTextFieldShoppingList(
                    modifier = Modifier.fillMaxWidth(0.82f),

                    onProductSelected = {
                        onEvent(ShoppingCartEvent.SetProduct(it)) },
                    productState=productState,
                    viewmodel = shoppingCartViewModel,
                )

                val sheetState = rememberModalBottomSheetState()
                var isSheetOpen by rememberSaveable { mutableStateOf(false) }
                BottomSheet(
                    sheetState,
                    isSheetOpen,
                    { isSheetOpen = false },
                    productState,
                    productViewModel::onEvent
                )

                SquareIconButton(
                    onClick = { isSheetOpen = true},
                )

            }



                Text(
                text = "Search by name",
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Unit",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Row (

            ){
                UnitOption(
                    text = "Pcs",
                    value = ItemUnit.PIECES,
                    isSelected = selectedItemUnit == ItemUnit.PIECES,
                    onUnitChange = {
                        selectedItemUnit = it
                        onEvent(ShoppingCartEvent.SetUnit(it))
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                UnitOption(
                    text = "G",
                    value = ItemUnit.GRAMS,
                    isSelected = selectedItemUnit == ItemUnit.GRAMS,
                    onUnitChange = {
                        selectedItemUnit = it
                        onEvent(ShoppingCartEvent.SetUnit(it))
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                UnitOption(
                    text = "Kg",
                    value = ItemUnit.KILOGRAMS,
                    isSelected = selectedItemUnit == ItemUnit.KILOGRAMS,
                    onUnitChange = {
                        selectedItemUnit = it
                        onEvent(ShoppingCartEvent.SetUnit(it))
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                UnitOption(
                    text = "L",
                    value = ItemUnit.LITERS,
                    isSelected = selectedItemUnit == ItemUnit.LITERS,
                    onUnitChange = {
                        selectedItemUnit = it
                        onEvent(ShoppingCartEvent.SetUnit(it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Amount",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            TextField(
                value = productAmountTextState,
                onValueChange = {
                    if(it.isEmpty()) {
                        productAmountTextState = ""
                        onEvent(ShoppingCartEvent.SetAmount(0f))
                    }
                    else if (it.matches(pattern)) {
                        productAmountTextState = it
                        onEvent(ShoppingCartEvent.SetAmount(it.toFloat()))
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                label = { Text("Enter amount of product...") }
            )
            Text(
                text = "Enter product amount",
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(16.dp))



            Text(
                text = errorMessage,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Red
            )

            Spacer(modifier = Modifier.height(8.dp))

            PrimaryButton("Add Item", onClick = {

                errorMessage = error(
                    productName = state.product,
                    amount = state.amount.toString(),
                    expDate = null,
                    price = null,
                    productsList = productNames
                )

                if (errorMessage.isEmpty()) {
//                if (true) {

//                    onEvent(InventoryItemEvent.SetProduct(productName))
//                    onEvent(InventoryItemEvent.SetAmount(ProductAmountTextState.text.toFloat()))
//                    onEvent(InventoryItemEvent.SetExpirationDate(convertDate(selectedDate)))
//                    if (PriceTextState.text.isNotEmpty())
//                        onEvent(InventoryItemEvent.SetPrice(PriceTextState.text.toFloat()))

                    onEvent(ShoppingCartEvent.SaveShoppingCartItem)

                    navController.navigate(ShoppingListScreen)
                }
            }
                , width = 400.dp)

        }
    }
}

@Composable
fun AutoCompleteTextFieldShoppingList(
    items: List<String>, // List of items for suggestions
    modifier: Modifier = Modifier,
    onItemSelected: (String) -> Unit = {},
    viewmodel: ShoppingCartViewModel
) {
    val state by viewmodel.state.collectAsState()
    val onEvent = viewmodel::onEvent

    var textState by remember { mutableStateOf(state.product) }

    var expanded by remember { mutableStateOf(false) }
    val filteredItems = remember(textState) {
        items.filter { it.startsWith(textState, ignoreCase = true) }
    }

    Column(modifier = modifier) {
        TextField(
            value = textState,
            onValueChange = {
                textState = it
                onEvent(ShoppingCartEvent.SetProduct(it))
                expanded = filteredItems.isNotEmpty() // Show dropdown if there are suggestions
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Start typing product name...") }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(focusable = false) // Ensure dropdown does not steal focus
        ) {
            filteredItems.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        textState = TextFieldValue(item).text // Update text field with selected item
                        //onEvent(InventoryItemEvent.SetProduct(TextFieldValue(item).text))
                        expanded = false
                        onItemSelected(item) // Notify parent composable of selection
                    },
                    text = { Text(item) }
                )
            }
        }
    }
}



@Composable
fun AutoCompleteTextFieldShoppingList(
    onProductSelected: (String) -> Unit,
    productState: ProductState,
    viewmodel: ShoppingCartViewModel,
    modifier: Modifier = Modifier
) {
    val products = productState.products
    val foodItems: List<String> = products.map { it.name }
    AutoCompleteTextFieldShoppingList(
        items = foodItems,
        modifier = modifier,
        onItemSelected = { selectedItem ->
            onProductSelected(selectedItem)
        },
        viewmodel
    )
}
