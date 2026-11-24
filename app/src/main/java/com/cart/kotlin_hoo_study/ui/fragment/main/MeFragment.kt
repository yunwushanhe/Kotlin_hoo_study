package com.cart.kotlin_hoo_study.ui.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.cart.kotlin_hoo_study.R
import com.cart.kotlin_hoo_study.common.UserPreferences
import com.cart.kotlin_hoo_study.databinding.MeFragmentBinding
import com.cart.kotlin_hoo_study.viewmodel.MeModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUser()
    }

    private fun observeUser() {
        lifecycleScope.launch {
            val userId = userPreferences.userId.first()
            if (userId == -1L) {
                Snackbar.make(requireView(), "请先登录", Snackbar.LENGTH_SHORT).show()
                return@launch
            }
            //meVM.user.value = meVM.getUserById(userId).value
            // 这么写是行不通的，是因为room的异步处理，meVM.user.value会被赋值为null
            meVM.getUserById(userId).observe(viewLifecycleOwner) { user ->
                meVM.user.value = user
            }
        }
    }
}