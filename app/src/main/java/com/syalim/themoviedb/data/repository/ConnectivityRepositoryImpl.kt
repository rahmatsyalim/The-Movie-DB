package com.syalim.themoviedb.data.repository

import com.syalim.themoviedb.common.ConnectivityStatus
import com.syalim.themoviedb.data.source.connectivity.ConnectivityDataSource
import com.syalim.themoviedb.domain.repository.ConnectivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/08
 * rahmatsyalim@gmail.com
 */

class ConnectivityRepositoryImpl @Inject constructor(
   private val connectivityDataSource: ConnectivityDataSource
) : ConnectivityRepository {

   override fun observeConnectivityState(): Flow<ConnectivityStatus> {
      return connectivityDataSource.observeConnectivityState()
   }
}