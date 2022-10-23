package com.syalim.themoviedb.core.ui.imageloader

import android.widget.ImageView


/**
 * Created by Rahmat Syalim on 2022/09/03
 * rahmatsyalim@gmail.com
 */
interface CustomImageLoader {

   fun imageUrl(url: String?): CustomImageLoader

   fun loadImageInto(imageView: ImageView)

   fun loadBackdropInto(imageView: ImageView)

   fun loadBackgroundInto(imageView: ImageView)

   fun loadAvatarInto(imageView: ImageView)

}