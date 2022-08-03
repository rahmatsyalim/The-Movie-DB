package com.syalim.themoviedb.presentation.common.extensions

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.syalim.themoviedb.R
import com.syalim.themoviedb.utils.Constants.IMAGE_BACKDROP_BIG_SIZE


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

fun View.hide() {
   isVisible = false
}

fun View.show() {
   isVisible = true
}

fun View.showSnackBar(message: String) {
   val font = context.getFontFrom(R.font.poppins_regular)
   Snackbar.make(
      rootView,
      message,
      Snackbar.LENGTH_LONG
   ).apply {
      view.apply {
         findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            textSize = 12f
            typeface = font
         }
         findViewById<TextView>(com.google.android.material.R.id.snackbar_action).apply {
            textSize = 13f
            setTextColor(ContextCompat.getColor(context, R.color.primary))
            typeface = font
         }
      }
   }.show()
}

fun View.showSnackBarWithAction(message: String, actionText: String, doAction: () -> Unit) {
   val font = ResourcesCompat.getFont(context, R.font.poppins_regular)
   Snackbar.make(
      rootView,
      message,
      Snackbar.LENGTH_INDEFINITE
   ).apply {
      setAction(actionText) {
         doAction()
      }
      view.apply {
         setBackgroundColor(ContextCompat.getColor(context, R.color.black))
         findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            textSize = 12f
            typeface = font
         }
         findViewById<TextView>(com.google.android.material.R.id.snackbar_action).apply {
            textSize = 13f
            setTextColor(ContextCompat.getColor(context, R.color.primary))
            typeface = font
         }
      }
   }.show()
}

fun ImageView.loadImage(imagePath: String?, imageSize: String) {
   Glide
      .with(context)
      .load(imagePath.setImageUrl(imageSize))
      .diskCacheStrategy(DiskCacheStrategy.ALL)
      .error(ResourcesCompat.getDrawable(resources, R.drawable.placeholder_image, context.theme))
      .placeholder(ResourcesCompat.getDrawable(resources, R.drawable.bg_image_ph, context.theme))
      .into(this)
}

fun ImageView.loadProfileImage(imagePath: String?, imageSize: String) {
   Glide
      .with(context)
      .load(imagePath.setImageUrl(imageSize))
      .diskCacheStrategy(DiskCacheStrategy.ALL)
      .error(ResourcesCompat.getDrawable(resources, R.drawable.placeholder_avatar, context.theme))
      .placeholder(ResourcesCompat.getDrawable(resources, R.drawable.bg_image_ph, context.theme))
      .into(this)
}

fun ImageView.loadBackgroundImage(imagePath: String?) {
   Glide
      .with(context)
      .asBitmap()
      .load(imagePath.setImageUrl(IMAGE_BACKDROP_BIG_SIZE))
      .diskCacheStrategy(DiskCacheStrategy.ALL)
      .into(this)
}