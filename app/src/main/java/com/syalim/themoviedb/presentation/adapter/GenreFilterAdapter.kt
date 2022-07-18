package com.syalim.themoviedb.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.R
import com.syalim.themoviedb.databinding.ItemFilterBinding
import com.syalim.themoviedb.domain.model.GenreItemEntity


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class GenreFilterAdapter(private val genre: List<String>? = listOf()) : RecyclerView.Adapter<GenreFilterAdapter.GenreFilterViewHolder>() {

   inner class GenreFilterViewHolder(private val binding: ItemFilterBinding) :
      RecyclerView.ViewHolder(binding.root) {

         fun bind(item: GenreItemEntity){
            Log.d("genre_picked", genre.toString())
            binding.ctGenre.apply {
               text = item.name

               genre?.let {
                  if (it.contains(item.id.toString())){
                     isChecked = true
                     setBackgroundColor(resources.getColor(R.color.primary))
                  }
               }

               setOnClickListener {
                  if (isChecked){
                     isChecked = false
                     setBackgroundColor(resources.getColor(R.color.transparent))
                  } else {
                     isChecked = true
                     setBackgroundColor(resources.getColor(R.color.primary))
                  }
                  onItemClickListener?.invoke(item,isChecked)
               }
            }
         }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreFilterViewHolder {
      return GenreFilterViewHolder(
         ItemFilterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
      )
   }

   override fun onBindViewHolder(holder: GenreFilterViewHolder, position: Int) {
      holder.bind(data.currentList[position])
   }

   override fun getItemCount(): Int {
      return data.currentList.size
   }

   object GenreComparator : DiffUtil.ItemCallback<GenreItemEntity>() {
      override fun areItemsTheSame(oldItem: GenreItemEntity, newItem: GenreItemEntity): Boolean {
         return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: GenreItemEntity, newItem: GenreItemEntity): Boolean {
         return oldItem == newItem
      }
   }

   val data = AsyncListDiffer(this, GenreComparator)

   private var onItemClickListener: ((GenreItemEntity,Boolean) -> Unit)? = null

   fun setOnItemClickListener(listener: (GenreItemEntity,Boolean) -> Unit) {
      onItemClickListener = listener
   }
}