package com.cart.kotlin_hoo_study.ui.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.cart.kotlin_hoo_study.R
import com.cart.kotlin_hoo_study.common.UserPreferences
import com.cart.kotlin_hoo_study.databinding.MeFragmentBinding
import com.cart.kotlin_hoo_study.viewmodel.MeModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MeFragment : Fragment() {

    private val meVM: MeModel by viewModels()
    private lateinit var binding: MeFragmentBinding
    @Inject
    lateinit var userPreferences: UserPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MeFragmentBinding.inflate(inflater, container, false)
        binding.model = meVM
        binding.lifecycleOwner = viewLifecycleOwner
        //binding.lifecycleOwner = this
        return binding.root
    }
}