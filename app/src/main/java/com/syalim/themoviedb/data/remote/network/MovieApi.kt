package com.syalim.themoviedb.data.remote.network

import com.syalim.themoviedb.data.remote.dto.genre.GenreListDto
import com.syalim.themoviedb.data.remote.dto.movie_detail.MovieDetailDto
import com.syalim.themoviedb.data.remote.dto.movie_list.MovieListDto
import com.syalim.themoviedb.data.remote.dto.movie_review.ReviewListDto
import com.syalim.themoviedb.data.remote.dto.movie_trailer.MovieTrailerListDto
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
      @Query("page") page: Int
   ): MovieListDto

   @GET("/3/movie/now_playing")
   suspend fun getNowPlayingMovies(
      @Query("page") page: Int
   ): MovieListDto

   @GET("/3/movie/upcoming")
   suspend fun getUpcomingMovies(
      @Query("page") page: Int
   ): MovieListDto

   @GET("/3/movie/top_rated")
   suspend fun getTopRatedMovies(
      @Query("page") page: Int
   ): MovieListDto

   @GET("/3/movie/{id}/recommendations")
   suspend fun getRecommendationMovies(
      @Path("id") id: String,
      @Query("page") page: Int
   ): MovieListDto

   @GET("/3/discover/movie")
   suspend fun getMoviesByGenre(
      @Query("page") page: Int,
      @Query("with_genres") genres: String?
   ): MovieListDto

   @GET("/3/movie/{id}")
   suspend fun getMovieDetails(
      @Path("id") id: String,
   ): MovieDetailDto

   @GET("/3/genre/movie/list")
   suspend fun getMovieGenres(
   ): GenreListDto

   @GET("/3/movie/{id}/reviews")
   suspend fun getMovieReviews(
      @Path("id") id: String,
      @Query("page") page: Int
   ): ReviewListDto

   @GET("/3/movie/{id}/videos")
   suspend fun getMovieTrailer(
      @Path("id") id: String,
   ): MovieTrailerListDto

}