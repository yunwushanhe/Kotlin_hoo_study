/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.db.repository

import com.cart.kotlin_hoo_study.db.dao.FavouriteShoeDao
import com.cart.kotlin_hoo_study.db.data.FavouriteShoe
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteShoeRepository @Inject constructor(private val favouriteShoeDao: FavouriteShoeDao) {

    suspend fun insertFavouriteShoe(favouriteShoe: FavouriteShoe) {
        withContext(IO) {
            favouriteShoeDao.insertFavouriteShoe(favouriteShoe)
        }
    }
}