package com.syalim.themoviedb.feature.movies.ui.utils

import android.content.Context
import com.syalim.themoviedb.feature.movies.domain.utils.MovieCategory
import com.syalim.themoviedb.feature.movies.ui.R


/**
 * Created by Rahmat Syalim on 2022/09/07
 * rahmatsyalim@gmail.com
 */

internal fun MovieCategory.getMovieCategoryTitle(context: Context): String {
   return when (this) {
      MovieCategory.IN_THEATER -> context.getString(R.string.in_theater)
      MovieCategory.POPULAR -> context.getString(R.string.popular)
      MovieCategory.TOP_RATED -> context.getString(R.string.top_rated)
      MovieCategory.UPCOMING -> context.getString(R.string.upcoming)
   }
}