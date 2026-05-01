package org.example.project.ui.notes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.example.project.data.NoteRepository
import org.example.project.domain.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    repository: NoteRepository,
    noteId: Long?,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isFavorite by remember { mutableStateOf(false) }
    var createdAt by remember { mutableStateOf(Clock.System.now().toEpochMilliseconds()) }

    LaunchedEffect(noteId) {
        if (noteId != null) {
            repository.getNoteById(noteId)?.let { note ->
                title = note.title
                content = note.content
                isFavorite = note.isFavorite
                createdAt = note.createdAt
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == null) "Tambah Catatan" else "Edit Catatan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (noteId != null) {
                        IconButton(onClick = {
                            scope.launch {
                                repository.deleteNote(noteId)
                                onBack()
                            }
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                    IconButton(onClick = {
                        scope.launch {
                            repository.insertNote(
                                Note(
                                    id = noteId,
                                    title = title,
                                    content = content,
                                    isFavorite = isFavorite,
                                    createdAt = createdAt
                                )
                            )
                            onBack()
                        }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Judul") },
                placeholder = { Text("TugasTugas") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Konten") },
                placeholder = { Text("Isi catatan...") },
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }
    }
}
