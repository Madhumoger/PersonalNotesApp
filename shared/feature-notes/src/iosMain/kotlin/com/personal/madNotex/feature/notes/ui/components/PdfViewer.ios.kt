package com.personal.madNotex.feature.notes.ui.components

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
    val viewerUrl = remember(url) {
        "https://docs.google.com/gview?embedded=true&url=$url"
    }
    val nsUrl = remember(viewerUrl) { NSURL.URLWithString(viewerUrl) }

    LaunchedEffect(url) {
        onLoadingStateChange(true)
    }

    UIKitView(
        modifier = modifier,
        factory = {
            val configuration = WKWebViewConfiguration()
            configuration.allowsInlineMediaPlayback = true
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
            if (webView.url?.absoluteString != viewerUrl) {
                nsUrl?.let {
                    val request = NSURLRequest.requestWithURL(it)
                    webView.loadRequest(request)
                }
            }
        }
    )
}

