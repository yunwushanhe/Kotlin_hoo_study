/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study

import android.app.Application
import com.cart.kotlin_hoo_study.db.util.ShoeDataInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Hilt 入口点，也是整个应用的主进程
 * 
 * 1. @HiltAndroidApp 注解标识这是一个 Hilt 应用的入口点
 * 2. Hilt 会自动为这个 Application 生成一个 Hilt 组件
 * 3. 所有使用 @AndroidEntryPoint 和 @HiltViewModel 的类都会依赖这个组件
 * 4. 这样就不需要手动管理 Application 的实例化和依赖注入
 *
 * 5. 当你的 App 启动时，系统首先会创建这个 HooApplication 的实例。
 * 6. 它的生命周期贯穿整个 App 的运行过程（除非进程被系统杀掉）。
 * 7. 它是单例的，整个 App 中只有一个 HooApplication 实例。
 * 8. 这个类里面可以进行很多初始化操作
 *
 * 在 Hilt 中，有两种注入方式：
 * 1. 通过 @Inject 注解 与 构造函数注入：@Inject constructor(@ApplicationContext private val context: Context)，其他非 Context 类型的变量则不用带 @ApplicationContext 注解
 * 2. 通过 @Inject 注解 与 字段注入：@Inject lateinit var context: Context
 * 建议使用 构造函数 注入更简洁、更安全（final 字段、线程安全）。
 *
 */
@HiltAndroidApp
class HooApplication : Application() {

    @Inject // 添加注入
    lateinit var shoeInitializer: ShoeDataInitializer

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        initializeShoes()
    }

    private fun initializeShoes() {
        applicationScope.launch {
            shoeInitializer.initializeShoesIfNeeded()
        }
    }
}
