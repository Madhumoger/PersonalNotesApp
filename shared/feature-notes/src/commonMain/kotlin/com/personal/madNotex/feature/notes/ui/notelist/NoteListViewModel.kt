package com.personal.madNotex.feature.notes.ui.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.madNotex.core.localization.Strings
import com.personal.madNotex.feature.notes.domain.model.Note
import com.personal.madNotex.feature.notes.domain.useCases.DeleteNoteUseCase
import com.personal.madNotex.feature.notes.domain.useCases.GetNotesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<NoteListViewState>(NoteListViewState.Loading)
    val viewState: StateFlow<NoteListViewState> = _viewState.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            getNotesUseCase()
                .catch { error ->
                    _viewState.value = NoteListViewState.Error(error.message ?: Strings.SOMETHING_WENT_WRONG)
                }
                .collect { notes ->
                    _viewState.value = if (notes.isEmpty()) {
                        NoteListViewState.Empty
                    } else {
                        NoteListViewState.Success(notes)
                    }
                }
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            deleteNoteUseCase(noteId)
                .onSuccess {
                    // Note will be automatically updated via Flow
                }
                .onFailure { error ->
                    _viewState.value = NoteListViewState.Error(error.message ?: Strings.FAILED_TO_DELETE_NOTE)
                }
        }
    }
}

sealed class NoteListViewState {
    data object Loading : NoteListViewState()
    data object Empty : NoteListViewState()
    data class Success(val notes: List<Note>) : NoteListViewState()
    data class Error(val message: String) : NoteListViewState()
}

