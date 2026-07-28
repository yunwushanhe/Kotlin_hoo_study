package com.cart.kotlin_hoo_study.di

import com.cart.kotlin_hoo_study.db.dao.FavouriteShoeDao
import com.cart.kotlin_hoo_study.db.dao.ShoeDao
import com.cart.kotlin_hoo_study.db.dao.UserDao
import com.cart.kotlin_hoo_study.db.repository.FavouriteShoeRepository
import com.cart.kotlin_hoo_study.db.repository.ShoeRepository
import com.cart.kotlin_hoo_study.db.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 提供 Repository 相关对象的实例，但实际上不需要也可以，
 * 因为所有的 Repository 对象都加上了Hilt框架的 @Singleton 注解，直接声明对象变量并加上 @Inject 注解延迟初始化就可以完成注入
 * 以 ShoeRepository 的实例注入为例
 * 这里写出，是为了让依赖注入对象的管理，体现出可以分类分模块进行管理
 *
 * Dao 类是 Room 自动生成的，不能用 @Inject，必须用 @Module + @Provides
 * 因为 Room 的 DAO 是接口，由 Hilt 通过 AppDataBase.getInstance(context).xxxDao() 提供
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * 提供 UserRepository 实例
     *
     * 1. UserRepository 的构造函数需要 UserDao 参数
     * 2. Hilt 会自动注入 provideUserDao 返回的 UserDao 实例
     * 3. 这样就不需要手动调用 UserRepository.getInstance(userDao)
     * 4. 自动完成依赖注入（Dependency Injection）
     *
     * @param userDao 由 Hilt 自动注入的 UserDao 实例
     * @return UserRepository 实例
     */
    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }

    /*@Provides
    @Singleton
    fun provideShoeRepository(shoeDao: ShoeDao): ShoeRepository {
        return ShoeRepository(shoeDao)
    }*/

    @Provides
    @Singleton
    fun provideFavouriteShoeRepository(favouriteShoeDao: FavouriteShoeDao): FavouriteShoeRepository {
        return FavouriteShoeRepository(favouriteShoeDao)
    }
}