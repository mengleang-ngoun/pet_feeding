package com.example.pet_feeding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.pet_feeding.add_device.TellToConnectToDeviceWifi
import com.example.pet_feeding.ui.auth.SignInFragment

class AddFeedingDeviceActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.commit {
            replace(R.id.add_device_fragment_container_view, TellToConnectToDeviceWifi())
        }
        setContentView(R.layout.activity_add_feeding_device)
    }
}