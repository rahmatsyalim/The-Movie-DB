package com.syalim.themoviedb.presentation.common

import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.utils.Constants.ERROR_NO_CONNECTION
import com.syalim.themoviedb.utils.getErrorMessage
import retrofit2.HttpException
import java.io.IOException


/**
 * Created by Rahmat Syalim on 2022/07/24
 * rahmatsyalim@gmail.com
 */
class HandlePagingLoadState<T : Any, VH : RecyclerView.ViewHolder>(val adapter: PagingDataAdapter<T, VH>) {
   operator fun invoke(
      onFirstLoading: ((Boolean) -> Unit)? = null,
      onLoading: ((Boolean) -> Unit)? = null,
      onError: ((String) -> Unit)? = null,
      onEmpty: (() -> Unit)? = null,
      onLoaded: (() -> Unit)? = null
   ) {
      adapter.addLoadStateListener { loadState ->
         with(loadState) {
            onFirstLoading?.invoke(refresh is LoadState.Loading && adapter.itemCount == 0)

            onLoading?.invoke(refresh is LoadState.Loading && adapter.itemCount > 0)

            val errorState =
               source.append as? LoadState.Error
                  ?: source.prepend as? LoadState.Error
                  ?: source.refresh as? LoadState.Error

            errorState?.error?.let { e ->
               when (e) {
                  is IOException -> e.message?.let { onError?.invoke(ERROR_NO_CONNECTION) }
                  is HttpException -> onError?.invoke(e.getErrorMessage())
                  else -> e.message?.let { onError?.invoke(it) }
               }
            }

            if (source.append is LoadState.NotLoading
               && source.append.endOfPaginationReached
               && adapter.itemCount == 0
            ) {
               onEmpty?.invoke()
            }
            if (source.refresh is LoadState.NotLoading
               && adapter.itemCount > 0
            ) {
               onLoaded?.invoke()
            }
         }
      }
   }
}