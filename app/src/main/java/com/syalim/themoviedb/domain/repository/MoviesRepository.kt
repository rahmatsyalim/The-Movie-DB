package com.syalim.themoviedb.domain.repository

import androidx.paging.PagingData
import com.syalim.themoviedb.common.Resource
import com.syalim.themoviedb.domain.model.*
import kotlinx.coroutines.flow.Flow


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
interface MoviesRepository {
   fun getHomeInTheaterMovies(): Flow<Resource<List<MovieCarousel>>>
   fun getHomePopularMovies(): Flow<Resource<List<Movie>>>
   fun getHomeTopRatedMovies(): Flow<Resource<List<Movie>>>
   fun getHomeUpcomingMovies(): Flow<Resource<List<Movie>>>
   fun getDiscoverMovies(genre: String): Flow<PagingData<Movie>>
   fun getMovieGenres(): Flow<Resource<List<MovieGenre>>>
   fun getMovieDetail(id: String): Flow<Resource<MovieDetail>>
   fun getMovieReviews(id: String): Flow<PagingData<MovieReview>>
   fun getMovieTrailer(id: String): Flow<Resource<MovieTrailer>>
   fun getRecommendationMovies(id: String): Flow<Resource<List<Movie>>>
   fun isMovieBookmarked(id: Int): Flow<Boolean>
}