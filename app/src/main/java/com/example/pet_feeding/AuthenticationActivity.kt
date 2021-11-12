package com.example.pet_feeding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pet_feeding.ui.auth.SignInFragment
import com.example.pet_feeding.ui.auth.SignUpFragment

class AuthenticationActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showFragment(SignUpFragment())
        showFragment(SignInFragment())
        setContentView(R.layout.activity_authentication)
    }
    private fun showFragment(fragment: Fragment){
       supportFragmentManager.beginTransaction().apply {
           replace(R.id.authentication_fragment_container_view,fragment)
           addToBackStack(null)
           commit()
       }
    }
}