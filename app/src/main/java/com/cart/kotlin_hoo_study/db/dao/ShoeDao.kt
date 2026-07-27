/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cart.kotlin_hoo_study.db.data.Shoe

@Dao
interface ShoeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoes(shoes: List<Shoe>)

    @Query("select * from shoe")
    fun getAllShoe(): LiveData<List<Shoe>>

    @Query("select * from shoe")
    suspend fun getAllShoeSync(): List<Shoe>

    @Query("select count(*) from shoe")
    suspend fun getShoeCount(): Int

    @Query("select * from shoe where id = :id")
    fun findShoeById(id: Long): LiveData<Shoe>

    @Query("select * from shoe where brand in (:brand)")
    fun findShoeByBrand(brand: Array<String>): LiveData<List<Shoe>>

    @Query("SELECT * FROM shoe WHERE brand NOT IN (:brand)")
    fun findShoesNotInBrand(brand: Array<String>): LiveData<List<Shoe>>

    @Query(
        "select shoe.* from shoe " +
        "inner join favourite_shoe on favourite_shoe.shoe_id = shoe.id " +
        "where favourite_shoe.user_id = :userId"
    )
    fun findFavouriteShoesByUserId(userId: Long): LiveData<List<Shoe>>
}