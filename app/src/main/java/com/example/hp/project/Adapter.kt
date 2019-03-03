package com.example.hp.project

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_row.view.*
import java.util.*

class adapter(val alarms: ArrayList<alarm> , val alarmManager: AlarmManager) : RecyclerView.Adapter<adapter.AlarmHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): AlarmHolder {

        context=container.context
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.item_row, container, false)
        return AlarmHolder(view)
    }


    override fun getItemCount() = alarms.size

    override fun onBindViewHolder(holder: AlarmHolder, position: Int) {
        val currentAlarm = alarms.get(position)

        with(holder.itemView) {
            itemrow_title.text = currentAlarm.title
            time_hr.text = currentAlarm.hour.toString()
            time_min.text = currentAlarm.min.toString()
            time_dots.text=":"

            if(currentAlarm.switch_value)
            itemrow_switch.setChecked(true)


        }

        if(holder.itemView.itemrow_switch.isChecked){
            alarm_activate(currentAlarm)
        }

    }


    inner class AlarmHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun alarm_activate(currentAlarm:alarm) {

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, currentAlarm.hour)
            set(Calendar.MINUTE, currentAlarm.min)
        }

        val intent=Intent(context , StepTracker::class.java)

        val pi = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pi
        )

    }
}
