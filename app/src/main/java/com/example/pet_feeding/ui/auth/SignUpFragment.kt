package com.example.pet_feeding.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.pet_feeding.R

class SignUpFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val signInBtn = view.findViewById<TextView>(R.id.sign_in_nav)
        signInBtn.setOnClickListener {
            showFragment(SignInFragment())
        }
    }
    private fun showFragment(fragment: Fragment){
        requireActivity().supportFragmentManager.commit {
            replace(R.id.authentication_fragment_container_view,fragment)
        }
    }
}