package com.syalim.themoviedb.presentation.common

import android.content.Context
import android.widget.ImageView
import coil.ImageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import com.syalim.themoviedb.R
import com.syalim.themoviedb.common.Constants.IMAGE_URL
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/10
 * rahmatsyalim@gmail.com
 */
class CoilImageLoader @Inject constructor(
   @ApplicationContext private val context: Context,
   private val imageLoader: ImageLoader
) {

   companion object {
      const val POSTER_THUMBNAIL = "/w185"
      const val POSTER_DETAIL = "/w300"
      const val POSTER_ORIGINAL = "/original"
      const val BACKDROP = "/w500"
      const val AVATAR_THUMBNAIL = "/w45"
   }

   private var imageUrl: String? = null
   private var size: String? = null

   fun imageUrl(path: String?, size: String) = apply {
      this.size = size
      this.imageUrl = if (path.isNullOrBlank()) {
         null
      } else {
         IMAGE_URL + size + path
      }
   }

   fun imageUrl(url: String?) = apply {
      this.imageUrl = url
   }

   fun loadInto(imageView: ImageView): Disposable {
      val request = ImageRequest.Builder(context)
         .data(imageUrl)
         .apply {
            when (size) {
               BACKDROP -> {}
               AVATAR_THUMBNAIL -> {
                  placeholder(R.drawable.bg_image_ph)
                  error(R.drawable.placeholder_avatar)
               }
               else -> {
                  placeholder(R.drawable.bg_image_ph)
                  error(R.drawable.placeholder_image)
               }
            }
         }
         .target(imageView)
         .build()
      return imageLoader.enqueue(request)
   }
}

