package com.syalim.themoviedb.feature.movies.domain.model

import com.syalim.themoviedb.feature.movies.domain.utils.MovieCategory


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

data class MoviesCategorized(
   val category: MovieCategory,
   val movie: List<Movie>
)