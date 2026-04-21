package com.cart.kotlin_hoo_study.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cart.kotlin_hoo_study.R
import com.cart.kotlin_hoo_study.common.BaseConstant
import com.cart.kotlin_hoo_study.databinding.LoginFragmentBinding
import com.cart.kotlin_hoo_study.databinding.RegisterFragmentBinding
import com.cart.kotlin_hoo_study.ui.activity.MainActivity
import com.cart.kotlin_hoo_study.viewmodel.LoginModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val loginModel: LoginModel by viewModels()
    private lateinit var binding: LoginFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater
            , R.layout.login_fragment
            , container
            , false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(binding)
        onSubscribeUi(binding)
    }

    private fun initData(binding: LoginFragmentBinding) {
        /**
         * 为什么 etAccount.setText(it) 有时不行？
         * 手动操作与 DataBinding 冲突：当 DataBinding 已经绑定了 android:text="@{model.account}" 时，手动调用 setText() 可能会被 DataBinding 的更新机制覆盖或忽略
         * 绑定时机问题：手动操作发生在 DataBinding 初始化完成之后，可能产生竞态条件
         * 最佳实践：在使用 DataBinding 时，应避免手动操作已绑定的 View，统一通过 ViewModel 管理数据状态，让 DataBinding 自动处理 UI 更新。
         */
        arguments?.getString(BaseConstant.KEY_REGISTER_ACCOUNT)?.let {
            //etAccount.setText(it)
            loginModel.account.value = it
        }

        binding.lifecycleOwner = this
        binding.model = loginModel
        binding.activity = activity
    }

    private fun onSubscribeUi(binding: LoginFragmentBinding) {
        binding.btnLogin.setOnClickListener {
            loginModel.login()
        }
        observeLoginResult()
    }

    private fun observeLoginResult() {
        loginModel.loginResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is LoginModel.LoginResult.Success -> {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(context, "登录成功！欢迎 ${result.user.account}", Toast.LENGTH_SHORT).show()
                }
                is LoginModel.LoginResult.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
                is LoginModel.LoginResult.Loading -> {
                    loginModel.enable.value = false
                }
            }
            if (result !is LoginModel.LoginResult.Loading) {
                loginModel.enable.value = true
            }
        }
    }
}