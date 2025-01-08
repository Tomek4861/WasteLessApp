package com.example.wastelessapp.screens

import android.app.DatePickerDialog
import androidx.collection.objectIntMap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemEvent
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemState
import com.example.wastelessapp.database.entities.inventory_item.InventoryItemViewModel
import com.example.wastelessapp.database.entities.inventory_item.ItemUnit
import com.example.wastelessapp.database.entities.product.ProductEvent
import com.example.wastelessapp.database.entities.product.ProductState
import com.example.wastelessapp.database.entities.product.ProductViewModel
import com.example.wastelessapp.ui.components.BottomSheet
import com.example.wastelessapp.ui.components.CustomButton
import com.example.wastelessapp.ui.components.PrimaryButton
import com.example.wastelessapp.ui.components.SecondaryButton
import com.example.wastelessapp.ui.components.SquareIconButton
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.ResolverStyle
import java.util.Calendar
import java.sql.Date

@Serializable
object AddInventoryItemScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInventoryItemScreen(
    navController: NavHostController,
    inventoryItemViewModel: InventoryItemViewModel,
    productViewModel: ProductViewModel,
) {
    val state by inventoryItemViewModel.state.collectAsState()
    val productState by productViewModel.state.collectAsState()
    val onEvent = inventoryItemViewModel::onEvent

//    var selectedDate by remember { mutableStateOf("") }
//    var productName by remember { mutableStateOf("") }
    var ProductAmountTextState by remember { mutableStateOf(state.amount.toString()) }
    var PriceTextState by remember { mutableStateOf(state.price.toString()) }
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
                text = "Add new Inventory Item",
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

                AutoCompleteTextFieldProducts(
                    modifier = Modifier.fillMaxWidth(0.82f),

                    onProductSelected = {
                        onEvent(InventoryItemEvent.SetProduct(it)) },
                    productState=productState,
                    inventoryItemViewModel=inventoryItemViewModel
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
                    text = "pcs",
                    value = ItemUnit.PIECES,
                    isSelected = selectedItemUnit == ItemUnit.PIECES,
                    onUnitChange = {
                        selectedItemUnit = it
                        onEvent(InventoryItemEvent.SetUnit(it))
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                UnitOption(
                    text = "g",
                    value = ItemUnit.GRAMS,
                    isSelected = selectedItemUnit == ItemUnit.GRAMS,
                    onUnitChange = {
                        selectedItemUnit = it
                        onEvent(InventoryItemEvent.SetUnit(it))
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                UnitOption(
                    text = "kg",
                    value = ItemUnit.KILOGRAMS,
                    isSelected = selectedItemUnit == ItemUnit.KILOGRAMS,
                    onUnitChange = {
                        selectedItemUnit = it
                        onEvent(InventoryItemEvent.SetUnit(it))
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                UnitOption(
                    text = "l",
                    value = ItemUnit.LITERS,
                    isSelected = selectedItemUnit == ItemUnit.LITERS,
                    onUnitChange = {
                        selectedItemUnit = it
                        onEvent(InventoryItemEvent.SetUnit(it))
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
                value = ProductAmountTextState,
                onValueChange = {
                    if(it.isEmpty()) {
                        ProductAmountTextState = ""
                        onEvent(InventoryItemEvent.SetAmount(0f))
                    }
                    else if (it.matches(pattern)) {
                        ProductAmountTextState = it
                        onEvent(InventoryItemEvent.SetAmount(it.toFloat()))
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
                text = "Expiration date",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            DatePickerField(
                    selectedDate = state.expirationDate.toString(),
                    onDateSelected = { onEvent(InventoryItemEvent.SetExpirationDate(convertDate(it))) }
                )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Price",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            TextField(
                value = PriceTextState,
                onValueChange = {
                    if(it.isEmpty()) {
                        PriceTextState = ""
                        onEvent(InventoryItemEvent.SetPrice(0f))
                    }
                    else if (it.matches(pattern)) {
                        PriceTextState = it
                        onEvent(InventoryItemEvent.SetPrice(it.toFloat()))
                    }

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .width(250.dp)
                    .padding(4.dp),
                label = { Text("Enter product price (optional)", fontSize = 12.sp) }
            )

            Spacer(modifier = Modifier.height(8.dp))

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
                    expDate = state.expirationDate.toString(),
                    price = state.price.toString(),
                    productsList = productNames
                )

                if (errorMessage.isEmpty()) {

//                    onEvent(InventoryItemEvent.SetProduct(productName))
//                    onEvent(InventoryItemEvent.SetAmount(ProductAmountTextState.text.toFloat()))
//                    onEvent(InventoryItemEvent.SetExpirationDate(convertDate(selectedDate)))
//                    if (PriceTextState.text.isNotEmpty())
//                        onEvent(InventoryItemEvent.SetPrice(PriceTextState.text.toFloat()))

                    onEvent(InventoryItemEvent.SaveInventoryItem)

                    navController.navigate(FoodScreen)
                }
            }
                , width = 400.dp)

        }
    }
}


@Composable
fun AutoCompleteTextField(
    items: List<String>, // List of items for suggestions
    modifier: Modifier = Modifier,
    onItemSelected: (String) -> Unit = {},
    inventoryItemViewModel: InventoryItemViewModel
) {
    val state by inventoryItemViewModel.state.collectAsState()
    val onEvent = inventoryItemViewModel::onEvent

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
                onEvent(InventoryItemEvent.SetProduct(it))
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
fun AutoCompleteTextFieldProducts(
    onProductSelected: (String) -> Unit,
    productState: ProductState,
    inventoryItemViewModel: InventoryItemViewModel,
    modifier: Modifier = Modifier
) {
    val products = productState.products
    val foodItems: List<String> = products.map { it.name }
    AutoCompleteTextField(
        items = foodItems,
        modifier = modifier,
        onItemSelected = { selectedItem ->
            onProductSelected(selectedItem)
        },
        inventoryItemViewModel
    )
}


@Composable
fun UnitOption(
    text: String,
    value: ItemUnit,
    isSelected: Boolean,
    onUnitChange: (ItemUnit) -> Unit
) {
    val backgroundColor = if (isSelected) Color.Blue else MaterialTheme.colorScheme.primary
    val contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onPrimary

    CustomButton(
        text = text,
        onClick = { onUnitChange(value) },
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        width = 75.dp,
        fontSize = 12.sp
    )
}

@Composable
fun DatePickerField(selectedDate: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val datePickerState = remember { mutableStateOf(false) }

    if (datePickerState.value) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                onDateSelected(date)
                datePickerState.value = false
            },
            year,
            month,
            day
        )

        datePickerDialog.setOnCancelListener {
            datePickerState.value = false // Reset state on cancel
        }

        datePickerDialog.show()
    }

    Row(
        modifier = Modifier
            .clickable { datePickerState.value = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = selectedDate.ifEmpty { "Click here to select an expiration date" },
            fontSize = 12.sp,
            modifier = Modifier
                .border(
                    BorderStroke(1.dp, color = Color.Gray),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(4.dp)

        )
    }
}

fun error(
    productName: String,
    amount: String,
    expDate: String,
    price: String,
    productsList: List<String>
): String {

    if (productName.isEmpty() || !productsList.contains(productName)){
        return "Valid product name is required!"
    }

    if (amount.isEmpty()){
        return "Product amount is required!"
    }

    try {
        val floatAmount = amount.toFloat()
        if (floatAmount <= 0){
            return "Amount can't be negative, zero or empty!"
        }
    }
    catch (e: NumberFormatException) {
        return "Incorrect amount!"
    }

    if (expDate.isEmpty()){
        return "Expiration date is required!"
    }

    val formatter = DateTimeFormatter.ofPattern("yyyy-M-d").withResolverStyle(ResolverStyle.LENIENT)
    val expDateParsed = LocalDate.parse(expDate, formatter)
    val today = LocalDate.now()

    if(expDateParsed.isBefore(today)){
        return "Expiration date can't be in the past!"
    }

    if (price.isNotEmpty()){
        try {
            val intPrice = price.toDouble()
            if (intPrice < 0){
                return "Price can't be negative!"
            }
        }
        catch (e: NumberFormatException){
            return "Incorrect price!"
        }
    }

    return ""

}

fun convertDate(date: String): Date {
    val inputFormats = listOf(
        DateTimeFormatter.ofPattern("yyyy-M-d"),
        DateTimeFormatter.ofPattern("yyyy-MM-d"),
        DateTimeFormatter.ofPattern("yyyy-M-dd"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd")
    )
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    for (format in inputFormats) {
        try {
            val parsedDate = LocalDate.parse(date, format)
            return Date.valueOf(parsedDate.format(outputFormatter))
        } catch (e: DateTimeParseException) {
            // Ignore
        }
    }
    return Date.valueOf("2100-01-01")
}

