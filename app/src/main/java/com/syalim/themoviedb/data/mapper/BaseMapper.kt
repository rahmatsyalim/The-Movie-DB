package com.syalim.themoviedb.data.mapper


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
abstract class BaseMapper<Dto, Result> {
   abstract fun  convert(dto: Dto): Result
}