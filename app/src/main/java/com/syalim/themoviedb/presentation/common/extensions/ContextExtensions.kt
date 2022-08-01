package com.syalim.themoviedb.presentation.common.extensions

import android.content.Context
import android.widget.Toast


/**
 * Created by Rahmat Syalim on 2022/07/31
 * rahmatsyalim@gmail.com
 */

fun Context.showToast(message: String) {
   Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}