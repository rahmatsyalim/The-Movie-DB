package com.syalim.themoviedb.data.remote

import com.syalim.themoviedb.data.remote.api.MovieApi
import com.syalim.themoviedb.data.remote.dto.MovieDetailDto
import com.syalim.themoviedb.data.remote.dto.MovieGenresDto
import com.syalim.themoviedb.data.remote.dto.MovieReviewsDto
import com.syalim.themoviedb.data.remote.dto.MoviesDto
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/09
 * rahmatsyalim@gmail.com
 */

class MoviesRemoteDataSourceImpl @Inject constructor(
   private val api: MovieApi
) : MoviesRemoteDataSource {

   override suspend fun getInTheaterMovies(page: Int): MoviesDto = api.getInTheaterMovies(page)

   override suspend fun getUpcomingMovies(page: Int): MoviesDto = api.getUpcomingMovies(page)

   override suspend fun getPopularMovies(page: Int): MoviesDto = api.getPopularMovies(page)

   override suspend fun getTopRatedMovies(page: Int): MoviesDto = api.getTopRatedMovies(page)

   override suspend fun getDiscoverMovies(page: Int, genre: String): MoviesDto =
      api.getDiscoverMovies(page, genre)

   override suspend fun getMovieGenres(): MovieGenresDto = api.getMovieGenres()

   override suspend fun getMovieDetail(id: String): MovieDetailDto = api.getMovieDetails(id)

   override suspend fun getMovieReviews(id: String, page: Int): MovieReviewsDto =
      api.getMovieReviews(id, page)
}