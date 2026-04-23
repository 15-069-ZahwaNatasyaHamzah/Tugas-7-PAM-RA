# MyProfileApp - Kotlin Multiplatform

Aplikasi Profil interaktif yang dibangun menggunakan **Compose Multiplatform** (Kotlin Multiplatform). Proyek ini menunjukkan implementasi desain modern Android dengan arsitektur MVVM, pengelolaan state reaktif, dan fitur tema dinamis.

## 🚀 Fitur Utama

- **Model-View-ViewModel (MVVM)**: Pemisahan logika bisnis dan UI yang bersih menggunakan `ViewModel` dan `StateFlow`.
- **Edit Profile**: Fitur untuk mengubah Nama dan Bio secara langsung dengan validasi state lokal (*State Hoisting*).
- **Dark Mode Toggle**: Dukungan penuh untuk tema Terang (Light) dan Gelap (Dark) yang dapat diubah secara instan.
- **Responsive UI**: Tata letak yang dioptimalkan agar tetap terlihat baik di berbagai ukuran layar, termasuk optimasi ruang untuk elemen interaktif di bagian bawah.
- **Interactive Dialog**: Komponen informasi tambahan menggunakan `AlertDialog` Material 3.

## 🏗️ Implementasi Arsitektur

### 1. MVVM Pattern
- **Model**: `ProfileUiState.kt` menyimpan data mentah seperti nama, bio, dan status UI.
- **ViewModel**: `ProfileViewModel.kt` mengelola logika untuk memperbarui profil dan beralih tema menggunakan `MutableStateFlow`.
- **View**: `App.kt` berisi komponen UI Compose yang mengamati (*observe*) state dari ViewModel dan bereaksi terhadap perubahan data.

### 2. State Management
Menggunakan `collectAsStateWithLifecycle()` untuk memastikan pengamatan state yang efisien dan aman terhadap siklus hidup (*lifecycle-aware*) pada platform Android.

## 🛠️ Tech Stack
- **Language**: Kotlin
- **Framework**: Compose Multiplatform
- **UI Components**: Material 3
- **Architecture**: MVVM
- **State Flow**: Kotlin Coroutines & Flow
- **Image Loading**: Coil 3 (KMP Support)

## 🏃 Cara Menjalankan Project

### Prasyarat
- Android Studio (versi terbaru disarankan).
- JDK 17 atau yang lebih baru.
- Android SDK yang sudah terpasang.

### Jalankan di Android
1. Buka proyek di **Android Studio**.
2. Tunggu proses **Gradle Sync** selesai.
3. Pilih konfigurasi run `composeApp` di toolbar atas.
4. Pilih emulator atau perangkat fisik Android.
5. Klik tombol **Run** (Play Hijau).

### Jalankan melalui Terminal
Anda juga dapat menjalankan perintah berikut dari root direktori proyek:

**Untuk Android:**
```bash
./gradlew :composeApp:installDebug
adb shell am start -n com.example.myprofileapp/com.example.myprofileapp.MainActivity
```

**Untuk Desktop (JVM):**
```bash
./gradlew :composeApp:run
```

## 📂 Struktur Folder Utama
- `composeApp/src/commonMain/kotlin/com/example/myprofileapp/`: Berisi logika UI utama dan ViewModel (Shared Code).
- `composeApp/src/androidMain/`: Kode spesifik platform Android.
- `shared/`: Modul untuk logika bisnis yang dibagikan antar platform.

---
*Dikembangkan sebagai bagian dari Tugas Pengembangan Aplikasi Mobile.*
