package com.syalim.themoviedb.presentation.movie_detail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.webkit.*
import android.webkit.WebView.RENDERER_PRIORITY_IMPORTANT


/**
 * Created by Rahmat Syalim on 2022/08/11
 * rahmatsyalim@gmail.com
 */

class YoutubePlayer private constructor(
   private val activity: Activity?,
   private val webView: WebView?,
   private val videoId: String?,
   private val loading: ((Boolean) -> Unit)?
) {

   private companion object {
      const val HTML_FILE = "file:///android_asset/youtube_player/index.html"
   }

   inner class YtWebViewClient : WebViewClient() {
      override fun onPageFinished(view: WebView?, url: String?) {
         url?.let {
            if (it == HTML_FILE) {
               view?.evaluateJavascript("loadYouTubeIframeAPI('$videoId')", null)
            }
         }
      }

      override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
         request?.let {
            val intent = Intent(Intent.ACTION_VIEW, it.url)
            activity?.startActivity(intent)
         }
         return true
      }
   }

   inner class YtWebChromeClient : WebChromeClient() {
      override fun onPermissionRequest(request: PermissionRequest?) {
         request?.deny()
      }
   }

   inner class JsInterface {
      @JavascriptInterface
      fun stopLoading() {
         activity?.runOnUiThread {
            this@YoutubePlayer.loading?.invoke(false)
            webView?.removeJavascriptInterface("AndroidInterface")
         }
      }
   }

   @SuppressLint("SetJavaScriptEnabled")
   private fun execute() = apply {
      activity?.let {
         webView?.apply {
            loading?.invoke(true)
            setBackgroundColor(Color.argb(1, 0, 0, 0))
            if (Build.VERSION.SDK_INT >= 26) {
               setRendererPriorityPolicy(RENDERER_PRIORITY_IMPORTANT, true)
            }
            webViewClient = YtWebViewClient()
            webChromeClient = YtWebChromeClient()
            addJavascriptInterface(JsInterface(), "AndroidInterface")
            settings.apply {
               javaScriptEnabled = true
               domStorageEnabled = true
               useWideViewPort = true
            }
         }?.loadUrl(HTML_FILE)
      }
   }

   class Builder(private val activity: Activity) {
      private var webView: WebView? = null
      private var videoId: String? = null
      private var loading: ((Boolean) -> Unit)? = null

      fun webView(webView: WebView) = apply {
         this.webView = webView
      }

      fun videoId(videoId: String?) = apply {
         if (videoId != null) {
            this.videoId = videoId
         }
      }

      fun onLoading(block: (Boolean) -> Unit) = apply {
         this.loading = block
      }

      fun load(): YoutubePlayer {
         return YoutubePlayer(activity, webView, videoId, loading).execute()
      }

   }

}