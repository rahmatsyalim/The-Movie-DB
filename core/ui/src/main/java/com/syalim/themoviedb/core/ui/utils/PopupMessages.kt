package com.syalim.themoviedb.core.ui.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


/**
 * Created by Rahmat Syalim on 2022/08/16
 * rahmatsyalim@gmail.com
 */

fun Context.showLongToast(message: String) {
   Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showShortToast(message: String) {
   Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.showSnackBar(message: String, anchor: View? = null) {
   Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
      .setAnchorView(anchor)
      .apply {
//         setBackgroundTint(context.getColorFrom(R.color.brand_dark))
         view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
//            setTextColor(context.getColorFrom(R.color.text_normal))
            textSize = 12f
            typeface = context.fontNormal
         }
      }
      .show()
}

fun View.showSnackBarWithAction(message: String, actionText: String, anchor: View? = null, block: Snackbar.() -> Unit) {
   Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE)
      .setAnchorView(anchor)
      .apply {
         setAction(actionText) {
            block.invoke(this)
         }
         view.apply {
//            setBackgroundTint(context.getColorFrom(R.color.brand_dark))
            findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
//               setTextColor(context.getColorFrom(R.color.text_normal))
               textSize = 12f
               typeface = context.fontNormal
            }
            findViewById<TextView>(com.google.android.material.R.id.snackbar_action).apply {
               textSize = 13f
//               setTextColor(context.getColorFrom(R.color.brand_main))
               typeface = context.fontBold
            }
         }
      }
      .show()
}