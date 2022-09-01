package com.syalim.themoviedb.domain.movie.usecase.home

import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.domain.movie.model.Movies
import com.syalim.themoviedb.domain.movie.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/31
 * rahmatsyalim@gmail.com
 */

class GetHomeInTheaterMoviesUseCase @Inject constructor(
   private val repository: MoviesRepository
) {
   operator fun invoke(): Flow<Result<Movies>> {
      return repository.getHomeInTheaterMovies()
   }
}