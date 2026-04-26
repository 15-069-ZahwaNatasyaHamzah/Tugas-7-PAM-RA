package com.example.myprofileapp

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

sealed class Screen(val route: String, val label: String) {
    object Notes : Screen("notes", "Notes")
    object Favorites : Screen("favorites", "Favorites")
    object Profile : Screen("profile", "Profile")
    object Settings : Screen("settings", "Settings")
}

@Composable
fun App() {
    KoinContext {
        val profileViewModel: ProfileViewModel = koinInject()
        val notesViewModel: NotesViewModel = koinInject()
        
        val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()
        val navController = rememberNavController()

        MaterialTheme(colorScheme = if (uiState.isDarkMode) darkColorScheme() else lightColorScheme()) {
            Scaffold(
                bottomBar = {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    
                    val showBottomBar = listOf(Screen.Notes.route, Screen.Favorites.route, Screen.Profile.route)
                        .any { it == currentDestination?.route }

                    if (showBottomBar) {
                        Surface(
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                            tonalElevation = 8.dp
                        ) {
                            NavigationBar(
                                containerColor = Color.Transparent,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                val items = listOf(Screen.Notes, Screen.Favorites, Screen.Profile)
                                items.forEach { screen ->
                                    NavigationBarItem(
                                        icon = {
                                            when(screen) {
                                                Screen.Notes -> Icon(Icons.AutoMirrored.Filled.Note, contentDescription = null)
                                                Screen.Favorites -> Icon(Icons.Default.Favorite, contentDescription = null)
                                                Screen.Profile -> Icon(Icons.Default.Person, contentDescription = null)
                                                else -> {}
                                            }
                                        },
                                        label = { Text(screen.label) },
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
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
                },
                floatingActionButton = {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    if (navBackStackEntry?.destination?.route == Screen.Notes.route) {
                        FloatingActionButton(
                            onClick = { navController.navigate("add_edit_note") },
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add Note")
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Notes.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Notes.route) {
                        NotesScreen(
                            viewModel = notesViewModel,
                            onNoteClick = { id -> navController.navigate("note_detail/$id") }
                        )
                    }
                    composable(Screen.Favorites.route) {
                        FavoritesScreen(
                            viewModel = notesViewModel,
                            onNoteClick = { id -> navController.navigate("note_detail/$id") }
                        )
                    }
                    composable(Screen.Profile.route) {
                        ProfileScreen(
                            viewModel = profileViewModel,
                            onSettingsClick = { navController.navigate(Screen.Settings.route) }
                        )
                    }
                    composable(Screen.Settings.route) {
                        SettingsScreen(
                            profileViewModel = profileViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("note_detail/{noteId}") { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("noteId") ?: ""
                        NoteDetailScreen(
                            noteId = noteId,
                            viewModel = notesViewModel,
                            onEditClick = { id -> navController.navigate("add_edit_note?noteId=$id") },
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("add_edit_note?noteId={noteId}") { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("noteId")
                        AddEditNoteScreen(
                            noteId = noteId,
                            viewModel = notesViewModel,
                            onSave = { navController.popBackStack() },
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
