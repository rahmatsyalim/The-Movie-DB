package com.syalim.themoviedb.data.source.connectivity

import com.syalim.themoviedb.common.ConnectivityStatus
import kotlinx.coroutines.flow.Flow


/**
 * Created by Rahmat Syalim on 2022/08/12
 * rahmatsyalim@gmail.com
 */
interface ConnectivityDataSource {

   fun observeConnectivityState(): Flow<ConnectivityStatus>

   fun isOnline(): Boolean

}