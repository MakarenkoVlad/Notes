package vlad.makarenko.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import vlad.makarenko.notes.ui.home.HomeBody
import vlad.makarenko.notes.ui.note.EditNoteBody
import vlad.makarenko.notes.ui.theme.NotesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesApp()
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun NotesApp() {
    NotesTheme() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                HomeBody(navController)
            }
            composable(Screen.NewNote.route) {
                EditNoteBody(navController = navController)
            }
            composable(
                Screen.EditNote.signature, arguments = listOf(
                    Screen.EditNote.idArgument
                )
            ) { entry ->
                entry.arguments?.getInt(Screen.EditNote.idArgument.name)?.let { id ->
                    EditNoteBody(
                        navController,
                        id
                    )
                }
            }
        }
    }
}