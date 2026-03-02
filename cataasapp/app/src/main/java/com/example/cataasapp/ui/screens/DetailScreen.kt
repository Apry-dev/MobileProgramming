package com.example.cataasapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cataasapp.R
import com.example.cataasapp.ui.components.LoadingIndicator
import com.example.cataasapp.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel = viewModel()
) {
    val operation by viewModel.operation.collectAsState()
    val imageUrl by viewModel.imageUrl.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val tag by viewModel::tag
    val text by viewModel::text
    val fontSize by viewModel::fontSize
    val fontColor by viewModel::fontColor

    // State for image loading (from Coil)
    var isImageLoading by remember { mutableStateOf(false) }
    var imageLoadError by remember { mutableStateOf(false) }

    // Use a key to force recomposition when URL changes
    val imageKey = remember(imageUrl) { imageUrl }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(operation?.name ?: "Cat") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Input fields based on operation
            operation?.let { op ->
                if (op.needsTag) {
                    OutlinedTextField(
                        value = tag,
                        onValueChange = { viewModel.tag = it },
                        label = { Text(stringResource(R.string.enter_tag)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if (op.needsText) {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { viewModel.text = it },
                        label = { Text(stringResource(R.string.enter_text)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if (op.needsFontParams) {
                    OutlinedTextField(
                        value = fontSize,
                        onValueChange = { viewModel.fontSize = it },
                        label = { Text(stringResource(R.string.enter_font_size)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = fontColor,
                        onValueChange = { viewModel.fontColor = it },
                        label = { Text(stringResource(R.string.enter_font_color)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Button(
                    onClick = {
                        imageLoadError = false
                        isImageLoading = true
                        viewModel.generateUrl()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.load_cat))
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // Image display with Coil
                if (imageUrl.isNotBlank()) {
                    key(imageKey) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Cat image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            onLoading = { isImageLoading = true },
                            onSuccess = {
                                isImageLoading = false
                                imageLoadError = false
                            },
                            onError = {
                                isImageLoading = false
                                imageLoadError = true
                            }
                        )
                    }
                    // Show spinner while loading
                    if (isImageLoading) {
                        LoadingIndicator()
                    }
                    // Show error message if image failed
                    if (imageLoadError) {
                        Text(
                            text = stringResource(R.string.error_loading),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}