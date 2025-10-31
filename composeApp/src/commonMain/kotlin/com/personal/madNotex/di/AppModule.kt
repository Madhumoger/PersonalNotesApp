package com.personal.madNotex.di

import com.personal.madNotex.feature.notes.di.notesModule
import org.koin.dsl.module

val appModule = module {
    includes(notesModule)
}

