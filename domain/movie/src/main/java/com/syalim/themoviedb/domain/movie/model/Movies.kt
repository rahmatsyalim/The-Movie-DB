package com.syalim.themoviedb.domain.movie.model


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

data class Movies(
   val id: Int,
   val category: String,
   val movie: List<Movie>
)