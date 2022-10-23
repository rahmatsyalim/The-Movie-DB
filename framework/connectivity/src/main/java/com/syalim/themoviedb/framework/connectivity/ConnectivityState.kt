package com.syalim.themoviedb.framework.connectivity

import com.syalim.themoviedb.framework.connectivity.utils.ConnectivityStatus
import kotlinx.coroutines.flow.Flow


/**
 * Created by Rahmat Syalim on 2022/08/12
 * rahmatsyalim@gmail.com
 */
interface ConnectivityState {

   fun observeConnectivityState(): Flow<ConnectivityStatus>

   fun isOnline(): Boolean

}