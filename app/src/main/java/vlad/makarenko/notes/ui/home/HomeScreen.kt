package vlad.makarenko.notes.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import vlad.makarenko.notes.Screen
import vlad.makarenko.notes.data.Note
import vlad.makarenko.notes.ui.components.NotesFloatingActionButton
import vlad.makarenko.notes.uimodel.HomeScreenUiState
import vlad.makarenko.notes.viewmodel.HomeViewModel

@ExperimentalMaterialApi
@Composable
fun HomeBody(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    HomeBodyStateful(
        state,
        onClickAddNote = { navController.navigate(Screen.NewNote.route) },
        onNoteClick = { navController.navigate(Screen.EditNote(it.id).route) },
        onDeleteNote = viewModel::deleteNote
    )
}

@ExperimentalMaterialApi
@Composable
private fun HomeBodyStateful(
    state: HomeScreenUiState,
    onClickAddNote: () -> Unit,
    onNoteClick: (note: Note) -> Unit,
    onDeleteNote: (note: Note) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            NotesFloatingActionButton(onClick = onClickAddNote, icon = Icons.Filled.Add)
        }
    ) { innerPadding ->

        if (state.isLoading)
            CircularProgressIndicator()

        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(state.data) { note ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it != DismissValue.Default)
                            onDeleteNote(note)
                        true
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.DismissedToEnd -> Color.LightGray
                                else -> Color.White
                            }
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            val isShown =
                                dismissState.targetValue == DismissValue.DismissedToEnd
                            Crossfade(targetState = isShown) {
                                if (it)
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Trash can",
                                        modifier = Modifier.padding(start = 16.dp)
                                    )
                            }
                        }
                    },
                    directions = setOf(DismissDirection.StartToEnd)
                ) {
                    NoteCard(
                        note = note,
                        onClick = { onNoteClick(note) }
                    )
                }
            }
        }
    }
}

@Composable
private fun NoteCard(note: Note, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = note.title)
        Text(text = note.description)
    }
}