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
import com.cart.kotlin_hoo_study.db.data.Shoe
import com.cart.kotlin_hoo_study.db.repository.ShoeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoeModel @Inject constructor(
    private val shoeRepository: ShoeRepository
) : ViewModel() {

    val allShoes: LiveData<List<Shoe>> = shoeRepository.getAllShoe()

    fun getShoesByBrand(brand: Array<String>): LiveData<List<Shoe>> {
        return shoeRepository.findShoeByBrand(brand)
    }

    fun getShoesNotInBrand(brand: Array<String>): LiveData<List<Shoe>> {
        return shoeRepository.findShoesNotInBrand(brand)
    }

    fun refreshShoes() {
        viewModelScope.launch {
            shoeRepository.getAllShoeSync()
        }
    }
}