package com.syalim.themoviedb.core.ui

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Rahmat Syalim on 2022/09/05
 * rahmatsyalim@gmail.com
 */

class CustomGridLayoutManager<VH : RecyclerView.ViewHolder>(
   context: Context,
   spanCount: Int,
   private val adapter: RecyclerView.Adapter<VH>,
) {
   val get = GridLayoutManager(context, spanCount).apply {
      spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
         override fun getSpanSize(position: Int): Int {
            return if (position == adapter.itemCount && adapter.itemCount > 0) {
               spanCount
            } else {
               1
            }
         }
      }
   }
}