package com.syalim.themoviedb.domain.movie.repository

import androidx.paging.PagingData
import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.domain.movie.model.MovieDetail
import com.syalim.themoviedb.domain.movie.model.MovieGenre
import com.syalim.themoviedb.domain.movie.model.Movies
import kotlinx.coroutines.flow.Flow


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
interface MoviesRepository {
   fun getHomeInTheaterMovies(): Flow<Result<Movies>>
   fun getHomePopularMovies(): Flow<Result<Movies>>
   fun getHomeTopRatedMovies(): Flow<Result<Movies>>
   fun getHomeUpcomingMovies(): Flow<Result<Movies>>
   fun getDiscoverMovies(genre: String): Flow<PagingData<com.syalim.themoviedb.domain.movie.model.Movie>>
   fun getMovieGenres(): Flow<Result<List<MovieGenre>>>
   fun getMovieDetail(id: String): Flow<Result<MovieDetail>>
   fun getMovieReviews(id: String): Flow<PagingData<com.syalim.themoviedb.domain.movie.model.MovieReview>>
   fun isMovieBookmarked(id: Int): Flow<Boolean>
}