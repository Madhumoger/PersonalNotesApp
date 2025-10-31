package com.personal.notes.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCSignatureOverride
import kotlinx.cinterop.cValue
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.*
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PdfViewer(
    url: String,
    modifier: Modifier,
    onLoadingStateChange: (Boolean) -> Unit
) {
    val nsUrl = remember(url) { NSURL.URLWithString(url) }

    LaunchedEffect(url) {
        onLoadingStateChange(true)
    }

    UIKitView(
        modifier = modifier,
        factory = {
            val configuration = WKWebViewConfiguration()
            val webView = WKWebView(
                frame = cValue { CGRectMake(0.0, 0.0, 0.0, 0.0) },
                configuration = configuration
            )

            val navigationDelegate = object : NSObject(), WKNavigationDelegateProtocol {
                @ObjCSignatureOverride
                override fun webView(webView: WKWebView, didFinishNavigation: WKNavigation?) {
                    onLoadingStateChange(false)
                }

                @ObjCSignatureOverride
                override fun webView(webView: WKWebView, didStartProvisionalNavigation: WKNavigation?) {
                    onLoadingStateChange(true)
                }

                @ObjCSignatureOverride
                override fun webView(
                    webView: WKWebView,
                    didFailNavigation: WKNavigation?,
                    withError: platform.Foundation.NSError
                ) {
                    onLoadingStateChange(false)
                }

                @ObjCSignatureOverride
                override fun webView(
                    webView: WKWebView,
                    didFailProvisionalNavigation: WKNavigation?,
                    withError: platform.Foundation.NSError
                ) {
                    onLoadingStateChange(false)
                }
            }
            
            webView.navigationDelegate = navigationDelegate
            webView
        },
        update = { webView ->
            nsUrl?.let {
                val request = NSURLRequest.requestWithURL(it)
                webView.loadRequest(request)
            }
        }
    )
}
