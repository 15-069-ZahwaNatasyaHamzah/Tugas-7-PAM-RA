package org.example.project.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.ui.theme.AppTheme

@Preview(showBackground = true)
@Composable
fun ProfileScreenAndroidPreview() {
    val viewModel = ProfileViewModel()
    AppTheme(darkTheme = true) {
        ProfileScreen(
            viewModel = viewModel,
            onEditClick = {}
        )
    }
}
