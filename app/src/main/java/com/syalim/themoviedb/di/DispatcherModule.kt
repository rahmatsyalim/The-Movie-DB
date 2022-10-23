package com.syalim.themoviedb.di

import com.syalim.themoviedb.core.common.IoDispatcher
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
internal object DispatcherModule {

   @Provides
   @IoDispatcher
   fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}