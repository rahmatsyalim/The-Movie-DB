package com.syalim.themoviedb.presentation

import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Rahmat Syalim on 2022/07/24
 * rahmatsyalim@gmail.com
 */
class PagingLoadStateHandler<T : Any, VH : RecyclerView.ViewHolder>(val adapter: PagingDataAdapter<T, VH>) {
   operator fun invoke(
      onFirstLoading: ((Boolean) -> Unit)? = null,
      onLoading: ((Boolean) -> Unit)? = null,
      onError: ((String?) -> Unit)? = null,
      onEmpty: ((Boolean) -> Unit)? = null
   ) {
      adapter.addLoadStateListener { loadState ->
         loadState.apply {
            onFirstLoading?.invoke(refresh is LoadState.Loading && adapter.itemCount == 0)

            onLoading?.invoke(refresh is LoadState.Loading && adapter.itemCount > 0)

            val errorState =
               loadState.append as? LoadState.Error
                  ?: loadState.prepend as? LoadState.Error
                  ?: loadState.refresh as? LoadState.Error

            onError?.invoke(errorState?.error?.message)

            onEmpty?.invoke(
               source.append is LoadState.NotLoading
                  && source.append.endOfPaginationReached
                  && adapter.itemCount == 0
            )
         }
      }
   }
}