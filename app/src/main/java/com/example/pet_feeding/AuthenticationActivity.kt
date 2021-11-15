package com.example.pet_feeding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.pet_feeding.ui.auth.SignInFragment
import com.example.pet_feeding.ui.auth.SignUpFragment

class AuthenticationActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showFragment(SignInFragment())
        setContentView(R.layout.activity_authentication)
    }
    private fun showFragment(fragment: Fragment){
        supportFragmentManager.commit {
            replace(R.id.authentication_fragment_container_view,fragment)
            addToBackStack(null)
        }
    }
}