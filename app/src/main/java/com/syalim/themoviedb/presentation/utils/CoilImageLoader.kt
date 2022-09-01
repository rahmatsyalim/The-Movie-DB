package com.syalim.themoviedb.presentation.utils

import android.content.Context
import android.widget.ImageView
import coil.ImageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import com.syalim.themoviedb.R
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
   private var imageUrl: String? = null

   fun imageUrl(url: String?) = apply {
      this.imageUrl = url
   }

   fun loadPosterInto(imageView: ImageView): Disposable {
      val request = ImageRequest.Builder(context)
         .data(imageUrl)
         .target(imageView)
         .placeholder(R.drawable.bg_image_ph)
         .error(R.drawable.placeholder_image)
         .build()
      return imageLoader.enqueue(request)
   }

   fun loadBackdropInto(imageView: ImageView): Disposable {
      val request = ImageRequest.Builder(context)
         .data(imageUrl)
         .target(imageView)
         .placeholder(R.drawable.bg_image_ph)
         .error(R.drawable.placeholder_image)
         .build()
      return imageLoader.enqueue(request)
   }

   fun loadBackgroundInto(imageView: ImageView): Disposable {
      val request = ImageRequest.Builder(context)
         .data(imageUrl)
         .target(imageView)
         .build()
      return imageLoader.enqueue(request)
   }

   fun loadAvatarInto(imageView: ImageView): Disposable {
      val request = ImageRequest.Builder(context)
         .data(imageUrl)
         .target(imageView)
         .placeholder(R.drawable.bg_image_ph)
         .error(R.drawable.placeholder_avatar)
         .build()
      return imageLoader.enqueue(request)
   }
}

