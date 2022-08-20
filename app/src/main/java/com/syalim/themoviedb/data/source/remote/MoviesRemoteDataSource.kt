package com.syalim.themoviedb.data.source.remote

import com.syalim.themoviedb.data.source.remote.dto.*


/**
 * Created by Rahmat Syalim on 2022/08/10
 * rahmatsyalim@gmail.com
 */
interface MoviesRemoteDataSource {

   suspend fun getInTheaterMovies(page: Int): MoviesDto
   suspend fun getPopularMovies(page: Int): MoviesDto
   suspend fun getTopRatedMovies(page: Int): MoviesDto
   suspend fun getUpcomingMovies(page: Int): MoviesDto
   suspend fun getDiscoverMovies(page: Int, genre: String): MoviesDto
   suspend fun getMovieGenres(): MovieGenresDto
   suspend fun getMovieDetail(id: String): MovieDetailDto
   suspend fun getMovieReviews(id: String, page: Int): MovieReviewsDto
   suspend fun getMovieTrailer(id: String): MovieTrailersDto
   suspend fun getRecommendationMovies(id: String): MoviesDto
}