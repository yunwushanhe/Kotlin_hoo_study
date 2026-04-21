/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cart.kotlin_hoo_study.R
import com.cart.kotlin_hoo_study.common.BaseConstant
import com.cart.kotlin_hoo_study.databinding.RegisterFragmentBinding
import com.cart.kotlin_hoo_study.viewmodel.RegisterModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 注册Fragment
 * 
 * 使用 @AndroidEntryPoint 注解标识，让 Hilt 管理该 Fragment 的依赖注入
 * 
 * 1. 为什么要用 @AndroidEntryPoint？
 *    - 标识这是一个需要注入依赖的 Android 组件（Activity、Fragment、Service、View等）
 *    - Hilt 会为该类生成一个 EntryPoint，内部包含获取依赖的方法
 *    - 只有添加了该注解，Fragment 才能使用 @Inject 字段和 viewModels() 委托
 *    
 * 2. viewModels() 的作用：
 *    - 这是 Hilt 提供的 ViewModel 获取方式
 *    - 自动获取带有 @HiltViewModel 注解的 ViewModel
 *    - 无需像传统方式那样手动创建 ViewModelProvider.Factory
 *    - 底层会自动获取 Hilt 生成的 ViewModelFactory
 *    
 * 3. SafeArgs 的使用：
 *    - navArgs() 可以获取 Navigation 传递的参数
 *    - email 参数来自上一个 Fragment 传递的邮箱地址
 *    
 * 4. 绑定数据：
 *    - binding.model = registerModel：将 ViewModel 绑定到布局的 data 区域
 *    - binding.lifecycleOwner = this：设置生命周期拥有者，确保数据变化能更新 UI
 *    - binding.activity = activity：将 Activity 引用传递给布局，用于调用 onBackPressed() 等方法
 */
@AndroidEntryPoint
class RegisterFragment : Fragment() {

    /**
     * 使用 viewModels() 委托获取 RegisterModel 实例
     * 
     * 1. 这里的 viewModels() 来自 androidx.fragment.app.viewModels
     * 2. 不同于传统的 ViewModelProvider，这里不需要传递 Factory
     * 3. Hilt 会自动从其生成的 ViewModelFactory 中创建 RegisterModel
     * 4. RegisterModel 构造函数的 UserRepository 参数会由 Hilt 自动注入
     */
    private val registerModel: RegisterModel by viewModels()

    private lateinit var binding: RegisterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.register_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(binding)
        onSubscribeUi(binding)
    }

    /**
     * 初始化数据
     * 将 ViewModel 和 Activity 绑定到布局
     */
    private fun initData(binding: RegisterFragmentBinding) {
        // SafeArgs 的使用：获取从上一个 Fragment 传递的邮箱参数
        val safeArgs: RegisterFragmentArgs by navArgs()
        val email = safeArgs.email

        // 设置生命周期拥有者，确保 LiveData 变化能更新 UI（必须要有这一行，不然数据变动不会反应到UI上面）
        binding.lifecycleOwner = this
        
        // 绑定 ViewModel 到布局的 data 区域
        binding.registerModel = registerModel
        // 将邮箱设置到 ViewModel
        binding.registerModel?.email?.value = email
        
        // 绑定 Activity 到布局，用于调用 Activity 的方法（如 onBackPressed）
        binding.activity = activity
    }

    /**
     * 订阅 UI 事件
     * 处理按钮点击等交互
     */
    private fun onSubscribeUi(binding: RegisterFragmentBinding) {
        // 注册按钮点击事件
        binding.btnRegister.setOnClickListener {
            // 执行注册
            registerModel.register()
            
            // 注册成功后，跳转到登录页面
            val bundle = Bundle()
            bundle.putString(BaseConstant.KEY_REGISTER_ACCOUNT, registerModel.account.value)
            findNavController().navigate(R.id.loginFragment, bundle)
        }
    }
}
