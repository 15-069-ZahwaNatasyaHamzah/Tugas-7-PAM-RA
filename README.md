# MyProfile & Notes App - Kotlin Multiplatform

Aplikasi manajemen catatan (Notes App) interaktif yang dibangun menggunakan **Compose Multiplatform**. Proyek ini merupakan pengembangan lebih lanjut yang mengintegrasikan sistem navigasi kompleks, manajemen data dinamis, dan profil pengguna dalam satu arsitektur MVVM yang solid.

## 🚀 Fitur Baru (Tugas 4 - Navigasi & Notes)

- **Bottom Navigation**: Navigasi utama dengan 3 tab:
  - 📝 **Notes**: Daftar utama semua catatan pengguna.
  - ❤️ **Favorites**: Koleksi catatan yang ditandai sebagai favorit.
  - 👤 **Profile**: Informasi profil pengguna dengan fitur Dark Mode & Edit.
- **Full CRUD Notes**: 
  - Menambah catatan baru melalui **Floating Action Button (FAB)**.
  - Melihat detail catatan dengan passing `noteId`.
  - Mengedit catatan yang sudah ada.
  - Menghapus dan menandai favorit.
- **Advanced Navigation**: 
  - Implementasi `androidx.navigation.compose`.
  - Perpindahan layar dengan argument passing (Passing `noteId`).
  - Navigasi balik (Back stack) yang proper di semua layar.
- **Multi-ViewModel Arch**: 
  - `NotesViewModel`: Mengelola state daftar catatan dan logika CRUD.
  - `ProfileViewModel`: Mengelola status profil dan tema aplikasi.

## 🏗️ Struktur Arsitektur

### 1. Navigasi
Menggunakan **Jetpack Navigation Compose** untuk mengatur aliran aplikasi:
- `NavHost` sebagai kontainer utama.
- Rute dinamis untuk detail dan edit: `note_detail/{noteId}`.
- Integrasi `Scaffold` untuk mengelola Bottom Bar dan FAB secara global.

### 2. State Management
- **StateFlow & UI State**: Setiap perubahan pada catatan atau profil dipancarkan melalui `StateFlow` dan diobservasi oleh UI secara reaktif.
- **Lifecycle Awareness**: Menggunakan `collectAsStateWithLifecycle()` untuk efisiensi memori pada platform Android.

## 🛠️ Tech Stack
- **Framework**: Compose Multiplatform
- **Navigation**: Navigation Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Concurrency**: Kotlin Coroutines & Flow
- **UI Components**: Material Design 3 (M3)
- **Serialization**: KotlinX Serialization (untuk rute navigasi)

## 🏃 Cara Menjalankan Project

### Prasyarat
- Android Studio (Koala atau lebih baru disarankan).
- JDK 17+.
- Emulator Android atau Perangkat Fisik.

### Langkah-langkah
1.  **Clone/Buka Project**: Buka folder project di Android Studio.
2.  **Gradle Sync**: Tunggu hingga proses sinkronisasi library selesai.
3.  **Run**:
    - Klik menu dropdown di toolbar atas, pilih **`composeApp`**.
    - Klik tombol **Run** (Play Hijau).

### Perintah Terminal (Opsional)
```bash
# Instal ke Android
./gradlew :composeApp:installDebug

# Jalankan di Desktop
./gradlew :composeApp:run
```

---
*Dikembangkan sebagai bagian dari Tugas Pengembangan Aplikasi Mobile (PAM).*
