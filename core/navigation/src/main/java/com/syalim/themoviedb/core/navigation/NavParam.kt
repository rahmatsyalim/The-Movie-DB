package com.syalim.themoviedb.core.navigation

import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.Navigator


/**
 * Created by Rahmat Syalim on 2022/09/11
 * rahmatsyalim@gmail.com
 */

data class NavParam(
   val destination: AppDestination,
   val args: Bundle? = null,
   val navOptions: NavOptions? = null,
   val navExtras: Navigator.Extras? = null
)
