package com.example.myprofileapp.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.io.File

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:notes.db")
        if (!File("notes.db").exists()) {
            AppDatabase.Schema.create(driver)
        }
        return driver
    }
}
