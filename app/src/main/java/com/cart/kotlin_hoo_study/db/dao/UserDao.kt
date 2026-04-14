/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cart.kotlin_hoo_study.db.data.User

@Dao
interface UserDao {
    @Query("select * from user where account = :account and pwd = :pwd")
    fun query(account: String, pwd: String): LiveData<User>

    @Query("select * from user where id = :id")
    fun findUserById(id: Long): LiveData<User>

    @Insert
    fun insert(user: User): Long

    @Delete()
    fun delete(user: User)

    @Update
    fun update(user: User)


}