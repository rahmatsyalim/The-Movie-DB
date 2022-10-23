package com.syalim.themoviedb.core.ui.utils

import android.view.View
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Rahmat Syalim on 2022/09/13
 * rahmatsyalim@gmail.com
 */

inline fun <V : View> V.setCustomCLickListener(crossinline block: V.() -> Unit) {
   setOnClickListener { block.invoke(this) }
}

inline fun RecyclerView.setVerticalScrollListener(crossinline block: (offset: Int, isScrollingDown: Boolean) -> Unit) {
   addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
         super.onScrolled(recyclerView, dx, dy)
         block.invoke(recyclerView.computeVerticalScrollOffset(), dy > 0)
      }
   })
}

inline fun PagingDataAdapter<*, *>.setCustomLoadStateListener(
   crossinline onRefresh: () -> Unit,
   crossinline onRefreshSuccess: () -> Unit,
   crossinline onRefreshEmpty: () -> Unit,
   crossinline onRefreshError: Throwable.() -> Unit
) {
   addLoadStateListener {
      when (val refreshState = it.source.refresh) {
         is LoadState.Loading -> onRefresh.invoke()
         is LoadState.NotLoading -> if (itemCount > 0) onRefreshSuccess.invoke() else onRefreshEmpty.invoke()
         is LoadState.Error -> refreshState.error.let(onRefreshError)
      }
   }
}