package com.syalim.themoviedb.library.youtubeplayer

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import com.google.android.material.progressindicator.CircularProgressIndicator


/**
 * Created by Rahmat Syalim on 2022/09/12
 * rahmatsyalim@gmail.com
 */

class YtPlayerView @JvmOverloads constructor(
   context: Context,
   private val attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

   private companion object {
      const val HTML_FILE = "file:///android_asset/youtube_player/index.html"
      val Int.dp: Int get() = times(Resources.getSystem().displayMetrics.density.toInt())
   }

   private var ytPlayerAspectRatio: List<Int> = listOf(16, 9)

   @ColorInt
   private val ytPlayerBackgroundColor: Int = Color.rgb(0, 0, 0)

   private var ytVideoId: String? = null

   private val webView by lazy {
      WebView(context).apply {
         layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
         )
         setLayerType(LAYER_TYPE_HARDWARE, null)
         setBackgroundColor(ytPlayerBackgroundColor)
         isVerticalScrollBarEnabled = false
         isHorizontalScrollBarEnabled = false
      }
   }

   private val progressBar by lazy {
      CircularProgressIndicator(context).apply {
         layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
         ).apply {
            gravity = Gravity.CENTER
         }
         isIndeterminate = true
         trackThickness = 2.dp
      }
   }

   init {
      initView()
   }

   private fun initView() {
      setBackgroundColor(ytPlayerBackgroundColor)
      initAttrs()
      addView(webView)
      addView(progressBar)
   }

   private fun initAttrs() {
      context.theme.obtainStyledAttributes(
         attrs,
         R.styleable.YtPlayerView,
         0,
         0
      ).apply {
         getString(R.styleable.YtPlayerView_player_aspectRatio)
            .getRatio()?.let { ytPlayerAspectRatio = it }
         ytVideoId = getString(R.styleable.YtPlayerView_player_videoId)
      }
   }

   @Deprecated("Deprecated in Java")
   override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
      val defWidth = MeasureSpec.getSize(widthMeasureSpec)
      val defHeight: Int
      val desiredWidthRatio = ytPlayerAspectRatio[0]
      var desiredHeightRatio = ytPlayerAspectRatio[1]
      desiredHeightRatio = if (desiredHeightRatio == 0) 1 else desiredHeightRatio
      defHeight = defWidth / desiredWidthRatio * desiredHeightRatio
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

   inner class Builder(private val activity: Activity) {

      fun ytVideoId(videoId: String?) = apply {
         if (videoId != null) {
            ytVideoId = videoId
         }
      }

      @SuppressLint("SetJavaScriptEnabled")
      fun build() {
         with(webView) {
            if (Build.VERSION.SDK_INT >= 26) {
               setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_IMPORTANT, true)
            }
            webViewClient = YtWebViewClient(activity, HTML_FILE, ytVideoId)
            webChromeClient = YtWebChromeClient()
            addJavascriptInterface(YtJsInterface(activity, webView, progressBar), "AndroidInterface")
            settings.apply {
               javaScriptEnabled = true
               domStorageEnabled = true
               useWideViewPort = true
            }
         }
         webView.loadUrl(HTML_FILE)
      }
   }
}