package com.syalim.themoviedb.feature.movies.domain.usecase.detail

import com.syalim.themoviedb.feature.movies.domain.repository.MoviesRepository
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/03
 * rahmatsyalim@gmail.com
 */

class IsMovieBookmarked @Inject constructor(
   private val repository: MoviesRepository
) {
   suspend operator fun invoke(id: String): Boolean {
      return repository.isMovieBookmarked(id.toInt())
   }
}