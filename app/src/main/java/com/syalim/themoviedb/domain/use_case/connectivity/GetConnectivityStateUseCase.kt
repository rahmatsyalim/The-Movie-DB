package com.syalim.themoviedb.domain.use_case.connectivity

import com.syalim.themoviedb.common.ConnectivityStatus
import com.syalim.themoviedb.domain.repository.ConnectivityRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/08
 * rahmatsyalim@gmail.com
 */
@ViewModelScoped
class GetConnectivityStateUseCase @Inject constructor(
   private val repository: ConnectivityRepository
) {
   operator fun invoke(): Flow<ConnectivityStatus> {
      return repository.observeConnectivityState()
   }
}