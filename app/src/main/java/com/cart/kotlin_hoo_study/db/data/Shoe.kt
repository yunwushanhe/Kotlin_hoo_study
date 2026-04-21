/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.db.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoe")
data class Shoe(
    @ColumnInfo(name = "name") val name: String, // 可以是 val 类型
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "price") var price: Float,
    @ColumnInfo(name = "brand") var brand: String,
    @ColumnInfo(name = "imageUrl") var imageUrl: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0 // 这个id是不能被设置成 val 类型
}