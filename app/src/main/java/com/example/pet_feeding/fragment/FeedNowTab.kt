package com.example.pet_feeding.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import com.example.pet_feeding.R
import com.example.pet_feeding.model.ScheduleModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.ref.Reference


class FeedNowTab : Fragment() {
    private lateinit var foodAmount: TextInputEditText
    private lateinit var db: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feednow_tab,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btFeedNow = view.findViewById<ImageButton>(R.id.bt_feeding_amount)
        foodAmount = view.findViewById(R.id.feeding_amount)
        db = Firebase.database.reference

        btFeedNow.setOnClickListener {
            Log.d("test", "onViewCreated: Hello")
            db.child("FeedNow").child(Firebase.auth.currentUser?.uid.toString()).child("id").get().addOnSuccessListener {
                if (foodAmount.text.toString() != ""){
                    db.child("FeedNow").child(Firebase.auth.currentUser?.uid.toString()).setValue(
                        object {
                            var id:Int = Integer.parseInt(it.value.toString())+1
                            var amount:Int = Integer.parseInt(foodAmount.text.toString())
                        }
                    )
                    Toast.makeText(requireContext(), "Successfully Feed Now", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(requireContext(), "Please input value", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                if (foodAmount.text.toString() != ""){
                    db.child("FeedNow").child(Firebase.auth.currentUser?.uid.toString()).setValue(
                        object {
                            var id:Int = 1
                            var amount:Int = Integer.parseInt(foodAmount.text.toString())
                        }
                    )
                    Toast.makeText(requireContext(), "Successfully Feed Now", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(requireContext(), "Please input value", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}