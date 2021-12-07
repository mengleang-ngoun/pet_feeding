package com.example.pet_feeding.add_device

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pet_feeding.R

class WifiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    public var wifiSSID: TextView = itemView.findViewById(R.id.wifi_ssid)
    public var wifiSecurity: ImageView = itemView.findViewById(R.id.wifi_security)
    public var wifiSignal: ImageView = itemView.findViewById(R.id.wifi_signel)
}