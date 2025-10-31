package com.personal.madNotex.di

import com.personal.madNotex.core.database.DatabaseDriverFactory
import org.koin.dsl.module

val platformModule = module {
    single { DatabaseDriverFactory() }
}

