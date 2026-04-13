/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.databinding.ObservableField
import com.cart.kotlin_hoo_study.common.BaseConstant
import com.cart.kotlin_hoo_study.ui.activity.MainActivity

class LoginModel constructor(name: String, pwd: String, context: Context) {
    val n = ObservableField<String>(name)
    val p = ObservableField<String>(pwd)
    var context: Context = context

    /**
     * 用户名改变回调的函数
     */
    fun onNameChanged(s: CharSequence) {
        n.set(s.toString())
    }

    /**
     * 密码改变的回调函数
     */
    fun onPwdChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        p.set(s.toString())
    }

    fun login() {
        if (n.get().equals(BaseConstant.USER_NAME)
            && p.get().equals(BaseConstant.USER_PWD)
        ) {
            Toast.makeText(context, "账号密码正确", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}