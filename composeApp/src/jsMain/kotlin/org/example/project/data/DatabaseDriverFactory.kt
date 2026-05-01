package org.example.project.data

import app.cash.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        throw Exception("SQLDelight JS driver not yet implemented")
    }
}
