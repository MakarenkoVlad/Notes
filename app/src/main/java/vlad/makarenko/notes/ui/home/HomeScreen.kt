package vlad.makarenko.notes.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import vlad.makarenko.notes.Screen
import vlad.makarenko.notes.data.Note
import vlad.makarenko.notes.ui.components.NotesFloatingActionButton
import vlad.makarenko.notes.viewmodel.HomeViewModel

@Composable
fun HomeBody(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.observeAsState(emptyList())
    HomeBodyStateful(
        notes,
        onClickAddNote = { navController.navigate(Screen.NewNote.route) },
        onNoteClick = { navController.navigate(Screen.EditNote(it.id).route) }
    )
}

@Composable
private fun HomeBodyStateful(
    notes: List<Note>,
    onClickAddNote: () -> Unit,
    onNoteClick: (note: Note) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            NotesFloatingActionButton(onClick = onClickAddNote, icon = Icons.Filled.Add)
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(notes) { note ->
                NoteCard(note = note, onClick = { onNoteClick(note) })
            }
        }
    }
}

@Composable
private fun NoteCard(note: Note, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxWidth()
            .clickable { onClick() }) {
        Text(text = note.title)
        Text(text = note.description)
    }
}

private object MockValues {
}