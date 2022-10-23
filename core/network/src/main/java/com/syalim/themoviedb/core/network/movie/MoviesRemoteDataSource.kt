package com.syalim.themoviedb.core.network.movie

import com.syalim.themoviedb.core.network.movie.dto.MovieDetailsDto
import com.syalim.themoviedb.core.network.movie.dto.MovieGenresDto
import com.syalim.themoviedb.core.network.movie.dto.MovieReviewsDto
import com.syalim.themoviedb.core.network.movie.dto.MoviesDto


/**
 * Created by Rahmat Syalim on 2022/09/02
 * rahmatsyalim@gmail.com
 */

interface MoviesRemoteDataSource {

   suspend fun getMovies(category: String): MoviesDto

   suspend fun getMoviesPaged(category: String, page: Int): MoviesDto

   suspend fun getDiscoverMovies(page: Int, genre: String): MoviesDto

   suspend fun getMovieGenres(): MovieGenresDto

   suspend fun getMovieDetails(id: String): MovieDetailsDto

   suspend fun getMovieReviews(id: String, page: Int): MovieReviewsDto
}