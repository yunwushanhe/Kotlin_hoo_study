/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cart.kotlin_hoo_study.R
import com.cart.kotlin_hoo_study.databinding.ItemShoeBinding
import com.cart.kotlin_hoo_study.db.data.Shoe

class ShoeAdapter(
    private val onItemClick: ((Shoe) -> Unit)? = null
) : ListAdapter<Shoe, ShoeAdapter.ViewHolder>(ShoeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemShoeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shoe = getItem(position)
        holder.bind(shoe)
    }

    inner class ViewHolder(
        private val binding: ItemShoeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Shoe) {
            binding.apply {
                tvName.text = item.name
                Glide.with(ivShoe.context)
                    .asBitmap()
                    .load(item.imageUrl)
                    .placeholder(R.drawable.glide_placeholder)
                    .centerCrop()
                    .into(ivShoe)
                root.setOnClickListener {
                    onItemClick?.invoke(item)
                }
            }
        }
    }

    class ShoeDiffCallback : DiffUtil.ItemCallback<Shoe>() {
        override fun areItemsTheSame(oldItem: Shoe, newItem: Shoe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Shoe, newItem: Shoe): Boolean {
            return oldItem == newItem
        }
    }
}