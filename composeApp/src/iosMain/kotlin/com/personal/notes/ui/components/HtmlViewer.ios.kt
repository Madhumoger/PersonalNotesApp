package com.personal.notes.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cValue
import platform.CoreGraphics.CGRectMake
import platform.WebKit.*
import platform.WebKit.WKAudiovisualMediaTypesNone
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun HtmlViewer(
    html: String,
    onMessageReceived: (String) -> Unit,
    modifier: Modifier
) {
    val messageHandler = remember {
        object : NSObject(), WKScriptMessageHandlerProtocol {
            override fun userContentController(
                userContentController: WKUserContentController,
                didReceiveScriptMessage: WKScriptMessage
            ) {
                val message = didReceiveScriptMessage.body as? String
                message?.let { onMessageReceived(it) }
            }
        }
    }

    UIKitView(
        modifier = modifier,
        factory = {
            val configuration = WKWebViewConfiguration()
            val userContentController = WKUserContentController()

            configuration.allowsInlineMediaPlayback = true
            configuration.mediaTypesRequiringUserActionForPlayback = WKAudiovisualMediaTypesNone
            configuration.suppressesIncrementalRendering = false

            userContentController.addScriptMessageHandler(
                messageHandler,
                "JavaScriptBridge"
            )

            val injectionTime = WKUserScriptInjectionTime.WKUserScriptInjectionTimeAtDocumentStart

            val script = """
                window.JavaScriptBridge = {
                    postMessage: function(msg) {
                        if (window.webkit && window.webkit.messageHandlers && window.webkit.messageHandlers.JavaScriptBridge) {
                            window.webkit.messageHandlers.JavaScriptBridge.postMessage(msg);
                        }
                    }
                };
            """.trimIndent()

            val userScript = WKUserScript(
                source = script,
                injectionTime = injectionTime,
                forMainFrameOnly = true
            )
            userContentController.addUserScript(userScript)

            configuration.userContentController = userContentController

            WKWebView(
                frame = cValue { CGRectMake(0.0, 0.0, 0.0, 0.0) },
                configuration = configuration
            )
        },
        update = { webView ->
            webView.loadHTMLString(html, null)
        }
    )
}
