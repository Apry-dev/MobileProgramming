package com.example.cataasapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cataasapp.data.model.Operation
import com.example.cataasapp.data.repository.CatRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    onOperationSelected: (Operation) -> Unit
) {
    val repository = CatRepository()
    val operations = repository.getOperations()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Cat Operations") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("info") }) {
                Text("i")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(operations) { op ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOperationSelected(op) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = op.name, style = MaterialTheme.typography.titleLarge)
                        Text(text = op.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}