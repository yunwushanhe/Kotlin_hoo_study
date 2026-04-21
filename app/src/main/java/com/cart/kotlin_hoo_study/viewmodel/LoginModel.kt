/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cart.kotlin_hoo_study.common.BaseConstant
import com.cart.kotlin_hoo_study.db.data.User
import com.cart.kotlin_hoo_study.db.repository.UserRepository
import com.cart.kotlin_hoo_study.ui.activity.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class LoginModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val account = MutableLiveData<String>("")
    val pwd = MutableLiveData<String>("")
    val enable = MutableLiveData<Boolean>(false)

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    sealed class LoginResult {
        data class Success(val user: User) : LoginResult()
        data class Error(val message: String) : LoginResult()
        object Loading : LoginResult()
    }

    fun onAccountChanged(s: CharSequence) {
        account.value = s.toString()
        judgeEnable()
    }

    fun onPwdChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        pwd.value = s.toString()
        judgeEnable()
    }

    fun judgeEnable() {
        enable.value = account.value!!.isNotEmpty() && pwd.value!!.isNotEmpty()
    }

    fun login() {
        val accountStr = account.value ?: ""
        val pwdStr = pwd.value ?: ""

        if (accountStr.isEmpty() || pwdStr.isEmpty()) {
            _loginResult.value = LoginResult.Error("账号或密码不能为空")
            return
        }

        _loginResult.value = LoginResult.Loading

        userRepository.login(accountStr, pwdStr).observeForever { user ->
            Log.d("ady", "login: ${user.toString()}")
            if (user != null) {
                _loginResult.value = LoginResult.Success(user)
            } else {
                _loginResult.value = LoginResult.Error("账号或密码错误")
            }
        }
    }
}