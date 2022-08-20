package com.syalim.themoviedb.presentation.common

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

   open fun submitData(data: List<T>) {
      differ.submitList(data)
   }

   final override fun getItemCount(): Int {
      return differ.currentList.size
   }

}