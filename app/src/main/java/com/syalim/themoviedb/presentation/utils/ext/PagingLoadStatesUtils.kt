package com.syalim.themoviedb.presentation.utils.ext

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState


/**
 * Created by Rahmat Syalim on 2022/08/09
 * rahmatsyalim@gmail.com
 */

//region CombinedLoadStates
inline fun CombinedLoadStates.onRefresh(
   itemCount: Int,
   started: () -> Unit,
   finishExist: () -> Unit,
   finishEmpty: () -> Unit,
   finishError: Throwable.() -> Unit
) {
   when (val refreshState = source.refresh) {
      is LoadState.Loading -> started.invoke()
      is LoadState.NotLoading -> if (itemCount > 0) finishExist.invoke() else finishEmpty.invoke()
      is LoadState.Error -> refreshState.error.let(finishError)
   }
}
//endregion