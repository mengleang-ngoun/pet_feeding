package com.example.pet_feeding.add_device

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.pet_feeding.MySingleton
import com.example.pet_feeding.R
import com.example.pet_feeding.model.Wifi
import com.google.android.material.progressindicator.LinearProgressIndicator
import org.json.JSONArray

class ConnectDeviceFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var reload_wifi: ImageView
    private lateinit var wifiArray: ArrayList<Wifi>
    private lateinit var loading_bar: LinearProgressIndicator
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_wifi_list, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.wifi_list)
        reload_wifi = view.findViewById(R.id.reload_wifi)
        loading_bar = view.findViewById(R.id.progress_horizontal)
        wifiArray = ArrayList<Wifi>();
        recyclerView.adapter = WifiAdapter(wifiArray)
        requestScanAp(wifiArray)
        reload_wifi.setOnClickListener {
            requestScanAp(wifiArray)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun requestScanAp(apList: ArrayList<Wifi>) {
        apList.clear()
        loading_bar.visibility = View.VISIBLE
        reload_wifi.visibility = View.GONE
        MySingleton.getInstance(requireContext()).addToRequestQueue(
            JsonArrayRequest(Request.Method.GET, "http://192.168.1.1:80/scan_ap", null,
                { response ->
                    for (i in 0 until response.length()) {
                        apList.add(
                            Wifi(
                                response.getJSONObject(i).getString("ap_ssid"),
                                response.getJSONObject(i).getInt("ap_signal"),
                                response.getJSONObject(i).getBoolean("ap_security")
                            )
                        )
                    }
                    apList.sortWith(compareBy { it.signal })
                    recyclerView.adapter?.notifyDataSetChanged()
                    loading_bar.visibility = View.GONE
                    reload_wifi.visibility = View.VISIBLE
                },
                { error ->
                    Log.d("test", "onViewCreated: $error")
                    loading_bar.visibility = View.GONE
                    reload_wifi.visibility = View.VISIBLE
                }
            )
        )
    }
}