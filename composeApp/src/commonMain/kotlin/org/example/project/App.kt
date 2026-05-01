package org.example.project

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import org.example.project.data.NoteRepository
import org.example.project.ui.notes.*
import org.example.project.ui.profile.*
import org.example.project.ui.theme.AppTheme

sealed class Screen(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Notes : Screen("notes", "Notes", Icons.Default.Description)
    object Favorites : Screen("favorites", "Saved", Icons.Default.Favorite)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
}

@Composable
fun App(repository: NoteRepository) {
    val profileViewModel: ProfileViewModel = viewModel { ProfileViewModel() }
    val notesViewModel: NotesViewModel = viewModel { NotesViewModel(repository) }
    val profileState by profileViewModel.uiState.collectAsState()
    
    val navController = rememberNavController()

    AppTheme(darkTheme = profileState.isDarkMode) {
        Scaffold(
            bottomBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                val bottomBarScreens = listOf(Screen.Notes, Screen.Favorites, Screen.Profile)
                val showBottomBar = bottomBarScreens.any { it.route == currentDestination?.route }

                if (showBottomBar) {
                    NavigationBar {
                        bottomBarScreens.forEach { screen ->
                            NavigationBarItem(
                                icon = { Icon(screen.icon, contentDescription = screen.label) },
                                label = { Text(screen.label) },
                                selected = currentDestination?.route == screen.route,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Notes.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(Screen.Notes.route) {
                    NotesScreen(
                        viewModel = notesViewModel,
                        onNoteClick = { id -> navController.navigate("note_detail/$id") },
                        onAddNote = { navController.navigate("add_edit_note") }
                    )
                }
                composable(Screen.Favorites.route) {
                    NotesScreen(
                        viewModel = notesViewModel,
                        onNoteClick = { id -> navController.navigate("note_detail/$id") },
                        onAddNote = {},
                        isFavoritesOnly = true
                    )
                }
                composable(Screen.Profile.route) {
                    ProfileScreen(
                        viewModel = profileViewModel,
                        onEditClick = { navController.navigate("edit_profile") }
                    )
                }
                composable("edit_profile") {
                    EditProfileScreen(
                        viewModel = profileViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(
                    "note_detail/{noteId}",
                    arguments = listOf(navArgument("noteId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getLong("noteId") ?: return@composable
                    NoteDetailScreen(
                        repository = repository,
                        noteId = noteId,
                        onEdit = { id -> navController.navigate("add_edit_note?noteId=$id") },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(
                    "add_edit_note?noteId={noteId}",
                    arguments = listOf(navArgument("noteId") { 
                        type = NavType.LongType
                        nullable = false
                        defaultValue = -1L
                    })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getLong("noteId")?.takeIf { it != -1L }
                    AddEditNoteScreen(
                        repository = repository,
                        noteId = noteId,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
