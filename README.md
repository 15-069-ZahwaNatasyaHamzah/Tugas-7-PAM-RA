# MyProfile & Notes App - Kotlin Multiplatform (Week 7 Upgrade)

Aplikasi manajemen catatan (Notes App) interaktif yang dibangun menggunakan **Compose Multiplatform**. Versi ini adalah upgrade signifikan dengan fitur database lokal, pengaturan persisten, dan fungsionalitas pencarian.

## 🚀 Fitur Baru (Tugas 7 - Database & DataStore)

- **SQLDelight Database**: 
  - Penyimpanan lokal yang tangguh dan offline-first.
  - CRUD lengkap: Create, Read, Update, Delete.
  - Sinkronisasi reaktif menggunakan Kotlin Flow.
- **Search Functionality**:
  - Cari catatan berdasarkan judul atau konten secara real-time.
  - Mendukung pencarian case-insensitive.
- **Settings with DataStore**:
  - Layar pengaturan baru untuk mengelola preferensi aplikasi.
  - Tema (Dark/Light) tersimpan secara permanen menggunakan Jetpack DataStore Preferences.
- **Offline-First Architecture**: 
  - Data tersimpan secara lokal di perangkat, memungkinkan penggunaan tanpa koneksi internet.
- **Proper UI States**:
  - **Loading State**: Menampilkan indikator saat mengambil data.
  - **Empty State**: Pesan informatif saat tidak ada catatan atau hasil pencarian nihil.
  - **Content State**: Tampilan daftar catatan yang rapi.

## 🏗️ Database Schema (SQLDelight)

```sql
CREATE TABLE NoteEntity (
    id TEXT NOT NULL PRIMARY KEY,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    isFavorite INTEGER AS Boolean DEFAULT 0 NOT NULL,
    timestamp INTEGER NOT NULL
);
```

## 🏗️ Struktur Arsitektur

### 1. Data Layer
- `NoteRepository`: Menangani komunikasi antara ViewModel dan SQLDelight.
- `SettingsRepository`: Menangani preferensi pengguna menggunakan DataStore.
- `DatabaseDriverFactory`: Implementasi platform-specific untuk driver SQLite.

### 2. UI Layer
- `NotesViewModel`: Logika pencarian, filter favorit, dan operasi CRUD.
- `ProfileViewModel`: Logika profil dan integrasi pengaturan tema DataStore.
- `NotesScreens`: Layar daftar, detail, dan tambah/edit catatan.
- `SettingsScreen`: Layar untuk mengelola preferensi aplikasi.

## 🛠️ Tech Stack
- **Database**: SQLDelight 2.0.2
- **Persistence**: Jetpack DataStore 1.1.1
- **Framework**: Compose Multiplatform
- **Navigation**: Navigation Compose
- **Architecture**: MVVM
- **State Management**: StateFlow & Flow

## 🏃 Cara Menjalankan Project

### Prasyarat
- Android Studio Koala+.
- JDK 17+.

### Langkah-langkah
1.  Buka project di Android Studio.
2.  Lakukan **Gradle Sync** untuk mengunduh dependensi SQLDelight dan DataStore.
3.  Jalankan aplikasi pada emulator atau perangkat fisik.

---
*Tugas 7 Pengembangan Aplikasi Mobile (PAM).*
