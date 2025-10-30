package com.personal.notes

import androidx.compose.ui.window.ComposeUIViewController
import com.personal.notes.di.appModule
import com.personal.notes.di.platformModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}

private fun initKoin() {
    try {
        startKoin {
            modules(platformModule, appModule)
        }
    } catch (e: Exception) {
        // Koin already initialized
    }
}