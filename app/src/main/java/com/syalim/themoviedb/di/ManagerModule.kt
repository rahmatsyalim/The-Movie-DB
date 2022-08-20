package com.syalim.themoviedb.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Rahmat Syalim on 2022/08/08
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
object ManagerModule {

   @Provides
   @Singleton
   fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
      return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
   }

   @Provides
   @Singleton
   fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
      return WorkManager.getInstance(context)
   }

   @Provides
   @Singleton
   fun provideNotificationManager(@ApplicationContext context: Context): NotificationManagerCompat {
      return NotificationManagerCompat.from(context)
   }
}