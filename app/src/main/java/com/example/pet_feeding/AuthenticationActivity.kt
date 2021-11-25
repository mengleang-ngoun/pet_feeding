package com.example.pet_feeding

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.pet_feeding.ui.auth.EmailVerificationFragment
import com.example.pet_feeding.ui.auth.SignInFragment
import com.example.pet_feeding.ui.auth.SignUpFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.commit {
            replace(R.id.authentication_fragment_container_view,SignInFragment())
        }
        setContentView(R.layout.activity_authentication)
    }
}