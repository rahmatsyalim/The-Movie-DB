package com.syalim.themoviedb.core.network.tvshow.api

import com.syalim.themoviedb.core.network.tvshow.dto.TvShowsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

interface TvShowsApi {

   @GET("/3/tv/{category}")
   suspend fun getTvShows(
      @Path("category") category: String,
      @Query("page") page: Int
   ): TvShowsDto

   @GET("/3/discover/tv")
   suspend fun getDiscoverTvShows(
      @Query("page") page: Int
   ): TvShowsDto
}