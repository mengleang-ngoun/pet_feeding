package com.example.pet_feeding.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.commit
import com.example.pet_feeding.R
import com.example.pet_feeding.model.ScheduleModel
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class FragmentCreateScheduleCard : Fragment() {

    private lateinit var db: DatabaseReference
    private lateinit var schedule: ScheduleModel
    private lateinit var getHour:String
    private lateinit var getMinute:String
    private lateinit var getFoodAmount:String
    private var getSwitch:Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_create_schedule_card, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val time = view.findViewById<TextInputLayout>(R.id.time)
        val foodAmount = view.findViewById<TextInputLayout>(R.id.foodAmount)
        val addSchedule = view.findViewById<ImageButton>(R.id.bt_create_card_schedule)
        val btEditSchedule = view.findViewById<ImageView>(R.id.bt_edit)


        db = Firebase.database.reference


        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select schedule feeder device")
                .build()

        time.editText?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus && !picker.isAdded){
                picker.show(requireActivity().supportFragmentManager,null)
            }
        }

        picker.addOnPositiveButtonClickListener {
            Log.d("test",picker.hour.toString()+":"+picker.minute)
            getHour = picker.hour.toString()
            getMinute = picker.minute.toString()
            time.editText?.setText("$getHour:$getMinute")

        }

        addSchedule.setOnClickListener {

            getFoodAmount = foodAmount.editText?.text.toString()
            if (getFoodAmount != ""  && picker != null){
                db.child("schedules").child(Firebase.auth.currentUser?.uid.toString())
                    .push().setValue(object {
                        val hour = getHour
                        val minute =  getMinute
                        val foodAmount =  getFoodAmount
                        val btSwitch =  getSwitch })
                Toast.makeText(requireContext(), "successfully added schedule", Toast.LENGTH_SHORT).show()
                replaceFragment(ScheduleTab())
            }else{
                Toast.makeText(requireContext(),"Please input value",Toast.LENGTH_SHORT).show()
            }


        }








        super.onViewCreated(view, savedInstanceState)
    }


    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.commit{
            replace(R.id.bt_tab_container,fragment)
            addToBackStack("schedule")
        }

    }

}