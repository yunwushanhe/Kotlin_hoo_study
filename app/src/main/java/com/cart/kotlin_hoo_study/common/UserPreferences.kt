/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// 在 Context 上扩展一个 DataStore 实例（顶层属性，全局唯一）
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

/**
 * UserPreferences 通过 @Inject constructor 接收 @ApplicationContext，Hilt 可以直接注入，
 * 不需要在 Module 中额外写 @Provides。
 */
@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val KEY_USER_ID = longPreferencesKey("user_id")
        private val KEY_USER_ACCOUNT = stringPreferencesKey("user_account")
    }

    /** 保存用户登录信息 */
    suspend fun saveLoginUser(userId: Long, account: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_USER_ID] = userId
            prefs[KEY_USER_ACCOUNT] = account
        }
    }

    /** 获取当前登录用户 ID（Flow，自动响应变化） */
    val userId: Flow<Long> = context.dataStore.data.map { prefs ->
        prefs[KEY_USER_ID] ?: -1L
    }

    /** 获取当前登录用户账号（Flow） */
    val userAccount: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_USER_ACCOUNT] ?: ""
    }

    /** 清除用户信息（退出登录时调用） */
    suspend fun clearUser() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}