package vlad.makarenko.notes.ui.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun NotesFloatingActionButton(onClick: () -> Unit, icon: ImageVector) {
    FloatingActionButton(onClick = onClick) {
        Icon(imageVector = icon, contentDescription = null)
    }
}