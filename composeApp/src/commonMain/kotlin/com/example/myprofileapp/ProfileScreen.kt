package com.example.myprofileapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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
import myprofileapp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel, 
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 1. TOP ROW: DARK MODE TOGGLE & SETTINGS
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (uiState.isDarkMode) "Dark Mode" else "Light Mode",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = uiState.isDarkMode,
                onCheckedChange = { viewModel.toggleDarkMode(it) },
                modifier = Modifier.scale(0.8f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onSettingsClick) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.primary)
            }
        }

        // 2. CENTERED PROFILE IMAGE (Seperti foto yang Anda kirim)
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    // Tidak menggunakan border tebal agar terlihat bersih seperti contoh Anda
            )
        }

        // 3. PROFILE INFO & ACTIONS
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = uiState.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            
            Text(
                text = uiState.bio,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

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
                OutlinedButton(
                    onClick = { viewModel.setEditing(true) },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Edit Profile")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 4. MODERN CONTACT CARD (Tetap menggunakan desain premium)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    ContactItem(Icons.Default.Email, uiState.email)
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                    )
                    ContactItem(Icons.Default.Phone, uiState.phone)
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                    )
                    ContactItem(Icons.Default.LocationOn, uiState.location)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = { showDialog = true }) {
                Text("Tentang Aplikasi", color = MaterialTheme.colorScheme.primary)
            }
            
            Spacer(modifier = Modifier.height(100.dp)) // Jarak aman untuk Bottom Nav
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) { Text("OK") }
            },
            title = { Text("Informasi") },
            text = { Text("Halo! Ini adalah aplikasi MyProfileApp yang dibuat menggunakan Kotlin Multiplatform.") }
        )
    }
}

@Composable
fun ContactItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            modifier = Modifier.size(40.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
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
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Bio") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2,
            shape = RoundedCornerShape(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) { Text("Cancel") }
            Button(
                onClick = { onSave(name, bio) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) { Text("Save") }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    profileViewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ListItem(
                headlineContent = { Text("Tema Gelap") },
                supportingContent = { Text("Ganti tampilan aplikasi") },
                trailingContent = {
                    Switch(
                        checked = uiState.isDarkMode,
                        onCheckedChange = { profileViewModel.toggleDarkMode(it) }
                    )
                }
            )
            HorizontalDivider()
            ListItem(
                headlineContent = { Text("Informasi Perangkat") },
                supportingContent = { Text("${uiState.deviceModel} - ${uiState.deviceOs}") },
                leadingContent = { Icon(Icons.Default.Info, contentDescription = null) }
            )
            HorizontalDivider()
        }
    }
}
