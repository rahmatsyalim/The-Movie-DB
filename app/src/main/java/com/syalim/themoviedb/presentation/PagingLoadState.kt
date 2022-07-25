package com.syalim.themoviedb.presentation

import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Rahmat Syalim on 2022/07/24
 * rahmatsyalim@gmail.com
 */
class PagingLoadState<T : Any, VH : RecyclerView.ViewHolder>(val adapter: PagingDataAdapter<T, VH>) {
   operator fun invoke(
      onFirstLoading: ((Boolean) -> Unit)? = null,
      onLoading: ((Boolean) -> Unit)? = null,
      onError: ((String) -> Unit)? = null,
      onEmpty: (() -> Unit)? = null,
      onSuccess: (()->Unit)? = null
   ) {
      adapter.addLoadStateListener { loadState ->
         loadState.apply {
            onFirstLoading?.invoke(refresh is LoadState.Loading && adapter.itemCount == 0)

            onLoading?.invoke(refresh is LoadState.Loading && adapter.itemCount > 0)

            val errorState =
               source.append as? LoadState.Error
                  ?: source.prepend as? LoadState.Error
                  ?: source.refresh as? LoadState.Error

            errorState?.error?.message?.let {
               onError?.invoke(it)
            }

            if (source.append is LoadState.NotLoading
               && source.append.endOfPaginationReached
               && adapter.itemCount == 0
            ) {
               onEmpty?.invoke()
            }
            if (source.refresh is LoadState.NotLoading
               && adapter.itemCount > 0){
               onSuccess?.invoke()
            }
         }
      }
   }
}