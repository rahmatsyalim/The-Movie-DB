package com.syalim.themoviedb.domain.repository

import com.syalim.themoviedb.common.ConnectivityStatus
import kotlinx.coroutines.flow.Flow


/**
 * Created by Rahmat Syalim on 2022/08/08
 * rahmatsyalim@gmail.com
 */
interface ConnectivityRepository {

   fun observeConnectivityState(): Flow<ConnectivityStatus>

}