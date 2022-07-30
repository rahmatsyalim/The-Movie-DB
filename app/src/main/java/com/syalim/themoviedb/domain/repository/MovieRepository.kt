package com.syalim.themoviedb.domain.repository

import androidx.paging.PagingData
import com.syalim.themoviedb.utils.Resource
import com.syalim.themoviedb.domain.model.*
import kotlinx.coroutines.flow.Flow


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
interface MovieRepository {

   fun getHomeUpcomingMovies(): Flow<Resource<List<MovieItemEntity>>>

   fun getHomePopularMovies(): Flow<Resource<List<MovieItemEntity>>>

   fun getHomeNowPlayingMovies(): Flow<Resource<List<MovieItemEntity>>>

   fun getHomeTopRatedMovies(): Flow<Resource<List<MovieItemEntity>>>

   fun getMoviesByGenre(genre: String?): Flow<PagingData<MovieItemEntity>>

   fun getMovieGenres(): Flow<Resource<List<GenreItemEntity>>>

   fun getMovieDetail(id: String): Flow<Resource<MovieDetailEntity>>

   fun getMovieReviews(id: String): Flow<PagingData<ReviewItemEntity>>

   fun getMovieTrailer(id: String): Flow<Resource<MovieTrailerEntity>>

   fun getRecommendationMovies(id: String): Flow<Resource<List<MovieItemEntity>>>
}