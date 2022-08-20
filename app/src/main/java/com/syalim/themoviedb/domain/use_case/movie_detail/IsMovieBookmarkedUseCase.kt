package com.syalim.themoviedb.domain.use_case.movie_detail

import com.syalim.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/18
 * rahmatsyalim@gmail.com
 */
class IsMovieBookmarkedUseCase @Inject constructor(
   private val repository: MoviesRepository
) {
   operator fun invoke(id: Int): Flow<Boolean> {
      return repository.isMovieBookmarked(id)
   }
}