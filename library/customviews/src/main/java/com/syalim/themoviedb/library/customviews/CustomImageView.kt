package com.syalim.themoviedb.library.customviews

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.imageview.ShapeableImageView


/**
 * Created by Rahmat Syalim on 2022/09/12
 * rahmatsyalim@gmail.com
 */

class CustomImageView @JvmOverloads constructor(
   context: Context,
   private val attrs: AttributeSet? = null,
   private val defStyleAttr: Int = 0
) : ShapeableImageView(context, attrs, defStyleAttr) {

   private var aspectRatio: List<Int>? = null

   init {
      initView()
   }

   private fun initView() {
      initAttrs()
      scaleType = ScaleType.CENTER_CROP
   }

   private fun initAttrs() {
      context.theme.obtainStyledAttributes(
         attrs,
         R.styleable.CustomImageView,
         defStyleAttr,
         0
      ).apply {
         try {
            aspectRatio = getString(R.styleable.CustomImageView_image_aspectRatio).getRatio()
         } finally {
            recycle()
         }
      }
   }

   override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
      val defWidth = MeasureSpec.getSize(widthMeasureSpec)
      var defHeight = MeasureSpec.getSize(heightMeasureSpec)
      aspectRatio?.let {
         val desiredWidthRatio = it[0]
         var desiredHeightRatio = it[1]
         if (desiredWidthRatio > 0) {
            desiredHeightRatio = if (desiredHeightRatio == 0) 1 else desiredHeightRatio
            defHeight = defWidth / desiredWidthRatio * desiredHeightRatio
         }
      }
      super.onMeasure(
         MeasureSpec.makeMeasureSpec(defWidth, MeasureSpec.EXACTLY),
         MeasureSpec.makeMeasureSpec(defHeight, MeasureSpec.EXACTLY)
      )
   }

   private fun String?.getRatio(): List<Int>? {
      return this?.let {
         split(":").let {
            if (it.size > 1) it.map { v -> v.toIntOrNull() ?: 0 }
            else listOf(this, "0").map { v -> v.toIntOrNull() ?: 0 }
         }
      }
   }
}