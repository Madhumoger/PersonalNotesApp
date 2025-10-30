package com.personal.notes

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.personal.notes.ui.addnote.AddNoteScreen
import com.personal.notes.ui.navigation.Screen
import com.personal.notes.ui.notedetail.NoteDetailScreen
import com.personal.notes.ui.notelist.NoteListScreen
import com.personal.notes.ui.pdfviewer.PdfViewerScreen
import com.personal.notes.ui.theme.NotesAppTheme

@Composable
fun App() {
    NotesAppTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screen.NoteList.route
        ) {
            // Notes List Screen
            composable(route = Screen.NoteList.route) {
                NoteListScreen(
                    onNoteClick = { noteId ->
                        navController.navigate(Screen.NoteDetail.createRoute(noteId))
                    },
                    onAddNoteClick = {
                        navController.navigate(Screen.AddNote.route)
                    },
                    onPdfViewerClick = {
                        navController.navigate(Screen.PdfViewer.route)
                    }
                )
            }

            // Add Note Screen
            composable(route = Screen.AddNote.route) {
                AddNoteScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNoteSaved = { noteId ->
                        navController.popBackStack()
                        navController.navigate(Screen.NoteDetail.createRoute(noteId))
                    }
                )
            }

            // Note Detail Screen
            composable(
                route = Screen.NoteDetail.ROUTE,
                arguments = listOf(
                    navArgument("noteId") {
                        type = NavType.LongType
                    }
                )
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getLong("noteId") ?: 0L
                NoteDetailScreen(
                    noteId = noteId,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            // PDF Viewer Screen
            composable(route = Screen.PdfViewer.route) {
                PdfViewerScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}