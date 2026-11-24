/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.ui.databinding

import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import com.cart.kotlin_hoo_study.R

/**
 * DataBinding 自定义属性。
 * Kotlin 中的 @BindingAdapter 需要模块启用 kotlin-kapt，
 * 才能被 DataBinding 编译器发现并生成对应 BindingImpl。
 */
object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageFromUrl")
    fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
        if (imageUrl.isNullOrEmpty()) {
            return
        }
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(R.drawable.glide_placeholder)
            .centerCrop()
            .into(view)
    }

    // 加载带圆角的头像
    @JvmStatic
    @BindingAdapter("imageTransFromUrl")
    fun bindImageTransFromUrl(view: ImageView, imageUrl: String?){
        if(!imageUrl.isNullOrEmpty()){
            Glide.with(view.context)
                .load(imageUrl)
                .apply(bitmapTransform(RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL)))
                .into(view)
        }
    }

    // 文本监听器
    /*@BindingAdapter("addTextChangedListener")
    fun addTextChangedListener(editText: EditText, simpleWatcher: SimpleWatcher) {
        editText.addTextChangedListener(simpleWatcher)
    }*/
}
