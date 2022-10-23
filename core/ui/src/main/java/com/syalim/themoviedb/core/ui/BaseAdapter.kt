package com.syalim.themoviedb.core.ui

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Rahmat Syalim on 2022/08/13
 * rahmatsyalim@gmail.com
 */
abstract class BaseAdapter<T : Any, VH : RecyclerView.ViewHolder> constructor(
   diffCallback: DiffUtil.ItemCallback<T>
) : RecyclerView.Adapter<VH>() {

   private val adapterInstance get() = this

   protected val differ = AsyncListDiffer(adapterInstance, diffCallback)

   protected fun getItem(position: Int): T = differ.currentList[position]

   @CallSuper
   open fun submitData(data: List<T>) {
      differ.submitList(data)
   }

   final override fun getItemCount(): Int {
      return differ.currentList.size
   }

   protected fun View.setWidthTo3itemsInRow(
      parent: ViewGroup,
      totalRowPadding: Int
   ) {
      layoutParams.width = parent.measuredWidth.minus(totalRowPadding.dp).times(0.33).toInt()
   }

   private val Int.dp: Int get() = times(Resources.getSystem().displayMetrics.density.toInt())

}