package com.example.cataasapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cataasapp.data.model.Operation
import com.example.cataasapp.data.repository.CatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel : ViewModel() {
    private val repository = CatRepository()

    private val _operation = MutableStateFlow<Operation?>(null)
    val operation: StateFlow<Operation?> = _operation.asStateFlow()

    // Input fields
    var tag by mutableStateOf("")
    var text by mutableStateOf("")
    var fontSize by mutableStateOf("50")
    var fontColor by mutableStateOf("red")
    private val _imageUrl = MutableStateFlow("")
    val imageUrl: StateFlow<String> = _imageUrl.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun setOperation(op: Operation) {
        _operation.value = op
        tag = ""
        text = ""
        fontSize = "50"
        fontColor = "red"
        _errorMessage.value = null
    }

    fun generateUrl() {
        val op = _operation.value ?: return

        if (op.needsTag && tag.isBlank()) {
            _errorMessage.value = "Please enter a tag"
            return
        }
        if (op.needsText && text.isBlank()) {
            _errorMessage.value = "Please enter some text"
            return
        }
        _errorMessage.value = null
        val url = repository.buildUrl(op, tag, text, fontSize, fontColor)
        _imageUrl.value = url
    }
}