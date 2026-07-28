/**
 * Copyright (c) 2025 - CARThunder
 * All Rights Reserved by Chengdu CARThunder Intelligent Technology Co., LTD and its affiliates.
 * You may not use, copy, distribute, modify, transmit in any form this file
 * except in compliance with CARThunder in writing by applicable law.
 */
package com.cart.kotlin_hoo_study.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.cart.kotlin_hoo_study.R
import com.cart.kotlin_hoo_study.common.UserPreferences
import com.cart.kotlin_hoo_study.ui.adapter.FavouriteAdapter
import com.cart.kotlin_hoo_study.viewmodel.FavouriteModel
import com.cart.kotlin_hoo_study.widget.EmptyView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private val favouriteVM: FavouriteModel by viewModels()

    @Inject
    lateinit var userPreferences: UserPreferences

    private lateinit var emptyView: EmptyView
    private lateinit var recyclerView: RecyclerView
    private lateinit var favouriteAdapter: FavouriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.favourite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyView = view.findViewById(R.id.empty)
        recyclerView = view.findViewById(R.id.recycler)

        emptyView.bind(arrayOf(recyclerView))
        emptyView.triggerLoading()

        setupRecyclerView()
        observeFavourites()
    }

    private fun setupRecyclerView() {
        favouriteAdapter = FavouriteAdapter { shoe ->
            val action = FavouriteFragmentDirections
                .actionFavouriteFragmentToShoeDetailFragment(shoe.id)
            findNavController().navigate(action)
        }
        recyclerView.adapter = favouriteAdapter
    }

    private fun observeFavourites() {
        viewLifecycleOwner.lifecycleScope.launch {
            val userId = userPreferences.userId.first()
            if (userId == -1L) {
                emptyView.triggerEmpty()
                return@launch
            }
            favouriteVM.findFavouriteShoesByUserId(userId)
                .observe(viewLifecycleOwner) { shoes ->
                    favouriteAdapter.submitList(shoes)
                    emptyView.triggerOkOrEmpty(!shoes.isNullOrEmpty())
                }
        }
    }
}
