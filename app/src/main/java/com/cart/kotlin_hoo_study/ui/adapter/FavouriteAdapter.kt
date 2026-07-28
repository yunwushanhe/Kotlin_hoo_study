/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cart.kotlin_hoo_study.databinding.ItemFavouriteBinding
import com.cart.kotlin_hoo_study.db.data.Shoe

class FavouriteAdapter(
    private val onItemClick: (Shoe) -> Unit
) : ListAdapter<Shoe, FavouriteAdapter.ViewHolder>(FavouriteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFavouriteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemFavouriteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Shoe) {
            binding.shoe = item
            binding.listener = View.OnClickListener {
                onItemClick(item)
            }
            binding.executePendingBindings()
        }
    }

    class FavouriteDiffCallback : DiffUtil.ItemCallback<Shoe>() {
        override fun areItemsTheSame(oldItem: Shoe, newItem: Shoe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Shoe, newItem: Shoe): Boolean {
            return oldItem == newItem
        }
    }
}
