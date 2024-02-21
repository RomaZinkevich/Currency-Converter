package com.example.currencycalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencycalculator.ui.theme.CurrencyCalculatorTheme




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    var amount = remember { mutableStateOf("") }
    val currencies = listOf("EUR", "USD", "GBP", "RUB")
    val exchangeRates = mapOf("EUR" to 1.0, "USD" to 1.12, "GBP" to 0.90, "RUB" to 100.0)
    var expanded = remember { mutableStateOf(false) }
    var selectedCurrency = remember { mutableStateOf("USD") }
    var convertedAmount = remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Convert Euros to other currencies",
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
        Column(){
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = amount.value,
                    onValueChange = { amount.value = it },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                TextField(
                    value = selectedCurrency.value,
                    onValueChange = { /* Handle value change */ },
                    label = { Text(text = "Currency") },
                    readOnly = true,
                    trailingIcon = {
                        Text(
                            text = "x",
                            modifier = Modifier.clickable { expanded.value = !expanded.value }
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(modifier = Modifier.fillMaxWidth()){
                Spacer(modifier = Modifier.fillMaxWidth())
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    currencies.forEach { currency ->
                        DropdownMenuItem(
                            text = { Text(currency) },
                            onClick = {
                                selectedCurrency.value = currency
                                expanded.value = false
                            }
                        )
                    }
                }
            }
            
        }


        Spacer(modifier = Modifier.weight(1f))

        Text(
            "Converted amount: ${formatDouble(convertedAmount.value)} ",
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                val amountValue = amount.value.toDoubleOrNull()
                if (amountValue != null) {
                    val rate = exchangeRates[selectedCurrency.value] ?: 1.0
                    convertedAmount.value = amountValue * rate
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Convert")
        }
    }
}


fun formatDouble(number: Double): String {
    val formattedString = String.format("%.3f", number)
    return if (formattedString.endsWith(".000")) {
        formattedString.substring(0, formattedString.length - 4)
    } else {
        formattedString
    }
}