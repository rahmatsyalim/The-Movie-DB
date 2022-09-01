package com.syalim.themoviedb.domain.movie.usecase.movie_detail

import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.domain.movie.model.MovieDetail
import com.syalim.themoviedb.domain.movie.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/31
 * rahmatsyalim@gmail.com
 */

class GetMovieDetailUseCase @Inject constructor(
   private val repository: MoviesRepository
) {
   operator fun invoke(id: String): Flow<Result<MovieDetail>> {
      return repository.getMovieDetail(id = id)
   }
}