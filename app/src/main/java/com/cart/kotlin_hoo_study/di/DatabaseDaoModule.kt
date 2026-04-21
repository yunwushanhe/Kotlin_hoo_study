/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.di

import android.content.Context
import com.cart.kotlin_hoo_study.db.AppDataBase
import com.cart.kotlin_hoo_study.db.dao.FavouriteShoeDao
import com.cart.kotlin_hoo_study.db.dao.ShoeDao
import com.cart.kotlin_hoo_study.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt 模块 - 提供数据库相关的依赖
 * 
 * 1. 为什么要用 @Module 和 @InstallIn？
 *    - @Module 告诉 Hilt 这是一个提供依赖的模块
 *    - @InstallIn(SingletonComponent::class) 表示这个模块在全局单例范围内生效
 *    - 所有使用 @AndroidEntryPoint 的组件都可以访问这里提供的依赖
 *    
 * 2. @Provides 的作用：
 *    - 用于标记提供依赖的方法
 *    - 当其他类需要注入 UserRepository、UserDao 等依赖时，Hilt 会自动调用这些方法
 *    - 类似工厂方法，但由 Hilt 自动管理
 *    
 * 3. @Singleton：
 *    - 保证依赖在应用生命周期内只有一个实例
 *    - 避免重复创建，节省资源
 *    
 * 4. 为什么要单独创建模块？
 *    - UserRepository 的构造函数需要 UserDao
 *    - UserDao 来自于 Room 数据库，需要通过 @Provides 方式提供（因为 Room 不是用 @Inject 构造的）
 *    - 这种方式叫做"模块绑定"，将外部库或无法直接使用 @Inject 的类绑定到 Hilt 系统中
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseDaoModule {

    /**
     * 提供 UserDao 实例
     * 
     * 1. 这里使用 @ApplicationContext 获取 Application Context
     * 2. 由于 AppDataBase 使用了单例模式，我们直接调用 getInstance
     * 3. @Singleton 保证整个应用只有一个 UserDao 实例
     */
    @Provides
    @Singleton
    fun provideUserDao(@ApplicationContext context: Context): UserDao {
        return AppDataBase.getInstance(context).userDao()
    }

    @Provides
    @Singleton
    fun provideShoeDao(@ApplicationContext context: Context): ShoeDao {
        return AppDataBase.getInstance(context).shoeDao()
    }

    @Provides
    @Singleton
    fun provideFavouriteShoeDao(@ApplicationContext context: Context): FavouriteShoeDao {
        return AppDataBase.getInstance(context).favouriteShoeDao()
    }
}
