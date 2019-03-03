package com.example.hp.project

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_step_tracker.*
import android.media.RingtoneManager
import android.net.Uri
import android.media.AudioManager
import android.media.MediaPlayer
import java.io.IOException


class StepTracker : AppCompatActivity(), SensorEventListener {

    lateinit var mMediaPlayer:MediaPlayer

    val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    var totalsteps=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_tracker)

        super.onStart()
        val stepCounter= sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        sensorManager.registerListener(this , stepCounter ,100)
        playSound(this , getAlarmUri()!!)
    }


    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {

            //          var values =event.values
//
//            if(values[0]==1)
            totalsteps= totalsteps+1
            stepCount.setText(totalsteps.toString())

            if(totalsteps>=5){
                mMediaPlayer.stop()
                sensorManager.unregisterListener(this)
                finish()
            }

        }
    }

    private fun playSound(context: Context, alert: Uri) {
        mMediaPlayer = MediaPlayer()
        try {
            mMediaPlayer.setDataSource(context, alert)
            val audioManager = context
                .getSystemService(Context.AUDIO_SERVICE) as AudioManager
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM)
                mMediaPlayer.prepare()
                mMediaPlayer.start()
            }
        } catch (e: IOException) {
            println("OOPS")
        }

    }

    private fun getAlarmUri(): Uri? {
        var alert: Uri? = RingtoneManager
            .getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alert == null) {
            alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            if (alert == null) {
                alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            }
        }
        return alert
    }
}
