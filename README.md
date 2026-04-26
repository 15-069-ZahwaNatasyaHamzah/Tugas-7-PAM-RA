# MyProfile & Notes App - Premium Edition (Week 7 Upgrade)

Aplikasi manajemen catatan (Notes App) modern yang dibangun menggunakan **Compose Multiplatform**. Proyek ini menonjolkan desain antarmuka premium, performa tinggi dengan penyimpanan lokal permanen, dan arsitektur kode yang bersih.

## 📝 Deskripsi Proyek
Aplikasi ini dirancang untuk memberikan pengalaman mencatat yang mulus dengan antarmuka yang estetis. Menggunakan **Kotlin Multiplatform (KMP)**, aplikasi ini berbagi logika bisnis dan UI di berbagai platform, sementara tetap mempertahankan performa native. Fokus utama upgrade minggu ini adalah pada **persistensi data**, **fitur pencarian cerdas**, dan **desain UI modern** yang terinspirasi oleh Material Design 3.

## 🚀 Fitur Utama
- **Modern Staggered Grid**: Tampilan daftar catatan menggunakan tata letak grid dinamis (masonry style) untuk estetika yang lebih hidup.
- **Full Offline CRUD**: Operasi Tambah, Baca, Edit, dan Hapus catatan yang tersimpan secara permanen di database lokal.
- **Smart Search**: Pencarian real-time yang memungkinkan pengguna menemukan catatan berdasarkan judul atau isi secara instan.
- **Favorite System**: Tandai catatan penting dengan ikon hati untuk akses cepat di tab Favorit.
- **Premium Profile Screen**: Desain profil yang bersih dengan header minimalis, foto terpusat, dan kartu informasi kontak yang elegan.
- **Persistent Settings**: Pengaturan tema (Dark/Light Mode) yang tersimpan secara permanen menggunakan Jetpack DataStore.
- **Responsive & Scrollable**: Seluruh elemen UI dioptimalkan untuk berbagai ukuran layar tanpa ada konten yang terpotong.

## 🗄️ Skema Database (SQLDelight)
Aplikasi menggunakan **SQLDelight** untuk manajemen database SQLite yang *type-safe*.

```sql
CREATE TABLE NoteEntity (
    id TEXT NOT NULL PRIMARY KEY,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    isFavorite INTEGER NOT NULL DEFAULT 0,
    timestamp INTEGER NOT NULL
);
```

## 🔍 Query Utama
Beberapa query SQL utama yang digunakan dalam aplikasi:
- **Ambil Semua Catatan**: `SELECT * FROM NoteEntity ORDER BY timestamp DESC;`
- **Pencarian**: `SELECT * FROM NoteEntity WHERE title LIKE ('%' || ? || '%') OR content LIKE ('%' || ? || '%') ORDER BY timestamp DESC;`
- **Tambah/Update**: `INSERT OR REPLACE INTO NoteEntity(id, title, content, isFavorite, timestamp) VALUES (?, ?, ?, ?, ?);`
- **Hapus**: `DELETE FROM NoteEntity WHERE id = ?;`
- **Toggle Favorit**: `UPDATE NoteEntity SET isFavorite = CASE WHEN isFavorite = 1 THEN 0 ELSE 1 END WHERE id = ?;`

## 🛠️ Teknologi yang Digunakan
| Komponen | Teknologi |
| --- | --- |
| **Bahasa** | Kotlin |
| **UI Framework** | Compose Multiplatform (Material 3) |
| **Database** | SQLDelight 2.0.2 |
| **Local Settings** | Jetpack DataStore Preferences 1.1.1 |
| **Navigation** | Jetpack Navigation Compose |
| **Architecture** | MVVM (Model-View-ViewModel) + Repository Pattern |
| **State Management** | StateFlow & Kotlin Flow |
| **Concurrency** | Kotlin Coroutines |

## 🏃 Cara Menjalankan
1.  Buka project di **Android Studio Koala** atau versi terbaru.
2.  Lakukan **Gradle Sync** untuk mengunduh semua dependensi.
3.  Pilih konfigurasi run **`composeApp`**.
4.  Pilih Emulator/Perangkat dan klik **Run**.

---
*Tugas 7 - Pengembangan Aplikasi Mobile (PAM) RA.*
