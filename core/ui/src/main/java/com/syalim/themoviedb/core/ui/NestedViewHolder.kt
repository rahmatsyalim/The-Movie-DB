package com.syalim.themoviedb.core.ui

import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Rahmat Syalim on 2022/08/21
 * rahmatsyalim@gmail.com
 */

interface NestedViewHolder {
   fun getId(): String
   fun getLayoutManager(): RecyclerView.LayoutManager?
}