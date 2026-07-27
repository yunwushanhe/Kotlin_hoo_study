package com.cart.kotlin_hoo_study.ui.fragment.main

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.navigation.fragment.findNavController
import com.cart.kotlin_hoo_study.R
import com.cart.kotlin_hoo_study.common.BaseConstant
import com.cart.kotlin_hoo_study.ui.adapter.ShoeAdapter
import com.cart.kotlin_hoo_study.viewmodel.ShoeModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val shoeModel: ShoeModel by viewModels()
    private lateinit var shoeAdapter: ShoeAdapter
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    // 下面一堆变量用于控制鞋字的品牌图片的动画效果
    private lateinit var mShoe: FloatingActionButton
    private lateinit var mNike: FloatingActionButton
    private lateinit var mAdi: FloatingActionButton
    private lateinit var mOther: FloatingActionButton
    private lateinit var nikeGroup: Group
    private lateinit var adiGroup: Group
    private lateinit var otherGroup: Group
    private var animatorSet: AnimatorSet? = null
    private var radius: Int = 0
    private var width: Int = 0

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

        // 初始化FAB
        mShoe = view.findViewById(R.id.fab_shoe)
        mNike = view.findViewById(R.id.fab_nike)
        mAdi = view.findViewById(R.id.fab_adidas)
        mOther = view.findViewById(R.id.fab_other)
        // 初始化Group
        nikeGroup = view.findViewById(R.id.gp_like)
        adiGroup = view.findViewById(R.id.gp_write)
        otherGroup = view.findViewById(R.id.gp_top)

        // 初始隐藏品牌按钮
        setViewVisible(false)
        // 设置点击事件
        mShoe.setOnClickListener {
            shoeAnimation()
        }
        mNike.setOnClickListener {
            // 动画进行时忽略点击
            if (animatorSet?.isRunning == false) {
                Snackbar.make(view, "Nike", Snackbar.LENGTH_SHORT).show()
                // 获取 LiveData
                val nikeShoesLiveData = shoeModel.getShoesByBrand(arrayOf(BaseConstant.SHOE_NAME_NIKE))
                // 观察这个 LiveData，当数据准备好时再更新 Adapter
                nikeShoesLiveData.observe(viewLifecycleOwner) { shoes ->
                    shoes?.let {
                        shoeAdapter.submitList(it)
                    }
                }
                shoeAnimation() // 收起动画
            }
        }
        mAdi.setOnClickListener {
            // 动画进行时忽略点击
            if (animatorSet?.isRunning == false) {
                Snackbar.make(view, "Adidas", Snackbar.LENGTH_SHORT).show()
                // 获取 LiveData
                val adidasShoesLiveData = shoeModel.getShoesByBrand(arrayOf(BaseConstant.SHOE_NAME_ADIDAS))
                // 观察这个 LiveData，当数据准备好时再更新 Adapter
                adidasShoesLiveData.observe(viewLifecycleOwner) { shoes ->
                    shoes?.let {
                        shoeAdapter.submitList(it)
                    }
                }
                shoeAnimation()
            }
        }
        // 这个按钮现在的执行逻辑就是获取所有的鞋子进行展示
        mOther.setOnClickListener {
            // 动画进行时忽略点击
            if (animatorSet?.isRunning == false) {
                Snackbar.make(view, "Other", Snackbar.LENGTH_SHORT).show()
                // 获取 LiveData
                val otherShoesLiveData = shoeModel.getShoesNotInBrand(arrayOf(BaseConstant.SHOE_NAME_OTHER))
                // 观察这个 LiveData，当数据准备好时再更新 Adapter
                otherShoesLiveData.observe(viewLifecycleOwner) { shoes ->
                    shoes?.let {
                        shoeAdapter.submitList(it)
                    }
                }
                shoeAnimation()
            }
        }
        // 获取FAB尺寸
        mShoe.post {
            width = mShoe.measuredWidth
        }
        radius = resources.getDimensionPixelSize(R.dimen.fab_radius) // 需要在dimens.xml定义80dp

        setupRecyclerView()
        observeData()
    }

    private fun shoeAnimation() {
        // 播放动画的时候不可以点击
        if (animatorSet != null && animatorSet!!.isRunning)
            return
        // 判断播放显示还是隐藏动画
        if (nikeGroup.visibility != View.VISIBLE) { // 触发显示图标动画逻辑
            animatorSet = AnimatorSet()
            val likeAnimator = getValueAnimator(mNike, false, nikeGroup, 0)
            val writeAnimator = getValueAnimator(mAdi, false, adiGroup, 45)
            val topAnimator = getValueAnimator(mOther, false, otherGroup, 90)
            animatorSet!!.playSequentially(likeAnimator, writeAnimator, topAnimator)
            animatorSet!!.start()
        } else { // 触发隐藏图标动画逻辑
            animatorSet = AnimatorSet()
            val likeAnimator = getValueAnimator(mNike, true, nikeGroup, 0)
            val writeAnimator = getValueAnimator(mAdi, true, adiGroup, 45)
            val topAnimator = getValueAnimator(mOther, true, otherGroup, 90)
            animatorSet!!.playSequentially(topAnimator, writeAnimator, likeAnimator)
            animatorSet!!.start()
        }
    }

    private fun getValueAnimator(
        button: FloatingActionButton,
        reverse: Boolean,
        group: Group,
        angle: Int
    ): ValueAnimator {
        val animator: ValueAnimator
        if (reverse)
            animator = ValueAnimator.ofFloat(1f, 0f)
        else
            animator = ValueAnimator.ofFloat(0f, 1f)

        animator.addUpdateListener { animation ->
            val v = animation.animatedValue as Float
            val params = button.layoutParams as ConstraintLayout.LayoutParams
            params.circleRadius = (radius.toFloat() * v).toInt()
            params.circleAngle = 270f + angle * v
            params.width = (width.toFloat() * v).toInt()
            params.height = (width.toFloat() * v).toInt()
            button.layoutParams = params
        }

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                group.visibility = View.VISIBLE
            }
            override fun onAnimationEnd(animation: Animator) {
                if (group === nikeGroup && reverse) {
                    setViewVisible(false)
                }
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

        animator.duration = 300
        animator.interpolator = DecelerateInterpolator()
        return animator
    }

    private fun setViewVisible(isShow: Boolean) {
        nikeGroup.visibility = if (isShow) View.VISIBLE else View.GONE
        adiGroup.visibility = if (isShow) View.VISIBLE else View.GONE
        otherGroup.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        shoeAdapter = ShoeAdapter { shoe ->
            val action = ShoeFragmentDirections.actionShoeFragmentToShoeDetailFragment(shoe.id)
            findNavController().navigate(action)
        }
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
}