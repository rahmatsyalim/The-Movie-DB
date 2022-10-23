package com.syalim.themoviedb.core.ui


/**
 * Created by Rahmat Syalim on 2022/09/13
 * rahmatsyalim@gmail.com
 */

object SharedUiUpdater {
   var onHomeToolbarColorChange: ((Boolean) -> Unit)? = null
   var onFabDiscoverIconChange: ((Int) -> Unit)? = null
}