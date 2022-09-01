package com.syalim.themoviedb.domain.connectivity.use_case

import com.syalim.themoviedb.core.common.ConnectivityStatus
import com.syalim.themoviedb.domain.connectivity.repository.ConnectivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/31
 * rahmatsyalim@gmail.com
 */

class GetConnectivityStateUseCase @Inject constructor(
   private val repository: ConnectivityRepository
) {
   operator fun invoke(): Flow<ConnectivityStatus> {
      return repository.observeConnectivityState()
   }
}