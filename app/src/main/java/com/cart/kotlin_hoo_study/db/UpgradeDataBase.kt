package com.cart.kotlin_hoo_study.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object UpgradeDataBase {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // 1. 创建 shoe 表
            database.execSQL(
                "CREATE TABLE `shoe` (" +
                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`name` TEXT NOT NULL, " +
                        "`description` TEXT NOT NULL, " +
                        "`price` REAL NOT NULL, " +
                        "`brand` TEXT NOT NULL, " +
                        "`imageUrl` TEXT NOT NULL)"
            )

            // 2. 创建 favourite_shoe 表
            // 修正点 1: 外键引用 'User' 要大写，因为你的 User 类没有指定 @Entity(tableName="user")
            // 修正点 2: 去掉 shoe_id 索引的 UNIQUE 约束，因为你的代码里没加 unique = true
            database.execSQL(
                "CREATE TABLE `favourite_shoe` (" +
                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`user_id` INTEGER NOT NULL, " +
                        "`shoe_id` INTEGER NOT NULL, " +
                        "`create_date` INTEGER NOT NULL, " +
                        "FOREIGN KEY(`user_id`) REFERENCES `User`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION, " + // 注意这里是 User
                        "FOREIGN KEY(`shoe_id`) REFERENCES `shoe`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION)"
            )

            // 3. 创建索引 (注意：这里不要加 UNIQUE)
            database.execSQL("CREATE INDEX `index_favourite_shoe_user_id` ON `favourite_shoe` (`user_id`)")
            database.execSQL("CREATE INDEX `index_favourite_shoe_shoe_id` ON `favourite_shoe` (`shoe_id`)")
        }
    }
}