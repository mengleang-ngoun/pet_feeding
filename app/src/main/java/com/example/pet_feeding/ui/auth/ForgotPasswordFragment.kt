package com.example.pet_feeding.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.pet_feeding.R

class ForgotPasswordFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forgot_password,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val signUpNav = view.findViewById<FrameLayout>(R.id.sign_up_nav)
        signUpNav.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.authentication_fragment_container_view,SignInFragment())
                addToBackStack("auth")
            }
        }
    }
}