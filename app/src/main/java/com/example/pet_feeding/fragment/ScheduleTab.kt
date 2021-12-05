package com.example.pet_feeding.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pet_feeding.R
import com.example.pet_feeding.model.ScheduleModel
import com.example.pet_feeding.recyclerview.ScheduleAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ScheduleTab : Fragment() {
//    private var hour = arrayListOf<String>("12","1")
//    private var minute = arrayListOf<String>("10","20")
//    private var foodAmount = arrayListOf<String>("12","12")
//    private var ifCheck = arrayListOf<Boolean>(false,false)
//    private var readSchedules = ReadSchedule(hour,minute,foodAmount,ifCheck)
      private lateinit var schedules :ArrayList<ScheduleModel>
//    private val schedules  = Schedule("12","","","true")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_schedule_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         schedules = ArrayList<ScheduleModel>()
         var layoutManager: RecyclerView.LayoutManager?= null
         var adapter: RecyclerView.Adapter<ScheduleAdapter.ViewHolder>? = null
         val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
         val btCreateScheduleCard = view.findViewById<FloatingActionButton>(R.id.floating_action_button)
         val btSwitch = view.findViewById<Switch>(R.id.schedule_switch)
         val database = Firebase.database
         val myRef = database.getReference("schedules")
         recyclerView?.adapter = ScheduleAdapter(schedules)

        layoutManager = LinearLayoutManager(view.context)
        recyclerView?.layoutManager = layoutManager


        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                schedules.clear()
                val value = snapshot.child(Firebase.auth.uid.toString()).children
                for (snap in value){
                    var tmpSchedule = ScheduleModel(snap.key.toString(),snap.child("hour").value.toString(),snap.child("minute").value.toString(),snap.child("foodAmount").value.toString(),
                        snap.child("btSwitch").value as Boolean
                    )
                    schedules.add(tmpSchedule)
                }
                recyclerView?.adapter?.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })





        btCreateScheduleCard.setOnClickListener(View.OnClickListener {
            replaceFragment(FragmentCreateScheduleCard())
        })

    }




    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.commit{
            replace(R.id.bt_tab_container,fragment)
            addToBackStack("schedule")
        }

    }


}


