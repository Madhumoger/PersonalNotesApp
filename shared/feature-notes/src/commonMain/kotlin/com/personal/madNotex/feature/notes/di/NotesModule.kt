package com.personal.madNotex.feature.notes.di

import com.personal.madNotex.core.database.DatabaseDriverFactory
import com.personal.madNotex.database.NotesDatabase
import com.personal.madNotex.feature.notes.data.repository.NoteRepository
import com.personal.madNotex.feature.notes.data.repository.NoteRepositoryImpl
import com.personal.madNotex.feature.notes.domain.useCases.DeleteNoteUseCase
import com.personal.madNotex.feature.notes.domain.useCases.GetNoteByIdUseCase
import com.personal.madNotex.feature.notes.domain.useCases.GetNotesUseCase
import com.personal.madNotex.feature.notes.domain.useCases.SaveNoteUseCase
import com.personal.madNotex.feature.notes.ui.addnote.AddNoteViewModel
import com.personal.madNotex.feature.notes.ui.notedetail.NoteDetailViewModel
import com.personal.madNotex.feature.notes.ui.notelist.NoteListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val notesModule = module {
    // Database
    single { get<DatabaseDriverFactory>().createDriver() }
    single { NotesDatabase(get()) }

    // Repository
    single<NoteRepository> { NoteRepositoryImpl(get()) }

    // Use Cases
    factory { GetNotesUseCase(get()) }
    factory { GetNoteByIdUseCase(get()) }
    factory { SaveNoteUseCase(get()) }
    factory { DeleteNoteUseCase(get()) }

    // ViewModels
    viewModel { NoteListViewModel(get(), get()) }
    viewModel { AddNoteViewModel(get()) }
    viewModel { NoteDetailViewModel(get()) }
}

