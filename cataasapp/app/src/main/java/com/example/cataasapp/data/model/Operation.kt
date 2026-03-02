package com.example.cataasapp.data.model

data class Operation(
    val id: Int,
    val name: String,
    val description: String,
    val endpoint: String,          // e.g. /cat, /cat/{tag}/says/{text}
    val needsTag: Boolean = false,
    val needsText: Boolean = false,
    val needsFontParams: Boolean = false
)