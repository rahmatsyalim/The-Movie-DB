package com.syalim.themoviedb.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syalim.themoviedb.domain.connectivity.use_case.GetConnectivityStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */


@HiltViewModel
class MovieViewModel @Inject constructor(
   getNetworkStateUseCase: GetConnectivityStateUseCase
) : ViewModel() {

   val connectivityState: StateFlow<com.syalim.themoviedb.core.common.ConnectivityStatus> =
      getNetworkStateUseCase.invoke().stateIn(
         viewModelScope,
         SharingStarted.Lazily,
         com.syalim.themoviedb.core.common.ConnectivityStatus.Available
      )

}


