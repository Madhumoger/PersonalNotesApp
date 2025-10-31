package com.personal.notes.ui.pdfviewer

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.personal.notes.ui.components.PdfViewer

/**
 * PDF Viewer Screen - displays a PDF from a URL
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfViewerScreen(
    onNavigateBack: () -> Unit
) {
    val pdfUrl = "https://qa.pilloo.ai/GeneratedPDF/Companies/202/2025-2026/DL.pdf"
    var isLoading by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PDF Viewer") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                ) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    )
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                PdfViewer(
                    url = pdfUrl,
                    modifier = Modifier.fillMaxSize(),
                    onLoadingStateChange = { loading ->
                        isLoading = loading
                    }
                )
            }
        }
    }
}