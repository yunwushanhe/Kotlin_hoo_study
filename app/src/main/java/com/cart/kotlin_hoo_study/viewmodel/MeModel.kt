/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cart.kotlin_hoo_study.db.data.User
import com.cart.kotlin_hoo_study.db.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MeModel @Inject constructor (
    private val userRepository: UserRepository
): ViewModel() {
    val user = MutableLiveData<User>()

    fun getUserById(userId: Long): LiveData<User> {
        return userRepository.findUserById(userId)
    }
}