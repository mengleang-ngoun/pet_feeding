package com.example.pet_feeding.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.pet_feeding.R
import com.example.pet_feeding.model.ScheduleModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class Weight : Fragment() {
    private lateinit var db: DatabaseReference
    private lateinit var petWeight: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_weight,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Firebase.database.getReference("PetWeight")
        petWeight = view.findViewById(R.id.pet_weight)

//    db.addValueEventListener(object : ValueEventListener {
//        override fun onDataChange(snapshot: DataSnapshot) {
//            val value = child(Firebase.auth.uid.toString()).value
//            Log.d("test",value.toString())
//        }
//        override fun onCancelled(error: DatabaseError) {
//            TODO("Not yet implemented")
//        }
//    })
        db.child(Firebase.auth.uid.toString()).get().addOnSuccessListener {

            petWeight.text = it.value.toString()
        }




    }




}