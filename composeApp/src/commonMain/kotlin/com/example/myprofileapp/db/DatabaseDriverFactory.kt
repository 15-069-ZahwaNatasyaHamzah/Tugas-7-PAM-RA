package com.example.myprofileapp.db

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(factory: DatabaseDriverFactory): AppDatabase {
    val driver = factory.createDriver()
    return AppDatabase(driver)
}
