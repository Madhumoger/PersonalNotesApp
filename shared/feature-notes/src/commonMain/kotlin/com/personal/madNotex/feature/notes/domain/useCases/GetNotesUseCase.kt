package com.personal.madNotex.feature.notes.domain.useCases

import com.personal.madNotex.feature.notes.domain.model.Note
import com.personal.madNotex.feature.notes.data.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getAllNotes()
    }
}

