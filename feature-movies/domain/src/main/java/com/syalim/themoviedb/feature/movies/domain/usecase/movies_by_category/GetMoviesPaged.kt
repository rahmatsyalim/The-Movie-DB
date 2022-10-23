package com.syalim.themoviedb.feature.movies.domain.usecase.movies_by_category

import androidx.paging.PagingData
import com.syalim.themoviedb.feature.movies.domain.utils.MovieCategory
import com.syalim.themoviedb.feature.movies.domain.model.Movie
import com.syalim.themoviedb.feature.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/05
 * rahmatsyalim@gmail.com
 */

class GetMoviesPaged @Inject constructor(
   private val repository: MoviesRepository
) {
   operator fun invoke(category: MovieCategory): Flow<PagingData<Movie>> {
      return repository.getMoviesPaged(category)
   }
}