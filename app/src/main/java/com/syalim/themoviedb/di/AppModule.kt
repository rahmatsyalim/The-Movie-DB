package com.syalim.themoviedb.di

import com.google.gson.GsonBuilder
import com.syalim.themoviedb.BuildConfig
import com.syalim.themoviedb.data.remote.api.MovieApi
import com.syalim.themoviedb.data.repository.MovieRepositoryImpl
import com.syalim.themoviedb.domain.repository.MovieRepository
import com.syalim.themoviedb.utils.Constants.BASE_URL
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
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

   @Provides
   @Singleton
   fun provideRetrofit(client: OkHttpClient): Retrofit {
      return Retrofit.Builder()
         .baseUrl(BASE_URL)
         .client(client)
         .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
         .build()
   }

   @Provides
   @Singleton
   fun provideOkhttpClient(headerInterceptor: Interceptor): OkHttpClient {
      return OkHttpClient
         .Builder()
         .connectTimeout(30L, TimeUnit.SECONDS)
         .readTimeout(30L, TimeUnit.SECONDS)
         .writeTimeout(30L, TimeUnit.SECONDS)
         .addInterceptor(headerInterceptor)
         .apply {
            if (!BuildConfig.IS_RELEASE) {
               addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            }
         }
         .build()
   }

   @Provides
   @Singleton
   fun provideHeaderInterceptor(): Interceptor {
      return Interceptor {
         val httpUrl = it.request().url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY).build()
         val requestBuilder = it.request().newBuilder()
         // add headers here

         it.proceed(requestBuilder.url(httpUrl).build())
      }
   }

   @Provides
   @Singleton
   fun provideMovieApi(retrofit: Retrofit): MovieApi {
      return retrofit.create(MovieApi::class.java)
   }

   @Provides
   @Singleton
   fun provideMovieRepository(api: MovieApi): MovieRepository {
      return MovieRepositoryImpl(api)
   }
}