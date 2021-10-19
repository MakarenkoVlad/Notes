package vlad.makarenko.notes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String
) {
    companion object {
        fun empty() = Note(
            title = "",
            description = "",
        )
    }

    override fun toString(): String {
        return "$title\n\n$description"
    }
}
