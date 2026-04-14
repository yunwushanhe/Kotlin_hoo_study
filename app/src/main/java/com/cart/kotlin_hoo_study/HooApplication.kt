/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Hilt 入口点
 * 
 * 1. @HiltAndroidApp 注解标识这是一个 Hilt 应用的入口点
 * 2. Hilt 会自动为这个 Application 生成一个 Hilt 组件
 * 3. 所有使用 @AndroidEntryPoint 和 @HiltViewModel 的类都会依赖这个组件
 * 4. 这样就不需要手动管理 Application 的实例化和依赖注入
 */
@HiltAndroidApp
class HooApplication : Application()
