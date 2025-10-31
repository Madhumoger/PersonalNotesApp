package com.personal.notes.ui.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.notes.data.model.Note
import com.personal.notes.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<NoteListUiState>(NoteListUiState.Loading)
    val uiState: StateFlow<NoteListUiState> = _uiState.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            noteRepository.getAllNotes()
                .catch { error ->
                    _uiState.value = NoteListUiState.Error(error.message ?: "Something went wrong")
                }
                .collect { notes ->
                    _uiState.value = if (notes.isEmpty()) {
                        NoteListUiState.Empty
                    } else {
                        NoteListUiState.Success(notes)
                    }
                }
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            try {
                noteRepository.deleteNote(noteId)
            } catch (e: Exception) {
                _uiState.value = NoteListUiState.Error(e.message ?: "Failed to delete note")
            }
        }
    }
}

sealed class NoteListUiState {
    data object Loading : NoteListUiState()
    data object Empty : NoteListUiState()
    data class Success(val notes: List<Note>) : NoteListUiState()
    data class Error(val message: String) : NoteListUiState()
}
