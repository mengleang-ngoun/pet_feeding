package com.example.pet_feeding.add_device

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.example.pet_feeding.R
import com.example.pet_feeding.model.Wifi
import com.example.pet_feeding.ui.auth.ForgotPasswordFragment

class WifiAdapter(private val wifiList: ArrayList<Wifi>) : RecyclerView.Adapter<WifiViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiViewHolder {
        return WifiViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.wifi_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WifiViewHolder, position: Int) {
        holder.wifiSSID.text = wifiList[position].ssid

        if (wifiList[position].security) {
            holder.wifiSecurity.visibility = View.VISIBLE
        } else {
            holder.wifiSecurity.visibility = View.GONE
        }

        if (wifiList[position].signal > -67 && wifiList[position].signal <= -30) {
            holder.wifiSignal.setBackgroundResource(R.drawable.ic_wifi_very_high_signel)
        } else if (wifiList[position].signal > -70 && wifiList[position].signal <= -67) {
            holder.wifiSignal.setBackgroundResource(R.drawable.ic_wifi_high_signel)
        } else if (wifiList[position].signal > -80 && wifiList[position].signal <= -70) {
            holder.wifiSignal.setBackgroundResource(R.drawable.ic_wifi_medium_signel)
        } else {
            holder.wifiSignal.setBackgroundResource(R.drawable.ic_wifi_low_signel)
        }

        holder.itemView.setOnClickListener { v ->
            val activity = v?.context as AppCompatActivity
            activity.supportFragmentManager.commit {
                replace(
                    R.id.add_device_fragment_container_view,
                    WifiConntectingFragment(wifiList[position])
                )
                addToBackStack("wifi")
            }
        }
    }

    override fun getItemCount(): Int {
        return wifiList.size
    }

}
