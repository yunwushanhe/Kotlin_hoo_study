/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.db.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Calendar

/**
 * FavouriteShoe 数据类，
 * 只有在构造函数中的变量才会被 toString 函数打印出来，直接在内部定义的不可以
 * 不在构造函数中定义的变量是只有基本的 set、get 函数，例如这里的 id 变量
 */
@Entity(
    tableName = "favourite_shoe",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user_id"]),
        ForeignKey(entity = Shoe::class, parentColumns = ["id"], childColumns = ["shoe_id"])
    ],
    indices = [
        Index("user_id"),
        Index("shoe_id")
    ]
)
data class FavouriteShoe(
    @ColumnInfo(name = "user_id") val userId: Long, // 外键，可以是 val 类型
    @ColumnInfo(name = "shoe_id") var shoeId: Long, // 外键
    @ColumnInfo(name = "create_date") var createDate: Calendar
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0  // 这个id是不能被设置成 val 类型
}