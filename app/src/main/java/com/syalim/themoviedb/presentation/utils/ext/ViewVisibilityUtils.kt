package com.syalim.themoviedb.presentation.utils.ext

import android.view.View
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

fun View.isVisible() {
   if (!isVisible) {
      visibility = View.VISIBLE
   }
}

fun View.isGone() {
   if (isVisible) {
      visibility = View.GONE
   }
}

fun View.isInvisible() {
   if (isVisible) {
      visibility = View.INVISIBLE
   }
}

fun ShimmerFrameLayout.start() {
   if (!isVisible) {
      isVisible()
      startShimmer()
   }
}

fun ShimmerFrameLayout.stop() {
   if (isVisible) {
      stopShimmer()
      isGone()
   }
}

fun ShimmerFrameLayout.delayedStop(coroutineScope: CoroutineScope, block: (() -> Unit)? = null) {
   if (isVisible) {
      coroutineScope.launch {
         delay(1000L)
         stopShimmer()
         isGone()
         block?.invoke()
      }
   } else {
      block?.invoke()
   }
}

fun SwipeRefreshLayout.start() {
   if (!isRefreshing) {
      isRefreshing = true
   }
}

fun SwipeRefreshLayout.stop() {
   if (isRefreshing) {
      isRefreshing = false
   }
}