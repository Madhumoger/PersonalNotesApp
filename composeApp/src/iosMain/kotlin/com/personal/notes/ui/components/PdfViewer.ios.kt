package com.personal.notes.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cValue
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PdfViewer(
    url: String,
    modifier: Modifier
) {
    UIKitView(
        modifier = modifier,
        factory = {
            val configuration = WKWebViewConfiguration()
            WKWebView(
                frame = cValue { CGRectMake(0.0, 0.0, 0.0, 0.0) },
                configuration = configuration
            )
        },
        update = { webView ->
            val nsUrl = NSURL.URLWithString(url)
            nsUrl?.let {
                val request = NSURLRequest.requestWithURL(it)
                webView.loadRequest(request)
            }
        }
    )
}
