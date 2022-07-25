package com.syalim.themoviedb.presentation.base

import androidx.lifecycle.ViewModel
import com.syalim.themoviedb.common.Resource
import com.syalim.themoviedb.presentation.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */
abstract class BaseViewModel : ViewModel() {

   protected suspend fun <T> handleRequest(
      state: MutableStateFlow<State<T>>,
      dataFlow: Flow<Resource<T>>,
      isFirstLoad: Boolean = false
   ) =
      dataFlow.collectLatest { result ->
         Resource.Handle(result)(
            onLoading = {
               state.value = if (isFirstLoad) {
                  State(isFirstLoading = it)
               } else {
                  State(isLoading = it)
               }
            },
            onError = {
               state.value = State(errorMsg = it)
            },
            onSuccess = {
               state.value = it?.let {
                  State(data = it)
               } ?: State(isEmpty = true)
            }
         )
      }
}