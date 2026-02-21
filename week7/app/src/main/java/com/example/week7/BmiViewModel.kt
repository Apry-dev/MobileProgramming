package com.example.week7

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BmiViewModel : ViewModel() {

    var height by mutableStateOf("")
    var weight by mutableStateOf("")

    val bmi: Double
        get() {
            val h = height.toDoubleOrNull() ?: 0.0
            val w = weight.toDoubleOrNull() ?: 0.0
            return if (h > 0) w / (h * h) else 0.0
        }

    fun updateHeight(newHeight: String) { height = newHeight }
    fun updateWeight(newWeight: String) { weight = newWeight }
}