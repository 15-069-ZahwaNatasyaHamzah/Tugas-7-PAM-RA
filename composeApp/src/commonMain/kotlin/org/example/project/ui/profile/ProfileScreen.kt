package org.example.project.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.ui.components.InfoItem
import org.example.project.ui.components.ProfileHeader

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onEditClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Mode", color = MaterialTheme.colorScheme.onBackground)
            Switch(
                checked = uiState.isDarkMode,
                onCheckedChange = { viewModel.toggleDarkMode(it) },
                modifier = Modifier.padding(start = 8.dp),
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        ProfileHeader(
            name = uiState.name,
            bio = uiState.bio
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onEditClick,
            modifier = Modifier.fillMaxWidth(0.7f),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF37363B)
            )
        ) {
            Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Edit Profile", color = Color.White)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            InfoItem(icon = Icons.Default.Email, text = uiState.email)
            HorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
            InfoItem(icon = Icons.Default.Phone, text = uiState.phone)
            HorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
            InfoItem(icon = Icons.Default.LocationOn, text = uiState.location)
        }
    }
}
