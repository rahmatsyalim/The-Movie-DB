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
      onFirstLoading: (Boolean) -> Unit,
      onLoading: (Boolean) -> Unit,
      onError: (String?) -> Unit,
      onEmpty: (Boolean) -> Unit
   ) {
      adapter.addLoadStateListener { loadState ->
         loadState.apply {
            onFirstLoading(refresh is LoadState.Loading && adapter.itemCount == 0)

            onLoading(refresh is LoadState.Loading && adapter.itemCount > 0)

            val errorState =
               loadState.append as? LoadState.Error
                  ?: loadState.prepend as? LoadState.Error
                  ?: loadState.refresh as? LoadState.Error

            onError(errorState?.error?.message)

            onEmpty(
               source.append is LoadState.NotLoading
                  && source.append.endOfPaginationReached
                  && adapter.itemCount == 0
            )
         }
      }
   }
}