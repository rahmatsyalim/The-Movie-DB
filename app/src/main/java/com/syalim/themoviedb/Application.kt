package com.syalim.themoviedb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

@HiltAndroidApp
class Application : Application() {

   override fun onCreate() {
      super.onCreate()

      if (!BuildConfig.IS_RELEASE){
         Timber.plant(Timber.DebugTree())
      }

   }

}