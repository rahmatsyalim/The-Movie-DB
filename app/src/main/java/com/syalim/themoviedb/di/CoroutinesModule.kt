package com.syalim.themoviedb.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier


/**
 * Created by Rahmat Syalim on 2022/08/10
 * rahmatsyalim@gmail.com
 */

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IoDispatcher

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

   @Provides
   @IoDispatcher
   fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}