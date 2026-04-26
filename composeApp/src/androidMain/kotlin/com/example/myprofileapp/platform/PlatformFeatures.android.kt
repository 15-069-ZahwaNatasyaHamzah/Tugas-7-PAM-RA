package com.example.myprofileapp.platform

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.startWith
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AndroidDeviceInfo : DeviceInfo {
    override val model: String = "${Build.MANUFACTURER} ${Build.MODEL}"
    override val osVersion: String = "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"
    override val platform: String = "Android"
}

class AndroidNetworkMonitor(private val context: Context) : NetworkMonitor {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val isOnline: Flow<Boolean> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true)
            }

            override fun onLost(network: Network) {
                trySend(false)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        
        connectivityManager.registerNetworkCallback(request, callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
    .startWith(getCurrentConnectivity())
    .distinctUntilChanged()

    private fun getCurrentConnectivity(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}

actual fun getDeviceInfo(): DeviceInfo = AndroidDeviceInfo()

// For Android, we need the context, so we'll actually use Koin to provide it,
// but for the sake of the 'expect/actual' function signature if we strictly follow it:
// We might need to change the expect/actual to be a class or handle context differently.
// Let's use Koin component to get context if needed, or better, pass context via Koin.
// Actually, Koin will handle the instantiation.

actual fun getNetworkMonitor(): NetworkMonitor {
    // This is tricky because actual fun getNetworkMonitor() has no params in commonMain.
    // We will use a workaround or dependency injection to provide it.
    // For now, let's assume we'll use Koin to provide the 'NetworkMonitor' instance.
    // But since it's an 'actual' function, let's use a static context holder or similar if necessary,
    // though it's better to inject it.
    throw IllegalStateException("Use Koin to inject NetworkMonitor on Android")
}
