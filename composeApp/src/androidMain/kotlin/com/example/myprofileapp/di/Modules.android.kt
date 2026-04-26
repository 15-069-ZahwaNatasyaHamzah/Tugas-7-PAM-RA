package com.example.myprofileapp.di

import com.example.myprofileapp.db.DataStoreFactory
import com.example.myprofileapp.db.DatabaseDriverFactory
import com.example.myprofileapp.db.createDatabase
import com.example.myprofileapp.platform.AndroidDeviceInfo
import com.example.myprofileapp.platform.AndroidNetworkMonitor
import com.example.myprofileapp.platform.DeviceInfo
import com.example.myprofileapp.platform.NetworkMonitor
import org.koin.dsl.module

actual val platformModule = module {
    single { createDatabase(DatabaseDriverFactory(get())) }
    single { DataStoreFactory(get()).create() }
    single<DeviceInfo> { AndroidDeviceInfo() }
    single<NetworkMonitor> { AndroidNetworkMonitor(get()) }
}
