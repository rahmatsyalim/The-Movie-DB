package com.syalim.themoviedb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syalim.themoviedb.framework.connectivity.ConnectivityState
import com.syalim.themoviedb.framework.connectivity.utils.ConnectivityStatus
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
class MainViewModel @Inject constructor(
   connectivityState: ConnectivityState
) : ViewModel() {

   val connectivityUiState: StateFlow<ConnectivityStatus> =
      connectivityState.observeConnectivityState().stateIn(
         viewModelScope,
         SharingStarted.Lazily,
         ConnectivityStatus.Available
      )

}


