package com.cart.kotlin_hoo_study.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.cart.kotlin_hoo_study.R
import com.cart.kotlin_hoo_study.databinding.LoginFragmentBinding
import com.cart.kotlin_hoo_study.ui.activity.MainActivity
import com.cart.kotlin_hoo_study.viewmodel.LoginModel

class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*val binding: LoginFragmentBinding =  LoginFragmentBinding.inflate(
            inflater
            , container
            , false
        )*/
        // 上面这种方式也可以，选其一就好
        val binding: LoginFragmentBinding = DataBindingUtil.inflate(
            inflater
            , R.layout.login_fragment
            , container
            , false
        )

        val loginModel = LoginModel("", "", requireContext())
        binding.model = loginModel
        binding.activity = activity

        return binding.root
        /*return inflater.inflate(R.layout.login_fragment, container, false)*/
    }

    lateinit var tvCancel: TextView
    lateinit var btnLogin: Button
    lateinit var etAccount: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvCancel = view.findViewById(R.id.tv_cancel)
        btnLogin = view.findViewById(R.id.btn_login)
        etAccount = view.findViewById(R.id.et_account)

        arguments?.getString("testKey")?.let {
            etAccount.setText(it)
        }

        /*tvCancel.setOnClickListener {
            findNavController().navigate(R.id.welcomeFragment)
        }

        btnLogin.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(context, "登录成功！", Toast.LENGTH_SHORT).show()
        }*/
    }
}