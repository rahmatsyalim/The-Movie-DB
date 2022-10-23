package com.syalim.themoviedb.library.youtubeplayer

import android.app.Activity
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.google.android.material.progressindicator.CircularProgressIndicator


/**
 * Created by Rahmat Syalim on 2022/09/08
 * rahmatsyalim@gmail.com
 */

internal class YtJsInterface(
   private val activity: Activity,
   private val webView: WebView,
   private val progressBar: CircularProgressIndicator
) {
   @JavascriptInterface
   fun stopLoading() {
      activity.runOnUiThread {
         progressBar.visibility = View.GONE
         webView.removeJavascriptInterface("AndroidInterface")
      }
   }
}