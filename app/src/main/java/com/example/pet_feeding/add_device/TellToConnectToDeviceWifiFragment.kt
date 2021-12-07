package com.example.pet_feeding.add_device

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.pet_feeding.R
import com.example.pet_feeding.ui.auth.ForgotPasswordFragment
import com.google.android.material.button.MaterialButton

class TellToConnectToDeviceWifiFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tell_to_connect_to_device_wifi,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<MaterialButton>(R.id.btn).setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.add_device_fragment_container_view, ConnectDeviceFragment())
            }
        }
        view.findViewById<TextView>(R.id.instruction_text).text = Html.fromHtml("Please change your phone wifi connection to <b>Automatic Feeding Device</b>")
    }
}