package com.syalim.themoviedb.domain.movie.usecase.movie_detail

import com.syalim.themoviedb.domain.movie.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/31
 * rahmatsyalim@gmail.com
 */

class IsMovieBookmarkedUseCase @Inject constructor(
   private val repository: MoviesRepository
) {
   operator fun invoke(id: Int): Flow<Boolean> {
      return repository.isMovieBookmarked(id)
   }
}