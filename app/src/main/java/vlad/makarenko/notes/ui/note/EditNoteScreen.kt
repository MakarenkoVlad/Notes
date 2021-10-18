package vlad.makarenko.notes.ui.note

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import vlad.makarenko.notes.data.Note
import vlad.makarenko.notes.viewmodel.EditNoteViewModel

@Composable
fun EditNoteBody(
    navController: NavController,
    id: Int,
    viewModel: EditNoteViewModel = hiltViewModel()
) {

    remember { viewModel.getNote(id) }

    val note by viewModel.note.observeAsState(Note(title = "", description = ""))

    NoteBody(
        note = note,
        onChangeNote = viewModel::editNote,
        onClickSaveNote = { viewModel.saveNote(); navController.navigateUp() }
    )
}