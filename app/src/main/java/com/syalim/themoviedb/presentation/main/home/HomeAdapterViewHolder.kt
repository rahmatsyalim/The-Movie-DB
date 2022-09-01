package com.syalim.themoviedb.presentation.main.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.syalim.themoviedb.R
import com.syalim.themoviedb.core.common.Constants.MOVIES_IN_THEATER
import com.syalim.themoviedb.core.common.Constants.MOVIES_POPULAR
import com.syalim.themoviedb.core.common.Constants.MOVIES_TOP_RATED
import com.syalim.themoviedb.core.common.Constants.MOVIES_UPCOMING
import com.syalim.themoviedb.databinding.ItemMovieCarouselBinding
import com.syalim.themoviedb.databinding.ItemMovieListBinding
import com.syalim.themoviedb.presentation.utils.CoilImageLoader
import com.syalim.themoviedb.presentation.utils.NestedViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs


/**
 * Created by Rahmat Syalim on 2022/08/21
 * rahmatsyalim@gmail.com
 */


sealed class HomeAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
   class CarouselViewHolder(
      val binding: ItemMovieCarouselBinding,
      coilImageLoader: CoilImageLoader,
      private val onItemNavigation: ((Int, Bundle) -> Unit)? = null,
      private val onViewMoreClicked: ((Int, Bundle) -> Unit)? = null,
      private var carouselPosition: (ViewPager2.() -> Unit)? = null,
      private val coroutineScope: CoroutineScope?
   ) : HomeAdapterViewHolder(binding.root) {

      private val carouselAdapter = HomeCarouselAdapter(coilImageLoader)

      fun onBind(item: com.syalim.themoviedb.domain.movie.model.Movies) {
         binding.apply {
            tvTitle.text = itemView.context.getTitle(item.category)
            btnMore.setOnClickListener {
               // TODO: navigate
            }
            viewPager.apply {
               offscreenPageLimit = 2
               getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
               adapter = carouselAdapter.apply {
                  onItemClickListener = {
                     onItemNavigation?.invoke(
                        R.id.action_main_fragment_to_movie_detail_fragment,
                        Bundle().apply { putString("id", it.id.toString()) }
                     )
                  }
               }
               setPageTransformer(
                  CompositePageTransformer().apply {
                     addTransformer(MarginPageTransformer(24))
                     addTransformer { page, position ->
                        val r = 1 - abs(position)
                        page.scaleY = 0.85f + r * 0.14f
                     }
                  }
               )
            }
            carouselAdapter.submitData(item.movie)
            viewPager.apply {
               carouselPosition?.invoke(this)
               setInTheaterCarouselJob()
               TabLayoutMediator(binding.tabLayout, this) { _, _ -> }.attach()
            }
         }
      }

      private var inTheaterCarouselJob: Job? = null
      private fun ViewPager2.setInTheaterCarouselJob() {
         inTheaterCarouselJob?.cancel()
         inTheaterCarouselJob = coroutineScope?.launch {
            setInTheaterCarousel()
         }
      }

      private suspend fun ViewPager2.setInTheaterCarousel() {
         delay(3000L)
         val itemSize = adapter?.itemCount ?: 0
         val lastItem = if (itemSize > 0) itemSize - 1 else 0
         val nextItem = if (currentItem == lastItem) 0 else currentItem + 1
         setCurrentItem(nextItem, true)
         setInTheaterCarousel()
      }
   }

   class MoviesViewHolder(
      val binding: ItemMovieListBinding,
      coilImageLoader: CoilImageLoader,
      private val onItemNavigation: ((Int, Bundle) -> Unit)? = null,
   ) : HomeAdapterViewHolder(binding.root),
      NestedViewHolder {

      private val moviesAdapter = HomeMoviesAdapter(coilImageLoader)

      private val layoutManager = LinearLayoutManager(
         itemView.context, LinearLayoutManager.HORIZONTAL, false
      )

      private lateinit var item: com.syalim.themoviedb.domain.movie.model.Movies

      fun onBind(item: com.syalim.themoviedb.domain.movie.model.Movies) {
         this.item = item
         binding.apply {
            tvTitle.text = itemView.context.getTitle(item.category)
            btnMore.setOnClickListener {
               // TODO: navigate
            }
            recyclerView.apply {
               layoutManager = this@MoviesViewHolder.layoutManager
               adapter = moviesAdapter.apply {
                  onItemClickListener = {
                     onItemNavigation?.invoke(
                        R.id.action_main_fragment_to_movie_detail_fragment,
                        Bundle().apply { putString("id", it.id.toString()) }
                     )
                  }
               }
            }
         }
         moviesAdapter.submitData(item.movie)
      }

      override fun getId(): String = item.id.toString()

      override fun getLayoutManager(): RecyclerView.LayoutManager? = binding.recyclerView.layoutManager
   }

   protected fun Context.getTitle(category: String): String {
      return when (category) {
         MOVIES_IN_THEATER -> getString(R.string.in_theater)
         MOVIES_POPULAR -> getString(R.string.popular)
         MOVIES_TOP_RATED -> getString(R.string.top_rated)
         MOVIES_UPCOMING -> getString(R.string.upcoming)
         else -> "Unknown"
      }
   }
}