package com.syalim.themoviedb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

@HiltAndroidApp
class App : Application() {

   override fun onCreate() {
      super.onCreate()

      if (BuildConfig.DEBUG) {
         Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
               super.log(priority, "Timber -> $tag", message, t)
            }
         })
      }

   }

}