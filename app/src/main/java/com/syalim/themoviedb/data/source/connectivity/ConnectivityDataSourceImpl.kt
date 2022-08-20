package com.syalim.themoviedb.data.source.connectivity

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.syalim.themoviedb.common.ConnectivityStatus
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/12
 * rahmatsyalim@gmail.com
 */
class ConnectivityDataSourceImpl @Inject constructor(
   private val connectivityManager: ConnectivityManager
) : ConnectivityDataSource {

   override fun observeConnectivityState(): Flow<ConnectivityStatus> {
      return callbackFlow {
         send(connectivityState(connectivityManager))
         val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
               super.onAvailable(network)
               launch { send(ConnectivityStatus.Available) }
            }

            override fun onUnavailable() {
               super.onUnavailable()
               launch { send(ConnectivityStatus.Unavailable) }
            }

            override fun onLost(network: Network) {
               super.onLost(network)
               launch { send(ConnectivityStatus.Lost) }
            }

         }
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(callback)
         } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), callback)
         }
         awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
         }
      }
   }

   override fun isOnline(): Boolean {
      return when (connectivityState(connectivityManager)) {
         ConnectivityStatus.Lost -> false
         ConnectivityStatus.Unavailable -> false
         else -> true
      }
   }

   @Suppress("DEPRECATION")
   private fun connectivityState(connectivityManager: ConnectivityManager): ConnectivityStatus {
      return if (Build.VERSION.SDK_INT >= 23) {
         val activeNetwork = connectivityManager.activeNetwork
         val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
         if (activeNetwork != null && capabilities != null &&
            when {
               capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
               capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
               capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
               else -> false
            }
         ) {
            ConnectivityStatus.Available
         } else {
            ConnectivityStatus.Unavailable
         }
      } else {
         val activeNetworkInfo = connectivityManager.activeNetworkInfo
         if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting) ConnectivityStatus.Available
         else ConnectivityStatus.Unavailable
      }
   }
}