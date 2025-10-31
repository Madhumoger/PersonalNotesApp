package com.personal.notes.ui.navigation

import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

interface NotesRouter {
    fun navigateToNoteList()
    fun navigateToAddNote()
    fun navigateToNoteDetail(noteId: Long)
    fun navigateToPdfViewer()
    fun onBackNavigation()
}

class NotesRouterImpl(
    private val navHostController: NavHostController,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : NotesRouter {
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    override fun navigateToNoteList() {
        scope.launch {
            navHostController.navigate(NotesJourney.NoteList) {
                popUpTo(NotesJourney.NoteList) {
                    inclusive = true
                }
            }
        }
    }

    override fun navigateToAddNote() {
        scope.launch {
            navHostController.navigate(NotesJourney.AddNote)
        }
    }

    override fun navigateToNoteDetail(noteId: Long) {
        scope.launch {
            navHostController.navigate(NotesJourney.NoteDetail(noteId = noteId))
        }
    }

    override fun navigateToPdfViewer() {
        scope.launch {
            navHostController.navigate(NotesJourney.PdfViewer)
        }
    }

    override fun onBackNavigation() {
        scope.launch {
            if (navHostController.previousBackStackEntry != null) {
                navHostController.popBackStack()
            }
        }
    }
}