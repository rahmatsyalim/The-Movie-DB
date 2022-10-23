package com.syalim.themoviedb.core.navigation

import androidx.annotation.IdRes


/**
 * Created by Rahmat Syalim on 2022/09/03
 * rahmatsyalim@gmail.com
 */

object AppNavigator {

   @IdRes
   var navHostId: Int? = null

   private var navigation: (NavParam.() -> Unit)? = null

   fun navigateTo(navParam: NavParam) {
      navigation?.invoke(navParam)
   }

   fun onNavigation(block: NavParam.() -> Unit) {
      navigation = block
   }

}