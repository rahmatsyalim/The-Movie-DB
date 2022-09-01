package com.syalim.themoviedb.data.connectivity.di

import com.syalim.themoviedb.data.connectivity.ConnectivityDataSource
import com.syalim.themoviedb.data.connectivity.ConnectivityDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Rahmat Syalim on 2022/08/10
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
interface ConnectivityDataSourceModule {

   @Binds
   @Singleton
   fun provideConnectivityDataSource(impl: ConnectivityDataSourceImpl): ConnectivityDataSource

}