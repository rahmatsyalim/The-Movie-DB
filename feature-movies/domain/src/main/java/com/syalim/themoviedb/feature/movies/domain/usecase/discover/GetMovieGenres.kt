package com.syalim.themoviedb.feature.movies.domain.usecase.discover

import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.feature.movies.domain.model.MovieGenre
import com.syalim.themoviedb.feature.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/02
 * rahmatsyalim@gmail.com
 */

class GetMovieGenres @Inject constructor(
   private val repository: MoviesRepository
) {
   operator fun invoke(): Flow<Result<List<MovieGenre>>> {
      return repository.getMovieGenres()
   }
}