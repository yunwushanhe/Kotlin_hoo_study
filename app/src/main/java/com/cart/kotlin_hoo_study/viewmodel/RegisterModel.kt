/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cart.kotlin_hoo_study.db.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 注册功能的 ViewModel
 * 
 * 使用 @HiltViewModel 注解标识，让 Hilt 自动管理该 ViewModel 的实例化
 * 
 * 1. 为什么要用 Hilt？
 *    - 传统方式：需要手动创建 ViewModelFactory 来传递依赖（如 UserRepository）
 *    - Hilt 方式：只需要在构造函数中使用 @Inject 标记需要注入的参数，Hilt 自动创建实例并注入依赖
 *    
 * 2. @HiltViewModel 的作用：
 *    - 标识该类是一个可以使用 Hilt 注入的 ViewModel
 *    - Hilt 会为该类生成一个 Factory，替代手动创建的 ViewModelProvider.Factory
 *    - 在 Fragment/Activity 中可以直接使用 viewModels() 委托获取实例，无需手动传递 Factory
 *    
 * 3. 依赖注入的好处：
 *    - 解耦：ViewModel 不需要知道依赖是如何创建的
 *    - 易于测试：可以通过 Hilt 提供 mock 的依赖进行单元测试
 *    - 代码简洁：不需要编写繁琐的 Factory 类
 */
@HiltViewModel
class RegisterModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    // 邮箱
    val email = MutableLiveData("")

    // 用账号
    val account = MutableLiveData("")
    
    // 密码  
    val pwd = MutableLiveData("")
    
    // 注册按钮是否可用
    val enable = MutableLiveData(false)

    /**
     * 邮箱改变回调
     * @param s 文本变化后的字符序列
     */
    fun onEmailChanged(s: CharSequence) {
        email.value = s.toString()
        judgeEnable()
    }

    /**
     * 用账号改变回调
     * @param s 文本变化后的字符序列
     */
    fun onAccountChanged(s: CharSequence) {
        account.value = s.toString()
        judgeEnable()
    }

    /**
     * 密码改变回调
     * @param s 文本变化后的字符序列
     */
    fun onPwdChanged(s: CharSequence) {
        pwd.value = s.toString()
        judgeEnable()
    }

    /**
     * 判断所有输入是否有效，控制注册按钮状态
     */
    private fun judgeEnable() {
        enable.value = pwd.value!!.isNotEmpty()
                && account.value!!.isNotEmpty()
                && email.value!!.isNotEmpty()
    }

    /**
     * 执行注册操作
     * 使用 viewModelScope.launch 在协程中执行数据库操作
     */
    fun register() {
        viewModelScope.launch {
            repository.register(email.value!!, account.value!!, pwd.value!!)
        }
    }
}
