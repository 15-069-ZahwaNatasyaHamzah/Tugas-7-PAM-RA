package com.example.myprofileapp.di

import com.example.myprofileapp.db.DataStoreFactory
import com.example.myprofileapp.db.DatabaseDriverFactory
import com.example.myprofileapp.db.createDatabase
import com.example.myprofileapp.platform.DeviceInfo
import com.example.myprofileapp.platform.NetworkMonitor
import kotlinx.coroutines.flow.flowOf
import org.koin.dsl.module
import platform.UIKit.UIDevice

class IosDeviceInfo : DeviceInfo {
    override val model: String = UIDevice.currentDevice.model
    override val osVersion: String = UIDevice.currentDevice.systemVersion
    override val platform: String = UIDevice.currentDevice.systemName
}

class IosNetworkMonitor : NetworkMonitor {
    override val isOnline = flowOf(true) // Mock for now
}

actual val platformModule = module {
    single { createDatabase(DatabaseDriverFactory()) }
    single { DataStoreFactory().create() }
    single<DeviceInfo> { IosDeviceInfo() }
    single<NetworkMonitor> { IosNetworkMonitor() }
}
