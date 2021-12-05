package com.example.pet_feeding.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.pet_feeding.R
import com.example.pet_feeding.fragment.ScheduleTab
import com.example.pet_feeding.model.ScheduleModel
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class ScheduleAdapter(private val schedules: ArrayList<ScheduleModel>) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>(){
var itemOnClock: ItemCallBack? = null

    private lateinit var db:DatabaseReference


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.schedule_layout,parent,false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        db = Firebase.database.reference

        holder.scheduleTime.text = schedules[position].hour+":"+schedules[position].minute
        holder.scheduleFoodAmount.text = schedules[position].foodAmount+"g"
        holder.btSwitch.isChecked = schedules[position].btSwitch

        holder.btSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                Log.d("test", "onBindViewHolder: ${schedules[position].scheduleKey}")
            if (!isChecked){
                db.child("schedules").child(Firebase.auth.currentUser?.uid.toString())
                    .child(schedules[position].scheduleKey).child("btSwitch").setValue(false)

            }else{
                db.child("schedules").child(Firebase.auth.currentUser?.uid.toString())
                    .child(schedules[position].scheduleKey).child("btSwitch").setValue(true)
            }
        }

        holder.btDelete.setOnClickListener {
            db.child("schedules").child(Firebase.auth.currentUser?.uid.toString())
                .child(schedules[position].scheduleKey).removeValue()
        }


    }


    override fun getItemCount(): Int {
        return schedules.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var scheduleTime : TextView = itemView.findViewById(R.id.schedule_time)
        var scheduleFoodAmount : TextView = itemView.findViewById(R.id.schedule_foodAmount)
        var btSwitch: SwitchMaterial = itemView.findViewById(R.id.schedule_switch)
        var btDelete: ImageView = itemView.findViewById(R.id.bt_delete)
        var btEdit: ImageView = itemView.findViewById(R.id.bt_edit)
    }

}


interface ItemCallBack{
    fun <T> onClock(t: T)
}