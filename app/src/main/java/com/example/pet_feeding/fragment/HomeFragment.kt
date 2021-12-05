package com.example.pet_feeding.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.commit
import com.example.pet_feeding.AddFeedingDeviceActivity
import com.example.pet_feeding.MainActivity
import com.example.pet_feeding.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {
    private lateinit var documentReference: DocumentReference
    private lateinit var uid:String
    private lateinit var addDeviceBtn:MaterialButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addDeviceBtn = view.findViewById<MaterialButton>(R.id.bt_add_device)
        val username  = view.findViewById<TextView>(R.id.tx_title)
        addDeviceBtn.setOnClickListener {
            startActivity(Intent(requireContext(), AddFeedingDeviceActivity::class.java))
            requireActivity().finish()
        }
        Firebase.auth.currentUser?.let {
            uid   =  Firebase.auth.currentUser!!.uid.toString()
        }

        documentReference = FirebaseFirestore.getInstance().collection("users").document(uid)
        documentReference.addSnapshotListener { snapshot, error ->
            if(error != null){
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()){
                username.text ="Hi " + snapshot.getString("username").toString()
            }
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.commit{
            replace(R.id.fragment_container,fragment)
        }

        }

}