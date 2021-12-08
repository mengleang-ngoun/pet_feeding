package com.example.pet_feeding.add_device

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.pet_feeding.*
import com.example.pet_feeding.model.Wifi
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONObject
import java.lang.reflect.Method
import java.util.*

class WifiConntectingFragment(private val selectedWifi: Wifi) : Fragment() {
    private lateinit var passwordInput: TextInputLayout
    private lateinit var btnConnectWifi: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var ssidTextView: TextView
    private lateinit var linearProgressIndicator: LinearProgressIndicator
    private var loading: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wifi_conecting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        passwordInput = view.findViewById(R.id.wifi_password)
        btnConnectWifi = view.findViewById(R.id.wifi_connecting)
        btnBack = view.findViewById(R.id.back_btn)
        ssidTextView = view.findViewById(R.id.ssid_textview)
        linearProgressIndicator = view.findViewById(R.id.progress_horizontal)
        ssidTextView.text = selectedWifi.ssid
        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(
                    R.id.add_device_fragment_container_view,
                    ConnectDeviceFragment()
                )
                addToBackStack("wifi")
            }
        }
        passwordInput.editText?.addTextChangedListener {
            passwordInput.isErrorEnabled = false
            passwordInput.error = null
        }
        btnConnectWifi.setOnClickListener {
            loading = true
            btnConnectWifi.isEnabled = false
            linearProgressIndicator.visibility = View.VISIBLE
            var json = JSONObject()
            json.put("ssid_ap", selectedWifi.ssid)
            json.put("pass_ap", passwordInput.editText?.text)
            Log.d("test", "onViewCreated: ${selectedWifi.ssid} ${passwordInput.editText?.text}")
            MySingleton.getInstance(requireContext()).addToRequestQueue(
                JsonObjectRequest(
                    Request.Method.POST, "http://192.168.1.1:80/connect_ap", json,
                    { response ->
                        Log.d("test", "onViewCreated: ${response.getString("status")}")
                    },
                    { error ->
                        Log.d("test", "onViewCreated: $error")
                    }
                )
            )
        }
        var json = JSONObject()
        json.put("uuid", Firebase.auth.currentUser?.uid)
        MySingleton.getInstance(requireContext()).addToRequestQueue(
            JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.1.1:80/addd_user_uuid",
                json,
                {
                        response ->
                    Log.d("test", "run: $response")
                },
                {
                        error ->
                    Log.d("test", "run: $error")
                })
        )
        val timer = Timer()
        val context = requireContext()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                MySingleton.getInstance(requireContext()).addToRequestQueue(
                    JsonObjectRequest(
                        Request.Method.GET, "http://192.168.1.1:80/status_ap", null,
                        { response ->
                            if (loading) {
                                if (response.getInt("status") == 1 || response.getInt("status") == 6 || response.getInt(
                                        "status"
                                    ) == 4
                                ) {
                                    passwordInput.error = "Please check password again"
                                    loading = false
                                    btnConnectWifi.isEnabled = true
                                    linearProgressIndicator.visibility = View.GONE
                                } else if (response.getInt("status") == 3) {
                                    timer.cancel()
                                    startActivity(
                                        Intent(
                                            context,
                                            MainActivity::class.java
                                        )
                                    )
                                    requireActivity().finish()
                                }
                            }
                            Log.d("test", "run: ${response.getInt("status")}")
                        },
                        { error ->
                            Log.d("test", "onViewCreated: $error")
                        }
                    )
                )
            }
        }, 0, 2000)
        super.onViewCreated(view, savedInstanceState)
    }

}

