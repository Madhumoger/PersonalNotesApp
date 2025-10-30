package com.personal.notes.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun HtmlViewer(
    html: String,
    onMessageReceived: (String) -> Unit,
    modifier: Modifier = Modifier
)
