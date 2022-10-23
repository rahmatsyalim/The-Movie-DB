package com.syalim.themoviedb.feature.movies.domain.usecase.detail

import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.feature.movies.domain.model.MovieDetails
import com.syalim.themoviedb.feature.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/03
 * rahmatsyalim@gmail.com
 */

class GetMovieDetails @Inject constructor(
   private val repository: MoviesRepository
) {
   operator fun invoke(id: String): Flow<Result<MovieDetails>> {
      return repository.getMovieDetails(id)
   }
}