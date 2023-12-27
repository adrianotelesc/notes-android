package com.adrianotelesc.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.adrianotelesc.notes.ui.screen.noteediting.NoteEditingScreen
import com.adrianotelesc.notes.ui.screen.notes.NotesScreen
import com.adrianotelesc.notes.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "notes",
                    ) {
                        composable(route = "notes") {
                            NotesScreen(
                                newNote = {
                                    navController.navigate("note_editing/{id}")
                                },
                                openNote = { id ->
                                    navController.navigate("note_editing/$id")
                                },
                            )
                        }
                        composable(
                            route = "note_editing/{id}",
                            arguments = listOf(
                                navArgument(name = "id") {
                                    type = NavType.StringType
                                    defaultValue = null
                                    nullable = true
                                },
                            )
                        ) { backStackEntry ->
                            NoteEditingScreen(
                                navigateUp = navController::navigateUp,
                                noteId = backStackEntry.arguments?.getString("id"),
                            )
                        }
                    }
                }
            }
        }
    }
}
