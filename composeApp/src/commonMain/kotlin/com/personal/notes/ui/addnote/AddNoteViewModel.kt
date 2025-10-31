package com.personal.notes.ui.addnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.notes.data.model.Note
import com.personal.notes.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant


class AddNoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddNoteUiState())
    val uiState: StateFlow<AddNoteUiState> = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun updateBody(body: String) {
        _uiState.value = _uiState.value.copy(body = body)
    }

    fun updateCreatedDate(timestamp: Long) {
        _uiState.value = _uiState.value.copy(createdDateMillis = timestamp)
    }

    fun saveNote(onSuccess: (Long) -> Unit, onError: (String) -> Unit) {
        val currentState = _uiState.value

        if (currentState.title.isBlank()) {
            onError("Title cannot be empty")
            return
        }

        if (currentState.body.isBlank()) {
            onError("Body cannot be empty")
            return
        }

        viewModelScope.launch {
            try {
                val note = Note(
                    title = currentState.title,
                    body = currentState.body,
                    createdDate = Instant.fromEpochMilliseconds(currentState.createdDateMillis)
                )
                val noteId = noteRepository.insertNote(note)
                onSuccess(noteId)
            } catch (e: Exception) {
                onError(e.message ?: "Failed to save note")
            }
        }
    }
}

data class AddNoteUiState(
    val title: String = "",
    val body: String = "",
    val createdDateMillis: Long = Clock.System.now().toEpochMilliseconds()
)