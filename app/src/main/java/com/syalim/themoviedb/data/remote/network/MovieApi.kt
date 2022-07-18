package com.syalim.themoviedb.data.remote.network

import com.syalim.themoviedb.BuildConfig
import com.syalim.themoviedb.data.remote.dto.genre.GenreListDto
import com.syalim.themoviedb.data.remote.dto.movie_detail.MovieDetailDto
import com.syalim.themoviedb.data.remote.dto.movie_list.MovieListDto
import com.syalim.themoviedb.data.remote.dto.movie_review.ReviewListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
interface MovieApi {

   @GET("/3/movie/popular")
   suspend fun getPopularMovies(
      @Query("api_key") apiKey: String = BuildConfig.apiKey,
      @Query("language") lang: String = "en-US",
      @Query("page") page: Int
   ): MovieListDto

   @GET("/3/movie/now_playing")
   suspend fun getNowPlayingMovies(
      @Query("api_key") apiKey: String = BuildConfig.apiKey,
      @Query("language") lang: String = "en-US",
      @Query("page") page: Int
   ): MovieListDto

   @GET("/3/movie/upcoming")
   suspend fun getUpcomingMovies(
      @Query("api_key") apiKey: String = BuildConfig.apiKey,
      @Query("language") lang: String = "en-US",
      @Query("page") page: Int
   ): MovieListDto

   @GET("/3/movie/top_rated")
   suspend fun getTopRatedMovies(
      @Query("api_key") apiKey: String = BuildConfig.apiKey,
      @Query("language") lang: String = "en-US",
      @Query("page") page: Int
   ): MovieListDto

   @GET("/3/movie/latest")
   suspend fun getLatestMovies(
      @Query("api_key") apiKey: String = BuildConfig.apiKey,
      @Query("language") lang: String = "en-US",
      @Query("page") page: Int
   ): MovieListDto

   @GET("/3/discover/movie")
   suspend fun getMoviesByGenre(
      @Query("api_key") apiKey: String = BuildConfig.apiKey,
      @Query("language") lang: String = "en-US",
      @Query("page") page: Int,
      @Query("with_genres") genres: String
   ): MovieListDto

   @GET("/3/movie/{id}")
   suspend fun getMovieDetails(
      @Query("api_key") apiKey: String = BuildConfig.apiKey,
      @Path("id") id: String
   ): MovieDetailDto

   @GET("/3/genre/movie/list")
   suspend fun getMovieGenres(
      @Query("api_key") apiKey: String = BuildConfig.apiKey,
      @Query("language") lang: String = "en-US",
   ): GenreListDto

   @GET("/3/movie/{id}/reviews")
   suspend fun getMovieReviews(
      @Query("api_key") apiKey: String = BuildConfig.apiKey,
      @Path("id") id: String,
      @Query("page") page: Int
   ): ReviewListDto

}