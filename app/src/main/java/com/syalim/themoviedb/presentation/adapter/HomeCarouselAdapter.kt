package com.syalim.themoviedb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.syalim.themoviedb.common.setImage
import com.syalim.themoviedb.common.setImageUrl
import com.syalim.themoviedb.databinding.ItemCarouselBinding
import com.syalim.themoviedb.domain.model.MovieItemEntity


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class HomeCarouselAdapter(private val viewPager: ViewPager2) :
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
            item.backdropPath?.let {
               ivBackdrop.setImage(it.setImageUrl())
               tvTitle.text = item.originalTitle
            }
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

//   val data: ArrayList<MovieItemEntity> = arrayListOf()

   private var onItemClickListener: ((MovieItemEntity) -> Unit)? = null

   fun setOnItemClickListener(listener: (MovieItemEntity) -> Unit) {
      onItemClickListener = listener
   }

//   private val runnable = Runnable {
//      data.addAll(data)
//      notifyDataSetChanged()
//   }


}