package com.personal.notes.ui.components

import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


@Composable
actual fun HtmlViewer(
    html: String,
    onMessageReceived: (String) -> Unit,
    modifier: Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = WebViewClient()

                addJavascriptInterface(
                    object {
                        @JavascriptInterface
                        fun postMessage(message: String) {
                            onMessageReceived(message)
                        }
                    },
                    "JavaScriptBridge"
                )
            }
        },
        update = { webView ->
            webView.loadDataWithBaseURL(
                null,
                html,
                "text/html",
                "UTF-8",
                null
            )
        }
    )
}
