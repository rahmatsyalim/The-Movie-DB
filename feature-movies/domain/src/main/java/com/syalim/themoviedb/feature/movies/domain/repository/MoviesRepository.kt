package com.syalim.themoviedb.feature.movies.domain.repository

import androidx.paging.PagingData
import com.syalim.themoviedb.feature.movies.domain.utils.MovieCategory
import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.feature.movies.domain.model.*
import kotlinx.coroutines.flow.Flow


/**
 * Created by Rahmat Syalim on 2022/09/02
 * rahmatsyalim@gmail.com
 */

interface MoviesRepository {

   fun getMovies(category: MovieCategory): Flow<Result<MoviesCategorized>>

   fun getMoviesPaged(category: MovieCategory): Flow<PagingData<Movie>>

   fun getDiscoverMovies(genre: String): Flow<PagingData<Movie>>

   fun getMovieGenres(): Flow<Result<List<MovieGenre>>>

   fun getMovieDetails(id: String): Flow<Result<MovieDetails>>

   fun getMovieReviews(id: String): Flow<PagingData<MovieReview>>

   suspend fun isMovieBookmarked(id: Int): Boolean

}