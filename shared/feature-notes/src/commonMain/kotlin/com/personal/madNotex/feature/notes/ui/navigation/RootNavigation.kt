package com.personal.madNotex.feature.notes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.personal.madNotex.core.common.GeneralUtils
import com.personal.madNotex.core.navigation.NotesJourney
import com.personal.madNotex.core.navigation.NotesRouterImpl
import com.personal.madNotex.feature.notes.ui.addnote.AddNoteScreen
import com.personal.madNotex.feature.notes.ui.notedetail.NoteDetailScreen
import com.personal.madNotex.feature.notes.ui.notelist.NoteListScreen
import com.personal.madNotex.feature.notes.ui.pdfviewer.PdfViewerScreen
import com.personal.madNotex.feature.notes.ui.theme.NotesAppTheme

@Composable
fun NotesNavHost(
    modifier: Modifier = Modifier
) {
    NotesAppTheme {
        val navController = rememberNavController()
        val notesRouter = NotesRouterImpl(navController)
        val transitions = GeneralUtils.getDefaultNavigationTransitions()

        NavHost(
            navController = navController,
            startDestination = NotesJourney.NoteList,
            modifier = modifier
        ) {
            composable<NotesJourney.NoteList>(
                enterTransition = transitions.enterTransition,
                exitTransition = transitions.exitTransition,
                popEnterTransition = transitions.popEnterTransition,
                popExitTransition = transitions.popExitTransition
            ) {
                NoteListScreen(
                    onNoteClick = { noteId ->
                        notesRouter.navigateToNoteDetail(noteId)
                    },
                    onAddNoteClick = {
                        notesRouter.navigateToAddNote()
                    },
                    onPdfViewerClick = {
                        notesRouter.navigateToPdfViewer()
                    }
                )
            }

            composable<NotesJourney.AddNote>(
                enterTransition = transitions.enterTransition,
                exitTransition = transitions.exitTransition,
                popEnterTransition = transitions.popEnterTransition,
                popExitTransition = transitions.popExitTransition
            ) {
                AddNoteScreen(
                    onNavigateBack = {
                        notesRouter.onBackNavigation()
                    },
                    onNoteSaved = { noteId ->
                        notesRouter.onBackNavigation()
                        notesRouter.navigateToNoteDetail(noteId)
                    }
                )
            }

            composable<NotesJourney.NoteDetail>(
                enterTransition = transitions.enterTransition,
                exitTransition = transitions.exitTransition,
                popEnterTransition = transitions.popEnterTransition,
                popExitTransition = transitions.popExitTransition
            ) { backStackEntry ->
                val args = backStackEntry.toRoute<NotesJourney.NoteDetail>()
                NoteDetailScreen(
                    noteId = args.noteId,
                    onNavigateBack = {
                        notesRouter.onBackNavigation()
                    }
                )
            }

            composable<NotesJourney.PdfViewer>(
                enterTransition = transitions.enterTransition,
                exitTransition = transitions.exitTransition,
                popEnterTransition = transitions.popEnterTransition,
                popExitTransition = transitions.popExitTransition
            ) {
                PdfViewerScreen(
                    onNavigateBack = {
                        notesRouter.onBackNavigation()
                    }
                )
            }
        }
    }
}

