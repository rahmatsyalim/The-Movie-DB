package com.syalim.themoviedb.domain.movie.usecase.discover

import androidx.paging.PagingData
import com.syalim.themoviedb.domain.movie.model.Movie
import com.syalim.themoviedb.domain.movie.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/31
 * rahmatsyalim@gmail.com
 */

class GetDiscoverMoviesUseCase @Inject constructor(
   private val repository: MoviesRepository
) {
   operator fun invoke(genre: String): Flow<PagingData<Movie>> {
      return repository.getDiscoverMovies(genre = genre)
   }
}