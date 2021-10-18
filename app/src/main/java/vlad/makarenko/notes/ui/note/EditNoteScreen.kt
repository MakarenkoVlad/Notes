package vlad.makarenko.notes.ui.note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import vlad.makarenko.notes.data.Note
import vlad.makarenko.notes.ui.components.NotesFloatingActionButton
import vlad.makarenko.notes.viewmodel.EditNoteViewModel

@Composable
fun EditNoteBody(
    navController: NavController,
    id: Int? = null,
    viewModel: EditNoteViewModel = hiltViewModel()
) {

    remember { id?.let { viewModel.getNote(it) } }

    val state by viewModel.state.collectAsState()

    EditNoteScreenStateful(
        note = state.note,
        onChangeNote = viewModel::changeNote,
        onCloseScreen = navController::navigateUp,
        onClickSaveNote = viewModel::saveNote,
        isSaved = state.isSaved,
    )
}

@Composable
fun EditNoteScreenStateful(
    note: Note,
    onChangeNote: (note: Note) -> Unit,
    onClickSaveNote: () -> Unit,
    onCloseScreen: () -> Unit,
    isSaved: Boolean,
) {
    Scaffold(
        floatingActionButton = {
            NotesFloatingActionButton(onClick = onClickSaveNote, icon = Icons.Filled.Done)
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TextField(
                value = note.title,
                onValueChange = { onChangeNote(note.copy(title = it)) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            )
            TextField(
                value = note.description,
                onValueChange = { onChangeNote(note.copy(description = it)) },
                modifier = Modifier
                    .weight(5f)
                    .fillMaxWidth(),
            )
        }
    }

    if (isSaved) {
        onCloseScreen()
    }
}