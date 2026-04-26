package com.example.myprofileapp.di

import com.example.myprofileapp.db.DataStoreFactory
import com.example.myprofileapp.db.DatabaseDriverFactory
import com.example.myprofileapp.db.createDatabase
import com.example.myprofileapp.platform.DeviceInfo
import com.example.myprofileapp.platform.NetworkMonitor
import kotlinx.coroutines.flow.flowOf
import org.koin.dsl.module

class JvmDeviceInfo : DeviceInfo {
    override val model: String = System.getProperty("os.name")
    override val osVersion: String = System.getProperty("os.version")
    override val platform: String = "Desktop (JVM)"
}

class JvmNetworkMonitor : NetworkMonitor {
    override val isOnline = flowOf(true) // Always online for desktop mock
}

actual val platformModule = module {
    single { createDatabase(DatabaseDriverFactory()) }
    single { DataStoreFactory().create() }
    single<DeviceInfo> { JvmDeviceInfo() }
    single<NetworkMonitor> { JvmNetworkMonitor() }
}
