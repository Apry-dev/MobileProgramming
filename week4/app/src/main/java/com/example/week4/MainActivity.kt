package com.example.week4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CostOfElectricityApp()
        }
    }
}

@Composable
fun CostOfElectricityApp() {

    var consumption by remember { mutableStateOf("") }
    var pricePerKwh by remember { mutableFloatStateOf(0.25f) }
    var isReducedVat by remember { mutableStateOf(false) }


    val payment = calculatePayment(consumption, pricePerKwh, isReducedVat)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "Cost of Electricity",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )


            OutlinedTextField(
                value = consumption,
                onValueChange = { consumption = it },
                label = { Text("Consumption (kWh)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )


            Text(
                text = "Price: %.2f €/kWh".format(pricePerKwh),
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )


            Slider(
                value = pricePerKwh,
                onValueChange = { pricePerKwh = it },
                valueRange = 0f..0.5f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Checkbox(
                    checked = isReducedVat,
                    onCheckedChange = { isReducedVat = it }
                )
                Text(
                    text = "Reduced VAT (10%)",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }


            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                color = Color(0xFFE3F2FD),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total Payment",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "%.2f €".format(payment),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1976D2)
                    )
                }
            }
        }
    }
}

fun calculatePayment(consumptionStr: String, pricePerKwh: Float, isReducedVat: Boolean): Double {

    val consumption = consumptionStr.toDoubleOrNull() ?: return 0.0


    val roundedPrice = (pricePerKwh * 100).roundToInt() / 100.0


    val cost = roundedPrice * consumption


    val vatPercentage = if (isReducedVat) 10.0 else 24.0
    val vat = (vatPercentage / 100.0) * cost


    return cost + vat
}