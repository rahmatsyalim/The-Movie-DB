package com.syalim.themoviedb.presentation.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.databinding.ItemCarouselBinding
import com.syalim.themoviedb.domain.model.MovieCarousel
import com.syalim.themoviedb.presentation.common.BaseAdapter
import com.syalim.themoviedb.presentation.common.CoilImageLoader
import com.syalim.themoviedb.presentation.common.CoilImageLoader.Companion.BACKDROP
import com.syalim.themoviedb.presentation.common.utils.showView
import com.syalim.themoviedb.presentation.common.utils.viewBinding
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

class CarouselAdapter @Inject constructor(
   private val coilImageLoader: CoilImageLoader
) : BaseAdapter<MovieCarousel, CarouselAdapter.ViewHolder>(Comparator) {

   var onItemClickListener: ((MovieCarousel) -> Unit)? = null

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return ViewHolder(parent.viewBinding(ItemCarouselBinding::inflate))
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(differ.currentList[position])
   }

   inner class ViewHolder(private val binding: ItemCarouselBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: MovieCarousel) {
         binding.apply {
            tvTitle.text = item.title
            tvDate.text = item.releaseDate
            coilImageLoader
               .imageUrl(item.backdropPath, BACKDROP)
               .loadInto(ivBackdrop)
               .job.invokeOnCompletion {
                  tvContainer.showView()
               }
         }
         itemView.setOnClickListener {
            onItemClickListener?.let { it(item) }
         }
      }
   }

   private object Comparator: DiffUtil.ItemCallback<MovieCarousel>() {
      override fun areItemsTheSame(oldItem: MovieCarousel, newItem: MovieCarousel): Boolean {
         return oldItem.id == newItem.id
      }
      override fun areContentsTheSame(oldItem: MovieCarousel, newItem: MovieCarousel): Boolean {
         return oldItem == newItem
      }
   }

}