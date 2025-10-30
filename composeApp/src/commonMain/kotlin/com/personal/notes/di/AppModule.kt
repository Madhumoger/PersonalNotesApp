package com.personal.notes.di

import com.personal.notes.data.local.DatabaseDriverFactory
import com.personal.notes.data.repository.NoteRepository
import com.personal.notes.data.repository.NoteRepositoryImpl
import com.personal.notes.database.NotesDatabase
import com.personal.notes.ui.addnote.AddNoteViewModel
import com.personal.notes.ui.notelist.NoteListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Database
    single { get<DatabaseDriverFactory>().createDriver() }
    single { NotesDatabase(get()) }
    
    // Repository
    single<NoteRepository> { NoteRepositoryImpl(get()) }
    
    // ViewModels
    viewModel { NoteListViewModel(get()) }
    viewModel { AddNoteViewModel(get()) }
}
