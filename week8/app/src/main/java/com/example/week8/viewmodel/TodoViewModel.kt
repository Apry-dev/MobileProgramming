package com.example.week8.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week8.model.Todo
import com.example.week8.model.TodosApi
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    var todos = mutableStateListOf<Todo>()
        private set

    init {
        getTodosList()
    }

    private fun getTodosList() {
        viewModelScope.launch {
            try {
                val todosApi = TodosApi.getInstance()
                todos.clear()
                todos.addAll(todosApi.getTodos())
            } catch (e: Exception) {
                Log.e("TodoViewModel", "Error fetching todos", e)
                // Error handling will be added in part 3
            }
        }
    }
}