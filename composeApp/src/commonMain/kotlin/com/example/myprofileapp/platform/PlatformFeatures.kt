package com.example.myprofileapp.platform

import kotlinx.coroutines.flow.Flow

interface DeviceInfo {
    val model: String
    val osVersion: String
    val platform: String
}

interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}
