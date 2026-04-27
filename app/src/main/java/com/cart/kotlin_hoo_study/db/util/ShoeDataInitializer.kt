package com.cart.kotlin_hoo_study.db.util

import android.content.Context
import com.cart.kotlin_hoo_study.db.data.Shoe
import com.cart.kotlin_hoo_study.db.repository.ShoeRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 放在构造函数是最佳实践，如非必要不要单独写成一行变量进行注入
 * @ApplicationContext        @ActivityContext          @ServiceContext
 * (App-wide Context)         (Current Activity)        (Service Context)
 * @ApplicationContext 代表是主进程的 context
 * @ActivityContext 代表是activity 的context
 * @ServiceContext 代表是service 的context
 * 三者的生命周期不一样，存在的时间点也不一样
 *
 * 只要类中有任何 @Inject 字段，构造函数就必须加 @Inject constructor()。
 */
@Singleton  // 添加 Hilt 注解
class ShoeDataInitializer @Inject constructor() {
    @Inject
    @ApplicationContext
    lateinit var context: Context
    @Inject
    lateinit var shoeRepository: ShoeRepository
    /*
    * 也可以以下面的形式放到构造函数里面，使用的时候就不用考虑传入的 shoeRepository 对象
    * @Inject constructor(
    *   @ApplicationContext private val context: Context,
    *   private val shoeRepository: ShoeRepository
    * )
    * */

    private val gson = Gson()

    suspend fun initializeShoesIfNeeded() {
        withContext(Dispatchers.IO) {
            val count = shoeRepository.getShoeCount()
            if (count == 0) {
                val shoesJson = loadShoesFromAssets()
                val shoes = gson.fromJson<List<Shoe>>(
                    shoesJson,
                    object : TypeToken<List<Shoe>>() {}.type
                )
                shoes.forEach { shoe -> shoe.id = 0 } // 将id重置为0，不然插入数据库会因为id相同而被覆盖
                repeat(5) {
                    shoeRepository.insertShoes(shoes)
                }
            }
        }
    }

    private fun loadShoesFromAssets(): String {
        return try {
            context.assets.open("shoes.json").bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            "[]"
        }
    }
}
