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
import androidx.room.TypeConverters
import com.cart.kotlin_hoo_study.db.dao.FavouriteShoeDao
import com.cart.kotlin_hoo_study.db.dao.ShoeDao
import com.cart.kotlin_hoo_study.db.dao.UserDao
import com.cart.kotlin_hoo_study.db.data.FavouriteShoe
import com.cart.kotlin_hoo_study.db.data.Shoe
import com.cart.kotlin_hoo_study.db.data.User

@Database(
    entities = [User::class, Shoe::class, FavouriteShoe::class],
    version = 2,
    exportSchema = false
) // 每次新增改变表，都需要版本+1
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun shoeDao(): ShoeDao

    abstract fun favouriteShoeDao(): FavouriteShoeDao

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
                //.addMigrations(UpgradeDataBase.MIGRATION_1_2) // 数据库变动，迁移元数据进行升级的方式
                .fallbackToDestructiveMigration() // 注释上面这一行，使用这一行，则是丢弃数据升级
                .build()
        }
    }
}