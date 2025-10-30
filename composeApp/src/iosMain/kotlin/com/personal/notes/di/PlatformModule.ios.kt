package com.personal.notes.di

import com.personal.notes.data.local.DatabaseDriverFactory
import org.koin.dsl.module

val platformModule = module {
    single { DatabaseDriverFactory() }
}
