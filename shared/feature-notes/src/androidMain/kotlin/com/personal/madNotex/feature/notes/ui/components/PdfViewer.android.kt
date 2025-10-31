package com.personal.madNotex.feature.notes.ui.components

import android.os.Build
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
@Composable
actual fun PdfViewer(
    url: String,
    modifier: Modifier,
    onLoadingStateChange: (Boolean) -> Unit
) {
    val viewerUrl = remember(url) {
        "https://docs.google.com/gview?embedded=true&url=$url"
    }
    
    LaunchedEffect(url) {
        onLoadingStateChange(true)
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.builtInZoomControls = true
                settings.displayZoomControls = false
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        onLoadingStateChange(false)
                    }

                    override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        onLoadingStateChange(true)
                    }
                }
            }
        },
        update = { webView ->
            if (webView.url != viewerUrl) {
                webView.loadUrl(viewerUrl)
            }
        }
    )
}

