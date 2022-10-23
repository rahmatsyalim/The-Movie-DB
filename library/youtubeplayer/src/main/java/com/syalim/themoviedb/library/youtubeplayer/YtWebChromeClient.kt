package com.syalim.themoviedb.library.youtubeplayer

import android.webkit.PermissionRequest
import android.webkit.WebChromeClient


/**
 * Created by Rahmat Syalim on 2022/09/08
 * rahmatsyalim@gmail.com
 */

internal class YtWebChromeClient : WebChromeClient() {
   override fun onPermissionRequest(request: PermissionRequest?) {
      request?.deny()
   }
}