package com.syalim.themoviedb.presentation.main.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.databinding.ItemCarouselBinding
import com.syalim.themoviedb.presentation.utils.BaseAdapter
import com.syalim.themoviedb.presentation.utils.CoilImageLoader
import com.syalim.themoviedb.presentation.utils.ext.isVisible
import com.syalim.themoviedb.presentation.utils.ext.viewBinding
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

class HomeCarouselAdapter @Inject constructor(
   private val coilImageLoader: CoilImageLoader
) : BaseAdapter<com.syalim.themoviedb.domain.movie.model.Movie, HomeCarouselAdapter.ViewHolder>(DataComparator) {

   var onItemClickListener: ((com.syalim.themoviedb.domain.movie.model.Movie) -> Unit)? = null

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return ViewHolder(parent.viewBinding(ItemCarouselBinding::inflate))
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(getItem(position))
   }

   inner class ViewHolder(private val binding: ItemCarouselBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: com.syalim.themoviedb.domain.movie.model.Movie) {
         binding.apply {
            tvTitle.text = item.title
            tvDate.text = item.releaseDate
            coilImageLoader
               .imageUrl(item.backdropPath)
               .loadBackdropInto(ivBackdrop)
               .job.invokeOnCompletion {
                  tvContainer.isVisible()
               }
         }
         itemView.setOnClickListener {
            onItemClickListener?.let { it(item) }
         }
      }
   }

   private object DataComparator : DiffUtil.ItemCallback<com.syalim.themoviedb.domain.movie.model.Movie>() {
      override fun areItemsTheSame(
         oldItem: com.syalim.themoviedb.domain.movie.model.Movie,
         newItem: com.syalim.themoviedb.domain.movie.model.Movie
      ): Boolean {
         return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
         oldItem: com.syalim.themoviedb.domain.movie.model.Movie,
         newItem: com.syalim.themoviedb.domain.movie.model.Movie
      ): Boolean {
         return oldItem == newItem
      }
   }

}