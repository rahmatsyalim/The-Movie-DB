package com.syalim.themoviedb.feature.movies.domain.usecase.discover

import androidx.paging.PagingData
import com.syalim.themoviedb.feature.movies.domain.model.Movie
import com.syalim.themoviedb.feature.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/02
 * rahmatsyalim@gmail.com
 */

class GetDiscoverMovies @Inject constructor(
   private val repository: MoviesRepository
) {
   operator fun invoke(genre: String): Flow<PagingData<Movie>> {
      return repository.getDiscoverMovies(genre)
   }
}