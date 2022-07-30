package com.syalim.themoviedb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.utils.Constants.IMAGE_BACKDROP_THUMBNAIL_SIZE
import com.syalim.themoviedb.utils.dateToViewDate
import com.syalim.themoviedb.utils.loadImage
import com.syalim.themoviedb.databinding.ItemCarouselBinding
import com.syalim.themoviedb.domain.model.MovieItemEntity


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class HomeCarouselAdapter :
   RecyclerView.Adapter<HomeCarouselAdapter.CarouselViewHolder>() {

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
      return CarouselViewHolder(
         ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
   }

   override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
      holder.bind(data.currentList[position])
   }

   override fun getItemCount(): Int {
      return data.currentList.size
   }

   inner class CarouselViewHolder(private val binding: ItemCarouselBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: MovieItemEntity) {
         binding.apply {
            ivBackdrop.loadImage(item.backdropPath, IMAGE_BACKDROP_THUMBNAIL_SIZE)
            tvTitle.text = item.title
            tvDate.text = item.releaseDate.dateToViewDate()
         }
         itemView.setOnClickListener {
            onItemClickListener?.let { it(item) }
         }
      }
   }

   object CarouselComparator : DiffUtil.ItemCallback<MovieItemEntity>() {
      override fun areItemsTheSame(oldItem: MovieItemEntity, newItem: MovieItemEntity): Boolean {
         return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: MovieItemEntity, newItem: MovieItemEntity): Boolean {
         return oldItem == newItem
      }
   }

   val data = AsyncListDiffer(this, CarouselComparator)

   private var onItemClickListener: ((MovieItemEntity) -> Unit)? = null

   fun setOnItemClickListener(listener: (MovieItemEntity) -> Unit) {
      onItemClickListener = listener
   }


}