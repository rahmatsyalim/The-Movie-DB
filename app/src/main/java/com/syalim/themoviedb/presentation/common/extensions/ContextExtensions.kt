package com.syalim.themoviedb.presentation.common.extensions

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat


/**
 * Created by Rahmat Syalim on 2022/07/31
 * rahmatsyalim@gmail.com
 */

fun Context.showToast(message: String) {
   Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.getDrawableFrom(@DrawableRes resId: Int): Drawable? {
   return ContextCompat.getDrawable(this, resId)
}

fun Context.getColorFrom(@ColorRes resId: Int): Int {
   return ContextCompat.getColor(this, resId)
}

fun Context.getFontFrom(@FontRes resId: Int): Typeface? {
   return ResourcesCompat.getFont(this, resId)
}