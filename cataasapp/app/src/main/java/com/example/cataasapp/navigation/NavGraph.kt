package com.example.cataasapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cataasapp.ui.screens.DetailScreen
import com.example.cataasapp.ui.screens.InfoScreen
import com.example.cataasapp.ui.screens.MainScreen
import com.example.cataasapp.viewmodel.DetailViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val detailViewModel: DetailViewModel = viewModel()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                navController = navController,
                onOperationSelected = { operation ->
                    detailViewModel.setOperation(operation)
                    navController.navigate("detail")
                }
            )
        }
        composable("detail") {
            DetailScreen(viewModel = detailViewModel)
        }
        composable("info") {
            InfoScreen(navController = navController)
        }
    }
}