package vlad.makarenko.notes.ui.note

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import vlad.makarenko.notes.data.Note
import vlad.makarenko.notes.viewmodel.AddNoteViewModel

@Composable
fun NewNoteBody(navController: NavController, viewModel: AddNoteViewModel = hiltViewModel()) {
    val note by viewModel.note.observeAsState(Note(title = "", description = ""))
    NoteBody(
        note = note,
        onChangeNote = viewModel::changeNote,
        onClickSaveNote = {
            viewModel.saveNote()
            navController.navigateUp()
        },
    )
}