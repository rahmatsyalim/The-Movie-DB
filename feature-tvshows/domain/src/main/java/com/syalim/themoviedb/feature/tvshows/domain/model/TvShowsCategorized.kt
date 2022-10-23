package com.syalim.themoviedb.feature.tvshows.domain.model

import com.syalim.themoviedb.feature.tvshows.domain.utils.TvShowCategory


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

data class TvShowsCategorized(
   val category: TvShowCategory,
   val tvShow: List<TvShow>
)