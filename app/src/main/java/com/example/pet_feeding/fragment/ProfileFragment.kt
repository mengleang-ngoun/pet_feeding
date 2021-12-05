package com.example.pet_feeding.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.pet_feeding.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.firestore.DocumentReference


class ProfileFragment : Fragment() {

    private lateinit var email:String
    private lateinit var name:String
    private lateinit var uid:String
    private lateinit var documentReference:DocumentReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileUsername = view.findViewById<TextView>(R.id.profile_username)
        val profileEmail = view.findViewById<TextView>(R.id.profile_email)

        Firebase.auth.currentUser?.let {
            email =  Firebase.auth.currentUser!!.email.toString()
            uid   =  Firebase.auth.currentUser!!.uid.toString()
        }
        documentReference = FirebaseFirestore.getInstance().collection("users").document(uid)
        documentReference.addSnapshotListener { snapshot, error ->
            if(error != null){
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()){
                profileUsername.text = snapshot.getString("username").toString()
            }
        }
        profileEmail.text = email

    }
}





