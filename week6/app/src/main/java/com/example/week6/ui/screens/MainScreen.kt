package com.example.week6.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.week6.ui.components.MainTopAppBar

@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        topBar = {
            MainTopAppBar(
                title = "Main Screen",
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(text = "This is the Main Screen content.")
        }
    }
}