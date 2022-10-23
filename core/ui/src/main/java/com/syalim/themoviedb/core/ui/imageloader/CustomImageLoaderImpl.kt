package com.syalim.themoviedb.core.ui.imageloader

import android.content.Context
import android.widget.ImageView
import coil.ImageLoader
import coil.request.ImageRequest
import com.syalim.themoviedb.core.ui.R


/**
 * Created by Rahmat Syalim on 2022/09/03
 * rahmatsyalim@gmail.com
 */

internal class CustomImageLoaderImpl(
   private val context: Context,
   private val imageLoader: ImageLoader
) : CustomImageLoader {

   private var imageUrl: String? = null

   override fun imageUrl(url: String?): CustomImageLoader = apply {
      this.imageUrl = url
   }

   override fun loadImageInto(imageView: ImageView) {
      ImageRequest.Builder(context)
         .data(imageUrl)
         .target(imageView)
         .placeholder(R.drawable.placeholder_background)
         .error(R.drawable.no_image)
         .build()
         .execute()
   }

   override fun loadBackdropInto(imageView: ImageView) {
      ImageRequest.Builder(context)
         .data(imageUrl)
         .target(imageView)
         .placeholder(R.drawable.placeholder_background)
         .error(R.drawable.no_image)
         .build()
         .execute()
   }

   override fun loadBackgroundInto(imageView: ImageView) {
      ImageRequest.Builder(context)
         .data(imageUrl)
         .target(imageView)
         .build()
         .execute()
   }

   override fun loadAvatarInto(imageView: ImageView) {
      ImageRequest.Builder(context)
         .data(imageUrl)
         .target(imageView)
         .placeholder(R.drawable.placeholder_background)
         .error(R.drawable.no_avatar_image)
         .build()
         .execute()
   }

   private fun ImageRequest.execute() {
      imageLoader.enqueue(this)
   }
}