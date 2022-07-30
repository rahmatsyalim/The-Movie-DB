package com.syalim.themoviedb.presentation.common

import androidx.lifecycle.ViewModel
import com.syalim.themoviedb.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */
abstract class BaseViewModel : ViewModel() {

   protected suspend fun <T> handleResult(
      state: MutableStateFlow<State<T>>,
      dataFlow: Flow<Resource<T>>,
      isFirstLoading: Boolean = false
   ) =
      dataFlow
         .distinctUntilChanged()
         .collectLatest { result ->
            result.handle(
               onLoading = {
                  state.value = State.Loading(isFirstLoading)
               },
               onError = {
                  state.value = State.Error(it)
               },
               onSuccess = {
                  state.value = State.Loaded(it)
               }
            )
         }

}