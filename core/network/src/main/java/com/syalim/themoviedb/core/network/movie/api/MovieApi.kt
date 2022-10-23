package com.syalim.themoviedb.core.network.movie.api

import com.syalim.themoviedb.core.network.movie.dto.MovieDetailsDto
import com.syalim.themoviedb.core.network.movie.dto.MovieGenresDto
import com.syalim.themoviedb.core.network.movie.dto.MovieReviewsDto
import com.syalim.themoviedb.core.network.movie.dto.MoviesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
interface MovieApi {

   @GET("/3/movie/{category}")
   suspend fun getMovies(
      @Path("category") category: String,
      @Query("page") page: Int
   ): MoviesDto

   @GET("/3/discover/movie")
   suspend fun getDiscoverMovies(
      @Query("page") page: Int,
      @Query("with_genres") genres: String?
   ): MoviesDto

   @GET("/3/movie/{id}")
   suspend fun getMovieDetails(
      @Path("id") id: String,
      @Query("append_to_response") append: String = "videos,recommendations,reviews"
   ): MovieDetailsDto

   @GET("/3/genre/movie/list")
   suspend fun getMovieGenres(
   ): MovieGenresDto

   @GET("/3/movie/{id}/reviews")
   suspend fun getMovieReviews(
      @Path("id") id: String,
      @Query("page") page: Int
   ): MovieReviewsDto

}