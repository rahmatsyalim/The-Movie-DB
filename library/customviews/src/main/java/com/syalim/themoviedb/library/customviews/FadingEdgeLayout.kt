package com.syalim.themoviedb.library.customviews

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout


/**
 * Created by Rahmat Syalim on 2022/09/15
 * rahmatsyalim@gmail.com
 */

class FadingEdgeLayout @JvmOverloads constructor(
   context: Context,
   private val attrs: AttributeSet? = null,
   private val defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

   companion object {
      const val FADING_TOP_FLAG = 1
      const val FADING_BOTTOM_FLAG = 2
      const val FADING_VERTICAL = 3
      const val FADING_LEFT_FLAG = 4
      const val FADING_RIGHT_FLAG = 8
      const val FADING_HORIZONTAL = 12
      const val FADING_ALL = 15
      const val DEFAULT_FADING_SIZE = 0
   }

   private var isFadingTop: Boolean = false
   private var isFadingBottom: Boolean = false
   private var isFadingLeft: Boolean = false
   private var isFadingRight: Boolean = false
   private var fadingSize: Int = DEFAULT_FADING_SIZE
   private val fadingPaintTop by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
   private val fadingPaintBottom by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
   private val fadingPaintLeft by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
   private val fadingPaintRight by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
   private val fadingRectTop by lazy { Rect() }
   private val fadingRectBottom by lazy { Rect() }
   private val fadingRectLeft by lazy { Rect() }
   private val fadingRectRight by lazy { Rect() }

   private var fadeColor = intArrayOf(Color.TRANSPARENT, Color.BLACK)
   private var fadeColorReverse = intArrayOf(Color.BLACK, Color.TRANSPARENT)

   init {
      initView()
   }

   private fun initView() {
      initAttrs()
   }

   private fun initAttrs() {
      context.theme.obtainStyledAttributes(
         attrs,
         R.styleable.FadingEdgeLayout,
         defStyle,
         0
      ).apply {
         try {
            val flag = getInt(R.styleable.FadingEdgeLayout_fading_edge, 0)
            fadingSize = getDimensionPixelSize(R.styleable.FadingEdgeLayout_fading_size, DEFAULT_FADING_SIZE)
            isFadingTop =
               (flag == FADING_TOP_FLAG || flag == FADING_VERTICAL || flag == FADING_ALL) && fadingSize > 0
            isFadingBottom =
               (flag == FADING_BOTTOM_FLAG || flag == FADING_VERTICAL || flag == FADING_ALL) && fadingSize > 0
            isFadingLeft =
               (flag == FADING_LEFT_FLAG || flag == FADING_HORIZONTAL || flag == FADING_ALL) && fadingSize > 0
            isFadingRight =
               (flag == FADING_RIGHT_FLAG || flag == FADING_HORIZONTAL || flag == FADING_ALL) && fadingSize > 0
         } finally {
            recycle()
         }
      }
   }

   override fun dispatchDraw(canvas: Canvas?) {
      val newWidth = width.toFloat()
      val newHeight = height.toFloat()
      val isFadingEdge = isFadingTop || isFadingBottom || isFadingLeft || isFadingRight
      if (visibility == GONE || newWidth == 0f || newHeight == 0f || !isFadingEdge) {
         super.dispatchDraw(canvas)
         return
      }
      if (isFadingTop) setupTopFading()
      if (isFadingBottom) setupBottomFading()
      if (isFadingLeft) setupLeftFading()
      if (isFadingRight) setupRightFading()
      val count = canvas!!.saveLayer(0.toFloat(), 0.toFloat(), newWidth, newHeight, null)
      super.dispatchDraw(canvas)
      if (isFadingTop) canvas.drawRect(fadingRectTop, fadingPaintTop)
      if (isFadingBottom) canvas.drawRect(fadingRectBottom, fadingPaintBottom)
      if (isFadingLeft) canvas.drawRect(fadingRectLeft, fadingPaintLeft)
      if (isFadingRight) canvas.drawRect(fadingRectRight, fadingPaintRight)
      canvas.restoreToCount(count)
   }

   private fun setupTopFading() {
      fadingPaintTop.posterDuffOrBlend()
      val actualHeight = height - paddingTop - paddingBottom
      val size = fadingSize.coerceAtMost(actualHeight)
      val l = paddingLeft
      val t = paddingTop
      val r = width - paddingRight
      val b = t + size
      fadingRectTop.set(l, t, r, b)
      val gradientShader = LinearGradient(
         l.toFloat(), t.toFloat(), l.toFloat(), b.toFloat(), fadeColor, null,
         Shader.TileMode.CLAMP
      )
      fadingPaintTop.shader = gradientShader
   }

   private fun setupBottomFading() {
      fadingPaintBottom.posterDuffOrBlend()
      val actualHeight = height - paddingTop - paddingBottom
      val size = fadingSize.coerceAtMost(actualHeight)
      val l = paddingLeft
      val t = paddingTop + actualHeight - size
      val r = width - paddingRight
      val b = t + size
      fadingRectBottom.set(l, t, r, b)
      val gradientShader = LinearGradient(
         l.toFloat(), t.toFloat(), l.toFloat(), b.toFloat(), fadeColorReverse,
         null, Shader.TileMode.CLAMP
      )
      fadingPaintBottom.shader = gradientShader
   }

   private fun setupLeftFading() {
      fadingPaintLeft.posterDuffOrBlend()
      val actualWidth = width - paddingLeft - paddingRight
      val size = fadingSize.coerceAtMost(actualWidth)
      val l = paddingLeft
      val t = paddingTop
      val r = l + size
      val b = height - paddingBottom
      fadingRectLeft.set(l, t, r, b)
      val gradientShader = LinearGradient(
         l.toFloat(), t.toFloat(), r.toFloat(), t.toFloat(), fadeColor,
         null, Shader.TileMode.CLAMP
      )
      fadingPaintLeft.shader = gradientShader
   }

   private fun setupRightFading() {
      fadingPaintRight.posterDuffOrBlend()
      val actualWidth = width - paddingLeft - paddingRight
      val size = fadingSize.coerceAtMost(actualWidth)
      val l = paddingLeft + actualWidth - size
      val t = paddingTop
      val r = l + size
      val b = height - paddingBottom
      fadingRectRight.set(l, t, r, b)
      val gradientShader = LinearGradient(
         l.toFloat(), t.toFloat(), r.toFloat(), t.toFloat(), fadeColorReverse,
         null, Shader.TileMode.CLAMP
      )
      fadingPaintRight.shader = gradientShader
   }

   private fun Paint.posterDuffOrBlend() {
      if (Build.VERSION.SDK_INT >= 29) {
         blendMode = BlendMode.DST_IN
      } else {
         xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
      }
   }

}