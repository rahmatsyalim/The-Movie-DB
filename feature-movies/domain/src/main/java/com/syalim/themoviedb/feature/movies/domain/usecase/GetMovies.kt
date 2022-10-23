package com.syalim.themoviedb.feature.movies.domain.usecase

import com.syalim.themoviedb.feature.movies.domain.utils.MovieCategory
import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.feature.movies.domain.model.MoviesCategorized
import com.syalim.themoviedb.feature.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

class GetMovies @Inject constructor(
   private val repository: MoviesRepository
) {
   operator fun invoke(category: MovieCategory): Flow<Result<MoviesCategorized>> {
      return repository.getMovies(category)
   }
}