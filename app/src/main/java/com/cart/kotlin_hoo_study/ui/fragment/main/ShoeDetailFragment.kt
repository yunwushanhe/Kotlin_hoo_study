package com.cart.kotlin_hoo_study.ui.fragment.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.cart.kotlin_hoo_study.R
import com.cart.kotlin_hoo_study.databinding.ShoeDetailFragmentBinding
import com.cart.kotlin_hoo_study.db.data.FavouriteShoe
import com.cart.kotlin_hoo_study.viewmodel.FavouriteModel
import com.cart.kotlin_hoo_study.viewmodel.ShoeDetailModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import com.cart.kotlin_hoo_study.common.UserPreferences
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class ShoeDetailFragment : Fragment() {

    private var _binding: ShoeDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: ShoeDetailFragmentArgs by navArgs()
    private val shoeDetailVM: ShoeDetailModel by viewModels()
    private val favouriteVM: FavouriteModel by viewModels()
    @Inject
    lateinit var userPreferences: UserPreferences

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
        setupListeners(view)
        initFavouriteIcon(shoeId)
    }

    private fun observeShoe(shoeId: Long) {
        shoeDetailVM.getShoeById(shoeId).observe(viewLifecycleOwner) { shoe ->
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

    private fun setupListeners(view: View) {
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.fbFavourite.setOnClickListener {
            val shoeId = binding.shoe?.id ?: return@setOnClickListener
            lifecycleScope.launch {
                val userId = userPreferences.userId.first()
                if (userId == -1L) {
                    Snackbar.make(view, "请先登录", Snackbar.LENGTH_SHORT).show()
                    return@launch
                }

                // 用 suspend 函数替代 LiveData.value，真正等待数据库查询结果，换成 findFavouriteShoeByUserIdAndShoeId 函数就是会出问题。
                val existing = favouriteVM.findFavouriteShoeByUserIdAndShoeIdSync(userId, shoeId)
                Log.d("Favourite", "existing = $existing")
                if (existing != null) {
                    Log.d("Favourite", "存在")
                    favouriteVM.deleteFavouriteShoeByUserIdAndShoeId(userId, shoeId)
                    Snackbar.make(view, "取消收藏成功！", Snackbar.LENGTH_SHORT).show()
                } else {
                    Log.d("Favourite", "不存在")
                    favouriteVM.insertFavouriteShoe(FavouriteShoe(userId, shoeId, Calendar.getInstance()))
                    Snackbar.make(view, "收藏成功！", Snackbar.LENGTH_SHORT).show()
                }
                // 图标由 initFavouriteIcon 的 observer 自动响应数据库变化并更新。每次相关表数据变了，Room 都会再通知这个 Observer执行。
                // initFavouriteIcon → 注册 LiveData observer（观察 Room 的查询），当Room 检测到 favourite_shoe 表变化 → 自动重新执行 SQL 查询。
                // Room 检测到 favourite_shoe 表变化 → 自动重新执行 SQL 查询，也就是说只要 favourite_shoe 表发生变化了，
                // findFavouriteShoeByUserIdAndShoeId 这个函数就会被重新执行一次，因为在 initFavouriteIcon 函数里面对这个函数返回的 LiveData 数据进行了观察。
                // Room 会解析 SQL 知道这个查询依赖 favourite_shoe 表。只有 favourite_shoe 表（不是其他表）发生变化时，这个查询才会重新执行。
                // 没有 .observe(...)，Room 不会重新执行查询
                // viewLifecycleOwner 在 onStart～onStop 期间是活跃的，此时表变化才会触发重新查询
                // 如果 Fragment 不可见（比如被压后台），Room 不会浪费资源去重新查询
            }
        }
    }

    private fun initFavouriteIcon(shoeId: Long) {
        Log.d("Favourite", "initFavouriteIcon called, shoeId=$shoeId")
        lifecycleScope.launch {
            val userId = userPreferences.userId.first()
            Log.d("Favourite", "userId from DataStore = $userId")
            if (userId == -1L) {
                Log.w("Favourite", "userId is -1, returning early")
                return@launch
            }
            Log.d("Favourite", "Setting up LiveData observer...")
            favouriteVM.findFavouriteShoeByUserIdAndShoeId(userId, shoeId)
                .observe(viewLifecycleOwner) { favourite ->
                    Log.d("Favourite", "Observer fired, favourite=$favourite")
                    val iconRes = if (favourite != null) {
                        R.drawable.detail_ic_favorite_red
                    } else {
                        R.drawable.detail_ic_favorite_white
                    }
                    binding.fbFavourite.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), iconRes)
                    )
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showUnderDevelopmentDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("提示")
            .setMessage("功能正在开发中，敬请期待！")
            .setPositiveButton("确定", null)
            .show()
    }
}