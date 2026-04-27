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

/**
 * Shoe数据类，
 * 只有在构造函数中的变量才会被 toString 函数打印出来，直接在内部定义的不可以
 * 不在构造函数中定义的变量是只有基本的 set、get 函数。
 */
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