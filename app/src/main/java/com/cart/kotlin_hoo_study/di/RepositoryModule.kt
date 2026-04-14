package com.cart.kotlin_hoo_study.di

import com.cart.kotlin_hoo_study.db.dao.UserDao
import com.cart.kotlin_hoo_study.db.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
}