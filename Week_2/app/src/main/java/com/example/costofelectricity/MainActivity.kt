package com.example.costofelectricity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.costofelectricity.ui.theme.CostOfElectricityTheme
import kotlin.math.ceil
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CostOfElectricityTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    CostOfElectricity(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CostOfElectricity(modifier: Modifier = Modifier) {

    var consumption by remember { mutableStateOf("1000") }
    var price by remember { mutableFloatStateOf(0.25f) }
    var reducedVat by remember { mutableStateOf(true) }

    // Rounding
    val roundedPrice = ceil(price * 100) / 100

    // VAT pc
    val vatRate = if (reducedVat) 0.10 else 0.24

    // cost
    val consumptionValue = consumption.toDoubleOrNull() ?: 0.0
    val cost = consumptionValue * roundedPrice

    // VAT
    val vat = cost * vatRate
    val payment = cost + vat

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(text = "Cost of electricity")

        OutlinedTextField(
            value = consumption,
            onValueChange = { consumption = it },
            label = { Text("Consumption in kWh") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Price/kWh: ${String.format("%.2f", roundedPrice)}")

        Slider(
            value = price,
            onValueChange = { price = it },
            valueRange = 0.01f..1.00f
        )

        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Checkbox(
                checked = reducedVat,
                onCheckedChange = { reducedVat = it }
            )

            Text(text = "VAT 10%")
        }

        Surface(
            color = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "${String.format("%.2f", payment)} €",
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CostOfElectricityPreview() {
    CostOfElectricityTheme {
        CostOfElectricity()
    }
}
