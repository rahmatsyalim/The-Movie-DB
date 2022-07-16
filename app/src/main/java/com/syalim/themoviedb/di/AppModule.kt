package com.syalim.themoviedb.di

import com.syalim.themoviedb.common.Constants.BASE_URL
import com.syalim.themoviedb.data.remote.network.MovieApi
import com.syalim.themoviedb.data.repository.MovieRepositoryImpl
import com.syalim.themoviedb.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

   @Provides
   @Singleton
   fun provideMovieApi(): MovieApi {
      return Retrofit.Builder()
         .baseUrl(BASE_URL)
         .addConverterFactory(GsonConverterFactory.create())
         .client(
            OkHttpClient.Builder().apply {
               addInterceptor(
                  HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
               )
            }
               .connectTimeout(30, TimeUnit.SECONDS)
               .readTimeout(30, TimeUnit.SECONDS)
               .build()
         )
         .build()
         .create(MovieApi::class.java)
   }

   @Provides
   @Singleton
   fun provideMovieRepository(api: MovieApi): MovieRepository {
      return MovieRepositoryImpl(api)
   }
}