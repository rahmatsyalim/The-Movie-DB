package com.syalim.themoviedb.core.network.movie

import com.syalim.themoviedb.core.network.movie.api.MovieApi
import com.syalim.themoviedb.core.network.movie.dto.MovieDetailsDto
import com.syalim.themoviedb.core.network.movie.dto.MovieGenresDto
import com.syalim.themoviedb.core.network.movie.dto.MovieReviewsDto
import com.syalim.themoviedb.core.network.movie.dto.MoviesDto


/**
 * Created by Rahmat Syalim on 2022/09/02
 * rahmatsyalim@gmail.com
 */

internal class MoviesRemoteDataSourceImpl(
   private val api: MovieApi
) : MoviesRemoteDataSource {
   override suspend fun getMovies(category: String): MoviesDto {
      return api.getMovies(category, 1)
   }

   override suspend fun getMoviesPaged(category: String, page: Int): MoviesDto {
      return api.getMovies(category, page)
   }

   override suspend fun getDiscoverMovies(page: Int, genre: String): MoviesDto {
      return api.getDiscoverMovies(page, genre)
   }

   override suspend fun getMovieGenres(): MovieGenresDto {
      return api.getMovieGenres()
   }

   override suspend fun getMovieDetails(id: String): MovieDetailsDto {
      return api.getMovieDetails(id)
   }

   override suspend fun getMovieReviews(id: String, page: Int): MovieReviewsDto {
      return api.getMovieReviews(id, page)
   }
}