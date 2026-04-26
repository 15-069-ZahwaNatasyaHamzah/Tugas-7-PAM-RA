package com.example.myprofileapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.myprofileapp.db.AppDatabase
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String, val icon: ImageVector? = null, val label: String? = null) {
    data object Notes : Screen("notes", Icons.Default.Description, "Notes")
    data object Favorites : Screen("favorites", Icons.Default.Favorite, "Favorites")
    data object Profile : Screen("profile", Icons.Default.Person, "Profile")
    data object Settings : Screen("settings", Icons.Default.Settings, "Settings")
}

import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
@OptIn(KoinExperimentalAPI::class)
fun App() {
    KoinContext {
        val profileViewModel: ProfileViewModel = koinViewModel()
        val notesViewModel: NotesViewModel = koinViewModel()

        val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()
        val navController = rememberNavController()

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
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val bottomNavItems = listOf(Screen.Notes, Screen.Favorites, Screen.Profile)
        val showBottomBar = currentDestination?.route in bottomNavItems.map { it.route }

        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    NavigationBar {
                        bottomNavItems.forEach { screen ->
                            NavigationBarItem(
                                icon = { Icon(screen.icon!!, contentDescription = screen.label) },
                                label = { Text(screen.label!!) },
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
            },
            floatingActionButton = {
                if (currentDestination?.route == Screen.Notes.route) {
                    FloatingActionButton(onClick = { navController.navigate("add_edit_note") }) {
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
}
