package vlad.makarenko.notes.ui.home

import android.content.Context
import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import vlad.makarenko.notes.R
import vlad.makarenko.notes.Screen
import vlad.makarenko.notes.data.Note
import vlad.makarenko.notes.ui.components.NotesFloatingActionButton
import vlad.makarenko.notes.uimodel.HomeScreenUiState
import vlad.makarenko.notes.viewmodel.HomeViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun HomeBody(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    HomeBodyStateful(
        state,
        onClickAddNote = { navController.navigate(Screen.NewNote.route) },
        onNoteClick = { navController.navigate(Screen.EditNote(it.id).route) },
        onDeleteNote = viewModel::deleteNote,
        onShareNote = { shareNote(context, it) }
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
private fun HomeBodyStateful(
    state: HomeScreenUiState,
    onClickAddNote: () -> Unit,
    onNoteClick: (note: Note) -> Unit,
    onDeleteNote: (note: Note) -> Unit,
    onShareNote: (note: Note) -> Unit
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
                Box(modifier = Modifier.fillMaxSize()) {
                    var isPopupExpanded by remember { mutableStateOf(false) }
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
                            val isShown =
                                dismissState.targetValue == DismissValue.DismissedToEnd
                            NoteCardBackground(color, isShown)
                        },
                        directions = setOf(DismissDirection.StartToEnd)
                    ) {
                        NoteCard(
                            note = note,
                            onClick = { onNoteClick(note) },
                            onLongClick = { isPopupExpanded = true }
                        )
                    }

                    NoteCardPopup(
                        isExpanded = isPopupExpanded,
                        onDismiss = { isPopupExpanded = false },
                        onDeleteNote = { onDeleteNote(note) },
                        onShareNote = { onShareNote(note) }
                    )
                }
            }
        }
    }
}

@Composable
private fun NoteCardBackground(color: Color, isShown: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color),
        contentAlignment = Alignment.CenterStart
    ) {
        Crossfade(targetState = isShown) {
            if (it)
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Trash can",
                    modifier = Modifier.padding(start = 16.dp)
                )
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun NoteCard(note: Note, onClick: () -> Unit, onLongClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
            .padding(16.dp)
    ) {
        Text(text = note.title)
        Text(text = note.description)
    }
}

@Composable
private fun NoteCardPopup(isExpanded: Boolean, onDismiss: () -> Unit, onDeleteNote: () -> Unit, onShareNote: () -> Unit) {
    DropdownMenu(expanded = isExpanded, onDismissRequest = onDismiss) {
        listOf(
            stringResource(R.string.delete) to onDeleteNote,
            stringResource(R.string.share) to onShareNote,
        ).forEach {
            NoteCardPopupItem(it.first, it.second)
        }
    }
}

@Composable
private fun NoteCardPopupItem(text: String, onClick: () -> Unit) {
    DropdownMenuItem(onClick = onClick) {
        Text(text = text)
    }
}

private fun shareNote(context: Context, note: Note) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, note.toString())
        type = "text/plain"
    }

    context.startActivity(shareIntent)
}