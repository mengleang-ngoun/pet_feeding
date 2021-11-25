package com.example.pet_feeding

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import org.w3c.dom.Text

class schedule_Adapter : RecyclerView.Adapter<schedule_Adapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): schedule_Adapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: schedule_Adapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var schedule_switch : SwitchMaterial
        var schedule_time : TextView
        var schedule_repeat : TextView

        init {
            schedule_switch = itemView.findViewById(R.id.schedule_switch)
            schedule_time = itemView.findViewById(R.id.schedule_time)
            schedule_repeat = itemView.findViewById(R.id.schedule_repeat)
        }
    }

}