/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cart.kotlin_hoo_study.db.data.Shoe
import com.cart.kotlin_hoo_study.db.repository.ShoeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShoeDetailModel @Inject constructor(
    private val shoeRepository: ShoeRepository
): ViewModel() {

    fun getShoeById(id: Long): LiveData<Shoe> {
        return shoeRepository.findShoeById(id)
    }
}