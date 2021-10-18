package vlad.makarenko.notes.ui.note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import vlad.makarenko.notes.data.Note
import vlad.makarenko.notes.ui.components.NotesFloatingActionButton

@Composable
fun NoteBody(
    note: Note,
    onChangeNote: (note: Note) -> Unit,
    onClickSaveNote: () -> Unit
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
                    .fillMaxWidth()
            )
            TextField(
                value = note.description,
                onValueChange = { onChangeNote(note.copy(description = it)) },
                modifier = Modifier
                    .weight(5f)
                    .fillMaxWidth()
            )
        }
    }
}