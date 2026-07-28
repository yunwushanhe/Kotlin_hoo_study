/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cart.kotlin_hoo_study.db.data.FavouriteShoe
import com.cart.kotlin_hoo_study.db.data.Shoe
import com.cart.kotlin_hoo_study.db.repository.FavouriteShoeRepository
import com.cart.kotlin_hoo_study.db.repository.ShoeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavouriteModel @Inject constructor(
    private val favouriteShoeRepository: FavouriteShoeRepository,
    private val shoeRepository: ShoeRepository
): ViewModel() {

    suspend fun insertFavouriteShoe(favouriteShoe: FavouriteShoe) {
        favouriteShoeRepository.insertFavouriteShoe(favouriteShoe)
    }

    fun findFavouriteShoeByUserIdAndShoeId(userId: Long, shoeId: Long): LiveData<FavouriteShoe> {
        return favouriteShoeRepository.findFavouriteShoeByUserIdAndShoeId(userId, shoeId)
    }

    suspend fun findFavouriteShoeByUserIdAndShoeIdSync(userId: Long, shoeId: Long): FavouriteShoe? {
        return favouriteShoeRepository.findFavouriteShoeByUserIdAndShoeIdSync(userId, shoeId)
    }

    fun findFavouriteShoesByUserId(userId: Long): LiveData<List<Shoe>> {
        return shoeRepository.findFavouriteShoesByUserId(userId);
    }

    fun deleteFavouriteShoeByUserIdAndShoeId(userId: Long, shoeId: Long) {
        viewModelScope.launch {
            favouriteShoeRepository.deleteFavouriteShoeByUserIdAndShoeId(userId, shoeId)
        }
    }

}