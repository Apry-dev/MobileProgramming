package com.example.week5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week5.ui.theme.Week5Theme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week5Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AlcometerScreen()
                }
            }
        }
    }
}

@Composable
fun AlcometerScreen() {
    var weight by remember { mutableStateOf("") }
    var bottles by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var isMale by remember { mutableStateOf(true) }
    var result by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Alcometer",
            fontSize = 28.sp,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = bottles,
            onValueChange = { bottles = it },
            label = { Text("Bottles (0.33l)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = time,
            onValueChange = { time = it },
            label = { Text("Hours") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            RadioButton(
                selected = isMale,
                onClick = { isMale = true }
            )

            Text(
                text = "Male",
                modifier = Modifier.padding(end = 16.dp)
            )

            RadioButton(
                selected = !isMale,
                onClick = { isMale = false }
            )
            Text(text = "Female")
        }

        Button(
            onClick = {
                result = calculateBloodAlcohol(
                    weight = weight.toDoubleOrNull() ?: 0.0,
                    bottles = bottles.toIntOrNull() ?: 0,
                    time = time.toDoubleOrNull() ?: 0.0,
                    isMale = isMale
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("CALCULATE")
        }

        if (result > 0) {
            Text(
                text = String.format(Locale.getDefault(), "%.2f", result),
                fontSize = 48.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}

fun calculateBloodAlcohol(weight: Double, bottles: Int, time: Double, isMale: Boolean): Double {
    if (weight <= 0 || bottles <= 0) return 0.0

    val litres = bottles * 0.33
    val grams = litres * 8 * 4.5
    val burning = weight / 10
    val gramsLeft = grams - (burning * time)

    if (gramsLeft <= 0) return 0.0

    val genderCoefficient = if (isMale) 0.7 else 0.6
    val result = gramsLeft / (weight * genderCoefficient)

    return result
}