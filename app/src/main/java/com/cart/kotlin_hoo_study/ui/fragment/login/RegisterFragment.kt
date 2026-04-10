package com.cart.kotlin_hoo_study.ui.fragment.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cart.kotlin_hoo_study.R

class RegisterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    lateinit var tvCancel: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etEmail: EditText = view.findViewById(R.id.et_email)
        val safeArgs:RegisterFragmentArgs by navArgs()
        val email = safeArgs.email
        etEmail.setText(email)

        tvCancel = view.findViewById(R.id.tv_cancel)
        tvCancel.setOnClickListener {
            findNavController().navigate(R.id.welcomeFragment)
        }
    }
}