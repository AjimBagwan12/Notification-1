package com.example.notification

import android.R
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notification.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var notificationManager : NotificationManagerCompat

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager = NotificationManagerCompat.from(this)
        createNotificationChannels()

        binding.btnNotify.setOnClickListener {
            val bitmap = BitmapFactory.decodeResource(
                resources,
                R.mipmap.sym_def_app_icon
            )


            val actionPendingIntent =
                PendingIntent.getActivities(
                    this@MainActivity,
                    1,
                    arrayOf(Intent(this@MainActivity,DemoActivity::class.java)),
                    PendingIntent.FLAG_IMMUTABLE
                )

            val notification =
                NotificationCompat.Builder(this@MainActivity,"updates")
                    .setContentTitle("AB Tech Updates!")
                    .setContentText("This is a sample update text...")
                    .setColor(Color.RED)
                    .setSmallIcon(R.mipmap.sym_def_app_icon)
                    //.setLargeIcon(R.mipmap.sym_def_app_icon)
                    .setAutoCancel(false)
                    .setLights(Color.RED,300,400)
            //.setSound(Uri.parse("/storage/bitcode/audio.mp3"))
                    .setVibrate(
                        arrayOf(400L,500L,300L,500L,400L,500L).toLongArray()
                    )
                    .setOngoing(true)
                    .setContentIntent(actionPendingIntent)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .build()

            notificationManager.notify(1,notification)
        }

        binding.btnCancel.setOnClickListener {
            notificationManager.cancel(1)
        }
    }

    private fun createNotificationChannels(){
        /*val channelUpdates =
           NotificationChannel(
               "updates",
               "Bitcode Updates",
               NotificationManager.IMPORTANCE_HIGH
           )*/

        val updatesChannel = NotificationChannelCompat.Builder(
            "updates",
            NotificationManager.IMPORTANCE_HIGH
        ).setDescription("Receive updates about events at ABTech")
            .setName("ABTech updates")
            .build()

        notificationManager.createNotificationChannel(updatesChannel)

        val placementChannel = NotificationChannelCompat.Builder(
            "placement",
            NotificationManager.IMPORTANCE_HIGH
        ).setDescription("Receive updates about placement at ABTech")
            .setName("ABTech placement updates")
            .build()

        notificationManager.createNotificationChannel(placementChannel)
    }
}



