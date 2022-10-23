package com.syalim.themoviedb.library.youtubeplayer

import android.app.Activity
import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient


/**
 * Created by Rahmat Syalim on 2022/09/08
 * rahmatsyalim@gmail.com
 */

internal class YtWebViewClient(
   private val activity: Activity,
   private val requiredUrl: String,
   private val videoId: String?
) : WebViewClient() {
   override fun onPageFinished(view: WebView?, url: String?) {
      url?.let {
         if (it == requiredUrl) {
            view?.evaluateJavascript("loadYouTubeIframeAPI('$videoId')", null)
         }
      }
   }

   override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
      request?.let {
         val intent = Intent(Intent.ACTION_VIEW, it.url)
         activity.startActivity(intent)
      }
      return true
   }
}