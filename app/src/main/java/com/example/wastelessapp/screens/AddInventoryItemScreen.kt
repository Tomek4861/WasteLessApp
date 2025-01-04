package com.example.wastelessapp.screens

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.example.wastelessapp.ui.components.FoodUnit
import com.example.wastelessapp.ui.components.PrimaryButton
import com.example.wastelessapp.ui.components.SecondaryButton
import java.util.Calendar

@Serializable
object AddInventoryItemScreen

@Composable
fun AddInventoryItemScreen(navController: NavHostController) {
    var selectedDate by remember { mutableStateOf("") }

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
            AutoCompleteTextFieldProducts()
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
                UnitOption("Grams", FoodUnit.GRAM)
                Spacer(modifier = Modifier.width(8.dp))
                UnitOption("Milliliters", FoodUnit.MILLILITER)
                Spacer(modifier = Modifier.width(8.dp))
                UnitOption("Pieces", FoodUnit.PCS)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Amount",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            var ProductAmountTextState by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                value = ProductAmountTextState,
                onValueChange = {
                    ProductAmountTextState = it
                },
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
                    selectedDate = selectedDate,
                    onDateSelected = { date -> selectedDate = date }
                )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Price",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            var PriceTextState by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                value = PriceTextState,
                onValueChange = {
                    PriceTextState = it
                },
                modifier = Modifier
                    .width(250.dp)
                    .padding(4.dp),
                label = { Text("Enter product price (optional)", fontSize = 12.sp) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton("Add Item", onClick = {
                // TODO add Item to database
                navController.navigate(FoodScreen)
            }
                , width = 400.dp)

        }
    }
}


@Composable
fun AutoCompleteTextField(
    items: List<String>, // List of items for suggestions
    modifier: Modifier = Modifier,
    onItemSelected: (String) -> Unit = {}
) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    var expanded by remember { mutableStateOf(false) }
    val filteredItems = remember(textState.text) {
        items.filter { it.startsWith(textState.text, ignoreCase = true) }
    }

    Column(modifier = modifier) {
        TextField(
            value = textState,
            onValueChange = {
                textState = it
                expanded = filteredItems.isNotEmpty() // Show dropdown if there are suggestions
            },
            modifier = Modifier
                .fillMaxWidth(),
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
                        textState = TextFieldValue(item)
                        expanded = false
                        onItemSelected(item)
                    },
                    text = { Text(item) }
                )
            }
        }
    }
}


@Composable
fun AutoCompleteTextFieldProducts() {
    val foodItems = listOf("Tomato", "Tuna", "Toast", "Turkey", "Tofu")
    AutoCompleteTextField(
        items = foodItems,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) { selectedItem ->
        println("Selected Item: $selectedItem")
    }
}

@Composable
fun UnitOption(
    text: String,
    value: FoodUnit
) {
    SecondaryButton(text = text, onClick = { /* TODO: */ }, width = 100.dp, fontSize = 12.sp)
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

        android.app.DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                onDateSelected(date)
                datePickerState.value = false
            },
            year,
            month,
            day
        ).show()
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




