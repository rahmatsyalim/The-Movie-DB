package com.syalim.themoviedb.core.common

import androidx.annotation.StringRes


/**
 * Created by Rahmat Syalim on 2022/08/12
 * rahmatsyalim@gmail.com
 */

enum class ConnectivityStatus(@StringRes val message: Int?) {
   Available(null),
   Unavailable(R.string.no_internet_connection),
   Lost(R.string.no_internet_connection);
}