/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.db.repository

import androidx.lifecycle.LiveData
import com.cart.kotlin_hoo_study.db.dao.UserDao
import com.cart.kotlin_hoo_study.db.data.User
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 用户仓库类
 * 
 * 使用 @Inject 和 @Singleton 注解，让 Hilt 管理该类的实例化
 * 
 * 1. 为什么要用 @Inject 构造函数？
 *    - 标识该类的构造函数可以由 Hilt 自动调用
 *    - UserRepository 的依赖（UserDao）会由 Hilt 自动注入
 *    
 * 2. 为什么要移除 private constructor 和 getInstance？
 *    - 原来使用单例模式是为了避免重复创建实例
 *    - 现在交给 Hilt 管理：@Singleton 保证全局只有一个实例
 *    - 更重要的是：Hilt 需要能够创建 UserRepository 实例才能注入到 ViewModel
 *    - private constructor 会阻止 Hilt 创建实例
 *    
 * 3. @Singleton 的作用：
 *    - 保证该实例在应用生命周期内只有一个
 *    - 类似于原来的单例效果，但由 Hilt 控制
 */
@Singleton
class UserRepository @Inject constructor(private val userDao: UserDao) {

    fun login(account: String, pwd: String): LiveData<User> {
        return userDao.query(account, pwd)
    }

    suspend fun register(email: String, account: String, pwd: String): Long {
        return withContext(IO) {
            userDao.insert(User(email, account, pwd, "https://raw.githubusercontent.com/mCyp/Photo/master/1560651318109.jpeg"))
        }
    }

    suspend fun updateUser(user: User) {
        withContext(IO) {
            userDao.update(user)
        }
    }

    fun findUserById(id: Long): LiveData<User> {
        return userDao.findUserById(id)
    }
}
