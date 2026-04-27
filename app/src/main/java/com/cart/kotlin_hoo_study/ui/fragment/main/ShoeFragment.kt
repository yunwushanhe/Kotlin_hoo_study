package com.cart.kotlin_hoo_study.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cart.kotlin_hoo_study.R
import com.cart.kotlin_hoo_study.ui.adapter.ShoeAdapter
import com.cart.kotlin_hoo_study.viewmodel.ShoeModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var _binding: View? = null
    private val binding get() = _binding!!

    private val shoeModel: ShoeModel by viewModels()
    private lateinit var shoeAdapter: ShoeAdapter
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.shoe_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout = view.findViewById(R.id.refreshLayout)
        recyclerView = view.findViewById(R.id.recyclerView)
        refreshLayout.setOnRefreshListener(this)
        setupRecyclerView()
        observeData()
    }

    private fun setupRecyclerView() {
        shoeAdapter = ShoeAdapter()
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = shoeAdapter
        }
    }

    private fun observeData() {
        shoeModel.allShoes.observe(viewLifecycleOwner) { shoes ->
            shoes?.let {
                shoeAdapter.submitList(it)
            }
        }
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}