package com.example.pet_feeding.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.commit
import com.example.pet_feeding.AuthenticationActivity
import com.example.pet_feeding.R
import com.example.pet_feeding.ui.auth.SignInFragment
import com.google.firebase.auth.FirebaseAuth


class SettingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val signIn = SignInFragment()
        val btLogout = view.findViewById<Button>(R.id.bt_logout)
        val btDevice = view.findViewById<Button>(R.id.bt_device)
        btDevice.setOnClickListener{
            replaceFragment(ScheduleFeed())

        }

        btLogout.setOnClickListener{
            signOut();
            startActivity(Intent(requireContext(), AuthenticationActivity::class.java))
            requireActivity().finish()
        }

    }

    private fun signOut(){
        FirebaseAuth.getInstance().signOut()
    }


    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.commit{
            replace(R.id.fragment_container,fragment)
            addToBackStack("schedule")
        }

    }



}

