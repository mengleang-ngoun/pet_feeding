package com.example.pet_feeding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pet_feeding.fragment.HomeFragment
import com.example.pet_feeding.fragment.ProfileFragment
import com.example.pet_feeding.fragment.SettingFragment
import com.example.pet_feeding.fragment.schedule_feednow
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val profileFragment = ProfileFragment()
    private val settingFragment = SettingFragment()




    override fun onCreate(savedInstanceState: Bundle?) {
        if (Firebase.auth.currentUser == null){
            startActivity(Intent(this,AuthenticationActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_main)
        replaceFragment(profileFragment)
        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottom_navigation.setOnNavigationItemSelectedListener{
            when (it.itemId){
                R.id.navigation_home -> replaceFragment(homeFragment)
                R.id.navigation_profile -> replaceFragment(profileFragment)
                R.id.navigation_setting -> replaceFragment(settingFragment)
            }
            true
        }
        super.onCreate(savedInstanceState)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,fragment)
            commit()
        }
    }
}


