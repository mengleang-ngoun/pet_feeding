package com.example.pet_feeding.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.pet_feeding.R
import kotlin.math.log

class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val signInNav = view.findViewById<FrameLayout>(R.id.sign_in_nav)
        val signUpBtn = view.findViewById<ImageButton>(R.id.sign_up_btn)
        signInNav.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.authentication_fragment_container_view, SignInFragment())
                addToBackStack("auth")
            }
        }
        signUpBtn.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.authentication_fragment_container_view, EmailVerificationFragment())
            }
        }
    }
}