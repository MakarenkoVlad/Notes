package vlad.makarenko.notes

import androidx.navigation.NavType
import androidx.navigation.compose.navArgument

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object NewNote : Screen("new_note_screen")

    data class EditNote(val id: Int) : Screen("$host/$id") {

        companion object {

            const val host = "edit_note_screen"

            val idArgument = navArgument("id") {
                type = NavType.IntType
            }

            val signature = "$host/{${idArgument.name}}"
        }
    }
}