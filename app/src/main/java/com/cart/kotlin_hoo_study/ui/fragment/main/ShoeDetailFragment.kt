package com.cart.kotlin_hoo_study.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.cart.kotlin_hoo_study.R
import com.cart.kotlin_hoo_study.databinding.ShoeDetailFragmentBinding
import com.cart.kotlin_hoo_study.viewmodel.ShoeDetailModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoeDetailFragment : Fragment() {

    private var _binding: ShoeDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: ShoeDetailFragmentArgs by navArgs()
    private val viewModel: ShoeDetailModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShoeDetailFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shoeId = args.shoeId
        observeShoe(shoeId)
        setupListeners()
    }

    private fun observeShoe(shoeId: Long) {
        viewModel.getShoeById(shoeId).observe(viewLifecycleOwner) { shoe ->
            shoe?.let {
                binding.shoe = it
                loadShoeImage(it.imageUrl)
                binding.tvPrice.text = String.format("$%.2f", it.price)
            }
        }
    }

    private fun loadShoeImage(imageUrl: String) {
        if (imageUrl.isNotEmpty()) {
            Glide.with(requireContext())
                .load(imageUrl)
                .placeholder(R.drawable.glide_placeholder)
                .fitCenter()
                .into(binding.ivShoe)
        }
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.fbFavourite.setOnClickListener {
            showUnderDevelopmentDialog()
        }
    }

    private fun showUnderDevelopmentDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("提示")
            .setMessage("功能正在开发中，敬请期待！")
            .setPositiveButton("确定", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}