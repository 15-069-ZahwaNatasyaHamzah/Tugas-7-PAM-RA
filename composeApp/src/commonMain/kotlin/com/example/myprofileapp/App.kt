package com.example.myprofileapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

import myprofileapp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun App(viewModel: ProfileViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }

    val colors = if (uiState.isDarkMode) {
        darkColorScheme(
            primary = Color(0xFFBB86FC),
            onPrimary = Color.Black,
            surface = Color(0xFF121212),
            onSurface = Color.White,
            background = Color(0xFF121212),
            onBackground = Color.White
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6C4AB6),
            onPrimary = Color.White,
            surface = Color.White,
            onSurface = Color.Black,
            background = Color(0xFFF2F2F2),
            onBackground = Color.Black
        )
    }

    MaterialTheme(colorScheme = colors) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                // Dark Mode Switch Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (uiState.isDarkMode) "Dark Mode" else "Light Mode",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = uiState.isDarkMode,
                        onCheckedChange = { viewModel.toggleDarkMode(it) },
                        modifier = Modifier.scale(0.8f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // FOTO PROFILE
                Image(
                    painter = painterResource(Res.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (uiState.isEditing) {
                    EditProfileForm(
                        initialName = uiState.name,
                        initialBio = uiState.bio,
                        onSave = { name, bio ->
                            viewModel.updateProfile(name, bio)
                        },
                        onCancel = { viewModel.setEditing(false) }
                    )
                } else {
                    ProfileDisplay(
                        name = uiState.name,
                        bio = uiState.bio,
                        onEditClick = { viewModel.setEditing(true) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        InfoRow(Icons.Default.Email, uiState.email)
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                        )
                        InfoRow(Icons.Default.Phone, uiState.phone)
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                        )
                        InfoRow(Icons.Default.LocationOn, uiState.location)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { showDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Info",
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("OK")
                        }
                    },
                    title = { Text("Informasi") },
                    text = { Text("Halo! Ini adalah aplikasi MyProfileApp yang dibuat menggunakan Kotlin Multiplatform.") }
                )
            }
        }
    }
}

@Composable
fun ProfileDisplay(name: String, bio: String, onEditClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = name,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = bio,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onEditClick,
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Edit Profile")
        }
    }
}

@Composable
fun EditProfileForm(
    initialName: String,
    initialBio: String,
    onSave: (String, String) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var bio by remember { mutableStateOf(initialBio) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Bio") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            TextButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }
            Button(
                onClick = { onSave(name, bio) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Save", maxLines = 1)
            }
        }
    }
}

@Composable
fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

