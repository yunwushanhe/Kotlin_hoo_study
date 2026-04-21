/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.db.repository

import androidx.lifecycle.LiveData
import com.cart.kotlin_hoo_study.db.dao.ShoeDao
import com.cart.kotlin_hoo_study.db.data.Shoe
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoeRepository @Inject constructor(private val shoeDao: ShoeDao) {

    suspend fun insertShoes(shoes: List<Shoe>) {
        withContext(IO) {
            shoeDao.insertShoes(shoes)
        }
    }

    fun getAllShoe(): LiveData<List<Shoe>> {
        return shoeDao.getAllShoe()
    }

    fun findShoeById(id: Long): LiveData<Shoe> {
        return shoeDao.findShoeById(id)
    }

    fun findShoeByBrand(brand: Array<String>): LiveData<List<Shoe>> {
        return shoeDao.findShoeByBrand(brand)
    }

    fun findFavouriteShoesByUserId(userId: Long): LiveData<List<Shoe>> {
        return shoeDao.findFavouriteShoesByUserId(userId)
    }
}