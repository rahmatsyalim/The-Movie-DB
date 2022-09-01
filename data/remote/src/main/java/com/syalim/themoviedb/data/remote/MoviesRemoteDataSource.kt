package com.syalim.themoviedb.data.remote

import com.syalim.themoviedb.data.remote.dto.MovieDetailDto
import com.syalim.themoviedb.data.remote.dto.MovieGenresDto
import com.syalim.themoviedb.data.remote.dto.MovieReviewsDto
import com.syalim.themoviedb.data.remote.dto.MoviesDto


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
}