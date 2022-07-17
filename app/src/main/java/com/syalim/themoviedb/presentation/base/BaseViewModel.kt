package com.syalim.themoviedb.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syalim.themoviedb.common.Resource
import com.syalim.themoviedb.presentation.State
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */
abstract class BaseViewModel : ViewModel() {

   protected suspend fun <T> handleRequest(
      state: MutableLiveData<State<T>>,
      dataFlow: Flow<Resource<T>>
   ) =
      dataFlow.collectLatest { result ->
         when (result) {
            is Resource.Loading -> state.value = State(isLoading = true)
            is Resource.Error -> state.value =
               State(errorMessage = result.message.toString())
            is Resource.Success -> state.value = State(data = result.data)
         }
      }

   protected suspend fun awaitAll(vararg blocks: suspend () -> Unit) = coroutineScope {
      blocks.forEach {
         launch { it() }
      }
   }

}