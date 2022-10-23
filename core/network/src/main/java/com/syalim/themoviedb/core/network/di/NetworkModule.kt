package com.syalim.themoviedb.core.network.di

import com.syalim.themoviedb.core.network.BuildConfig
import com.syalim.themoviedb.core.network.movie.api.MovieApi
import com.syalim.themoviedb.core.network.tvshow.api.TvShowsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Rahmat Syalim on 2022/08/30
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

   @Provides
   @Singleton
   fun provideMovieApi(): MovieApi {
      return retrofit.create(MovieApi::class.java)
   }

   @Provides
   @Singleton
   fun provideTvShowsApi(): TvShowsApi {
      return retrofit.create(TvShowsApi::class.java)
   }

   private val retrofit by lazy {
      Retrofit.Builder()
         .baseUrl(BuildConfig.BASE_URL)
         .client(client)
         .addConverterFactory(GsonConverterFactory.create())
         .build()
   }

   private val client by lazy {
      OkHttpClient.Builder()
         .connectTimeout(30L, TimeUnit.SECONDS)
         .readTimeout(30L, TimeUnit.SECONDS)
         .writeTimeout(30L, TimeUnit.SECONDS)
         .addInterceptor(headerInterceptor)
         .apply {
            if (BuildConfig.DEBUG) {
               addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            }
         }
         .build()
   }

   private val headerInterceptor by lazy {
      Interceptor {
         val httpUrl = it.request().url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY).build()
         val requestBuilder = it.request().newBuilder()
         // add headers here

         it.proceed(requestBuilder.url(httpUrl).build())
      }
   }
}