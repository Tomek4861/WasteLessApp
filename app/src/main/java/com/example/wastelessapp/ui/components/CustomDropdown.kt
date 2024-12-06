package com.example.wastelessapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wastelessapp.ui.components.CustomButton

@Composable
fun CustomDropdownMenu(options: List<String>,onOptionSelected: (String) -> Unit, width: Dp =200.dp, ) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by rememberSaveable  { mutableStateOf(options[0]) }

    Column(
        modifier = Modifier
    ) {
        SecondaryButton(
            onClick = { expanded = !expanded  },
            text = selectedOption,
            icon = if (expanded) Icons.Default.ArrowDropDown
                    else Icons.Default.ArrowDropDown, //this if is necessary for constant arrow display idk why
            width = width
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option, ) },
                    onClick = {
                        onOptionSelected(option)
                        selectedOption = option
                        expanded = false
                    },

                    modifier = Modifier.width(width)

                )
            }
        }
    }
}
