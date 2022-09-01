package com.syalim.themoviedb.core.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


/**
 * Created by Rahmat Syalim on 2022/08/30
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

   @Provides
   @com.syalim.themoviedb.core.common.IoDispatcher
   fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}