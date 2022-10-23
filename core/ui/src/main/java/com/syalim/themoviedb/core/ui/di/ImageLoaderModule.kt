package com.syalim.themoviedb.core.ui.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.syalim.themoviedb.core.common.IoDispatcher
import com.syalim.themoviedb.core.ui.imageloader.CustomImageLoader
import com.syalim.themoviedb.core.ui.imageloader.CustomImageLoaderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton


/**
 * Created by Rahmat Syalim on 2022/09/01
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {
   @Provides
   @Singleton
   internal fun provideCoilImageLoader(
      @ApplicationContext context: Context,
      @IoDispatcher ioDispatcher: CoroutineDispatcher
   ): ImageLoader {
      return ImageLoader.Builder(context)
         .dispatcher(ioDispatcher)
         .allowHardware(true)
         .crossfade(true)
         .memoryCache {
            MemoryCache.Builder(context)
               .weakReferencesEnabled(true)
               .maxSizePercent(0.25)
               .build()
         }
         .diskCache {
            DiskCache.Builder()
               .cleanupDispatcher(ioDispatcher)
               .directory(context.cacheDir.resolve("cache/images"))
               .maxSizePercent(0.1)
               .build()
         }
         .build()
   }

   @Provides
   fun provideCustomImageLoader(
      @ApplicationContext context: Context,
      imageLoader: ImageLoader
   ): CustomImageLoader {
      return CustomImageLoaderImpl(context, imageLoader)
   }
}