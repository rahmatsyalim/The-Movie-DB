package com.syalim.themoviedb.data.connectivity.di

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Rahmat Syalim on 2022/08/30
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
object ConnectivityModule {
   @Provides
   @Singleton
   fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
      return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
   }
}