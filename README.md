# MyProfile & Notes App - Kotlin Multiplatform

Aplikasi manajemen catatan (Notes App) ini merupakan pengembangan lanjutan berbasis Compose Multiplatform yang telah mengintegrasikan penyimpanan lokal menggunakan SQLDelight dengan pendekatan offline-first. Aplikasi mendukung fitur utama seperti CRUD (Create, Read, Update, Delete), pencarian catatan (search), serta halaman pengaturan menggunakan DataStore untuk preferensi tema dan pengurutan. Dengan arsitektur MVVM, aplikasi mampu mengelola data dan tampilan secara terstruktur, serta menghadirkan UI state yang lengkap (loading, empty, content) sehingga memberikan pengalaman pengguna yang lebih responsif dan tetap dapat digunakan tanpa koneksi internet.

## Fitur

-   CRUD Catatan (Create, Read, Update, Delete): Kelola catatan harian dengan mudah.
-   Fitur Pencarian: Temukan catatan dengan cepat berdasarkan judul atau isi.
-   Sistem Favorit: Tandai catatan penting agar mudah diakses kembali.
-   Mode Gelap (Dark Mode): Beralih antara tema terang dan gelap dengan nyaman.
-   Multiplatform: Logika bisnis dan UI dapat digunakan di Android, iOS, Desktop, dan Web.
-   UI Modern: Dibangun menggunakan Material 3 dengan tampilan yang bersih dan intuitif.

## Tech Stack

-   **UI Framework**: [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
-   **Database**: [SQLDelight](https://cashapp.github.io/sqldelight/)
-   **Concurrency**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
-   **Date & Time**: [kotlinx-datetime](https://github.com/Kotlin/kotlinx-datetime)
-   **Navigation**: [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation)

## Database Schema

Aplikasi ini menggunakan **SQLDelight** untuk pengelolaan basis data lokal. Berikut adalah skema untuk tabel `NoteEntity` :

```sql
CREATE TABLE NoteEntity (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    isFavorite INTEGER NOT NULL DEFAULT 0,
    createdAt INTEGER NOT NULL
);
```

### Query yang tersedia:
- `getAllNotes`: Mengambil semua catatan berdasarkan waktu pembuatan.
- `getFavoriteNotes`: Mengambil hanya catatan yang ditandai sebagai favorit.
- `searchNotes`: Mencari catatan berdasarkan judul atau isi sesuai kata kunci.
- `insertNote`: Menambahkan catatan baru atau memperbarui catatan yang sudah ada.
- `deleteNote`: Menghapus catatan berdasarkan ID.

## Cara Menjalankan Project

1. **Persiapan Resource**: Pastikan file `profile_user.png` berada di folder `composeApp/src/commonMain/composeResources/drawable/`.
2. **Sync Project**: Lakukan *Gradle Sync* di Android Studio.
3. **Run**:
   - Untuk Android: Pilih modul `composeApp` lalu klik **Run**.
   - Untuk Desktop: Jalankan perintah `./gradlew :composeApp:run` di terminal.

## Dokumentasi Visual

| Notes List | Edit Note | Profile |
| :---: | :---: | :---: |
|<img width="478" height="865" alt="image" src="https://github.com/user-attachments/assets/16c33c20-6542-4ca6-8e01-657244020859" /> | <img width="484" height="873" alt="image" src="https://github.com/user-attachments/assets/0f2fd260-7455-4185-817f-231b0e5b42ae" /> | <img width="480" height="865" alt="image" src="https://github.com/user-attachments/assets/fcca4d3e-7042-45cb-8e56-0c4dc63d416a" />
 |

##  Video Demo
Video demo fitur aplikasi dapat diakses melalui tautan berikut : https://youtube.com/shorts/elQ8uHpX1Aw?si=Vuo3DXu492FLlLeb 

---
