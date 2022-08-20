package com.syalim.themoviedb.presentation.common.utils

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.syalim.themoviedb.common.Resource
import com.syalim.themoviedb.presentation.common.ContentState


/**
 * Created by Rahmat Syalim on 2022/08/09
 * rahmatsyalim@gmail.com
 */

fun <T> Resource<T>.asContentState(refresh: Boolean = false): ContentState<T> {
   return when (this) {
      is Resource.Loading -> ContentState.Loading(refresh)
      is Resource.Error -> ContentState.Error(cause)
      is Resource.Success -> ContentState.Success(data, empty)
   }
}

val Resource<*>.isLoading get() = this is Resource.Loading

inline fun <T> ContentState<T>.onSuccess(
   block: T.(empty: Boolean) -> Unit
) {
   if (this is ContentState.Success) block(data, empty)
}

inline fun ContentState<*>.onContentEmpty(
   block: () -> Unit
) {
   if (this is ContentState.Success && empty) block()
}

inline fun ContentState<*>.onContentError(
   block: Throwable.() -> Unit
) {
   if (this is ContentState.Error) block.invoke(cause)
}


fun CombinedLoadStates.setListener(
   itemCount: Int,
   onLoading: ((Boolean) -> Unit)? = null,
   onRefresh: ((Boolean) -> Unit)? = null,
   onError: ((Throwable) -> Unit)? = null,
   onEmpty: (() -> Unit)? = null,
   onExist: (() -> Unit)? = null
) {
   onLoading?.invoke(refresh is LoadState.Loading && itemCount == 0)

   onRefresh?.invoke(refresh is LoadState.Loading && itemCount > 0)

   val errorState =
      source.append as? LoadState.Error
         ?: source.prepend as? LoadState.Error
         ?: source.refresh as? LoadState.Error

   errorState?.error?.let {
      onError?.invoke(it)
   }

   if (source.append is LoadState.NotLoading
      && source.append.endOfPaginationReached
      && itemCount == 0
   ) onEmpty?.invoke()

   if (source.refresh is LoadState.NotLoading
      && itemCount > 0
   ) onExist?.invoke()
}