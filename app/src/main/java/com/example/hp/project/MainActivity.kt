package com.example.hp.project

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.database.*


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val myRef = FirebaseDatabase.getInstance().reference

        fab.setOnClickListener {
            intent =Intent(this , AlarmDetails::class.java)
            startActivity(intent)
        }

        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val alarm_list = ArrayList<alarm>()
        val alarmAdapter = adapter(alarm_list,alarmManager)
        rv_alarms.adapter =alarmAdapter

        rv_alarms.setLayoutManager(LinearLayoutManager(baseContext))


//        myRef.child("Alarms").addValueEventListener(object :ValueEventListener{
//            override fun onCancelled(p0: DatabaseError) {
//
//
//            }
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                val children = dataSnapshot.children
//
//                for (ds: DataSnapshot in children) {
//
//                    val currentalarm: alarm? = ds.getValue(alarm::class.java)
//
//                    alarm_list.add(currentalarm!!)
//                    alarmAdapter.notifyDataSetChanged()
//                    Log.e("TAG", "onDataChanged : Name : ${currentalarm.title}")
//
//                }
//                Log.e("AAAAAAAAAA" , " ${alarm_list.size}")
//            }
//
//
//        })

        myRef.child("Alarms").addChildEventListener(object :ChildEventListener{
            override fun onCancelled(e: DatabaseError) {
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousKey: String?) {
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {

//                    val obj  = dataSnapshot.getValue(alarm::class.java)
//                    alarm_list.add(obj!!)
//
//                alarmAdapter.notifyDataSetChanged()
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, previousKey: String?) {

                    val obj = dataSnapshot.getValue(alarm::class.java)
                    alarm_list.add(obj!!)

                alarmAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            }


        })


    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}
