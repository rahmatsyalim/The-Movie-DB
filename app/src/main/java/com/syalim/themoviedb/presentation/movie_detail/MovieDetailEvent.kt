package com.syalim.themoviedb.presentation.movie_detail

import com.syalim.themoviedb.domain.model.Movie


/**
 * Created by Rahmat Syalim on 2022/08/18
 * rahmatsyalim@gmail.com
 */

sealed interface MovieDetailEvent {
   data class Bookmark(val data: Movie) : MovieDetailEvent
   data class LoadContents(val id: String, val refresh: Boolean = false) : MovieDetailEvent
}