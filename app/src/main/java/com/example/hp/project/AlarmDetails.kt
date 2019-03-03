package com.example.hp.project

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.alarm_detail.*

class AlarmDetails : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_detail)

        FirebaseApp.initializeApp(this)
        val database = FirebaseDatabase.getInstance()
        val rootNode = database.reference

        btn_cancel.setOnClickListener {
            finish()
        }

        btn_save.setOnClickListener {


            rootNode.child("Alarms").push().setValue(alarm().apply {
                this.hour=timePicker.hour
                this.min=timePicker.minute
                this.title=et_title.text.toString()
            }).addOnCompleteListener {
                finish()
            }
                .addOnFailureListener { it.printStackTrace() }
       }

    }
}