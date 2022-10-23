package com.syalim.themoviedb.core.ui

import android.os.Parcelable
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference


/**
 * Created by Rahmat Syalim on 2022/08/21
 * rahmatsyalim@gmail.com
 */

abstract class BaseNestedAdapter<T : Any, VH : RecyclerView.ViewHolder>(
   diffUtil: DiffUtil.ItemCallback<T>
) : BaseAdapter<T, VH>(diffUtil) {

   private data class ViewHolderRef(
      val id: String,
      val reference: WeakReference<NestedViewHolder>
   )

   private val layoutManagerStates = hashMapOf<String, Parcelable?>()
   private val visibleScrollableViews = hashMapOf<Int, ViewHolderRef>()

   @CallSuper
   override fun onViewRecycled(holder: VH) {
      if (holder is NestedViewHolder) {
         val state = holder.getLayoutManager()?.onSaveInstanceState()
         layoutManagerStates[holder.getId()] = state
         // State saved and view is not visible
         visibleScrollableViews.remove(holder.hashCode())
      }
      super.onViewRecycled(holder)
   }

   @CallSuper
   override fun onBindViewHolder(holder: VH, position: Int) {
      if (holder is NestedViewHolder) {
         holder.getLayoutManager()?.let {
            // Retrieve and set the saved LayoutManager state
            val state: Parcelable? = layoutManagerStates[holder.getId()]
            if (state != null) {
               it.onRestoreInstanceState(state)
            } else {
               // reset the scroll position when no state needs to be restored
               it.scrollToPosition(0)
            }
         }
         visibleScrollableViews[holder.hashCode()] = ViewHolderRef(
            holder.getId(),
            WeakReference(holder as NestedViewHolder)
         )
      }
   }

   @CallSuper
   override fun submitData(data: List<T>) {
      saveState()
      super.submitData(data)
   }

   private fun saveState() {
      visibleScrollableViews.values.forEach { item ->
         item.reference.get()?.let {
            layoutManagerStates[item.id] = it.getLayoutManager()?.onSaveInstanceState()
         }
      }
      visibleScrollableViews.clear()
   }

   fun clearState() {
      layoutManagerStates.clear()
      visibleScrollableViews.clear()
   }

}