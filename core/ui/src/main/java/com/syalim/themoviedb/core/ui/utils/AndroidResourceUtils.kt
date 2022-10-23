package com.syalim.themoviedb.core.ui.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.syalim.themoviedb.core.ui.R


/**
 * Created by Rahmat Syalim on 2022/08/16
 * rahmatsyalim@gmail.com
 */

fun Context.getDrawableFrom(@DrawableRes resId: Int): Drawable? {
   return ContextCompat.getDrawable(this, resId)
}

@ColorInt
fun Context.getColorFrom(@ColorRes resId: Int): Int {
   return ContextCompat.getColor(this, resId)
}

fun Context.getFontFrom(@FontRes resId: Int): Typeface? {
   return ResourcesCompat.getFont(this, resId)
}

fun Context.getColorStateListFrom(@ColorRes resId: Int): ColorStateList {
   return ColorStateList.valueOf(getColorFrom(resId))
}

val Context.fontNormal: Typeface get() = Typeface.create(getFontFrom(R.font.poppins_regular), Typeface.NORMAL)
val Context.fontBold: Typeface get() = Typeface.create(getFontFrom(R.font.poppins_regular), Typeface.BOLD)