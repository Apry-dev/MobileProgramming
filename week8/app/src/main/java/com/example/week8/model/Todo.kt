package com.example.week8.model

import com.google.gson.annotations.SerializedName

data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)