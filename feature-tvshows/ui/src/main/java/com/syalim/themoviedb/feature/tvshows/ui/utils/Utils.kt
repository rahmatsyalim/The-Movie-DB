package com.syalim.themoviedb.feature.tvshows.ui.utils

import android.content.Context
import com.syalim.themoviedb.feature.tvshows.domain.utils.TvShowCategory
import com.syalim.themoviedb.feature.tvshows.ui.R


/**
 * Created by Rahmat Syalim on 2022/09/07
 * rahmatsyalim@gmail.com
 */

fun TvShowCategory.getTvShowCategoryTitle(context: Context): String {
   return when (this) {
      TvShowCategory.AIRING_TODAY -> context.getString(R.string.airing_today)
      TvShowCategory.POPULAR -> context.getString(R.string.popular)
      TvShowCategory.TOP_RATED -> context.getString(R.string.top_rated)
      TvShowCategory.ON_THE_AIR -> context.getString(R.string.on_the_air)
   }
}