package com.syalim.themoviedb.core.ui

import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs


/**
 * Created by Rahmat Syalim on 2022/09/07
 * rahmatsyalim@gmail.com
 */

object CustomCarousel {

   private var sliderJob: Job? = null

   fun setup(
      viewPager2: ViewPager2,
      coroutineScope: CoroutineScope,
      currentPosition: Int,
      onSavePosition: Int.() -> Unit
   ) {
      viewPager2.setupViewPager(currentPosition)
      viewPager2.onPageChanged(onSavePosition)
      sliderJob?.cancel()
      sliderJob = coroutineScope.launch {
         viewPager2.sliderJob()
      }
   }

   private fun ViewPager2.setupViewPager(currentPosition: Int) {
      setCurrentItem(currentPosition, false)
      offscreenPageLimit = 2
      setPageTransformer(
         CompositePageTransformer().apply {
            addTransformer { page, position ->
               val r = 1 - abs(position)
               page.scaleY = 0.85f + r * 0.15f
            }
         }
      )
   }

   private fun ViewPager2.onPageChanged(saveCurrentPosition: Int.() -> Unit) {
      registerOnPageChangeCallback(
         object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
               super.onPageSelected(position)
               saveCurrentPosition.invoke(position)
            }
         }
      )
   }

   private suspend fun ViewPager2.sliderJob() {
      delay(5000L)
      val itemSize = adapter?.itemCount ?: 0
      val lastItem = if (itemSize > 0) itemSize - 1 else 0
      val nextItem = if (currentItem == lastItem) 0 else currentItem + 1
      setCurrentItem(nextItem, true)
      sliderJob()
   }
}