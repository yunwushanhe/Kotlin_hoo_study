/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cart.kotlin_hoo_study.db.dao.UserDao
import com.cart.kotlin_hoo_study.db.data.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile // 保证多线程可见，避免线程缓存导致的问题
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDataBase(context: Context): AppDataBase {
            return Room
                .databaseBuilder(context, AppDataBase::class.java, "study-database")
                .build()
        }
    }
}