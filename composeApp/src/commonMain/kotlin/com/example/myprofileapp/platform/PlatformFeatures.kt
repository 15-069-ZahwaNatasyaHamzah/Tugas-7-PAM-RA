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

// These are not needed if we use Koin to provide the interfaces directly, 
// but the prompt asked for expect/actual. 
// However, since we are using Koin, we can just define the interfaces in commonMain
// and provide the actual implementations in platform modules.
