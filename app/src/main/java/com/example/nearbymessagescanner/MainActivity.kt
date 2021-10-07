package com.example.nearbymessagescanner

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.nearbymessagescanner.databinding.ActivityMainBinding
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    override fun onDestroy() {
        unSubscribe()
        super.onDestroy()
    }

    private fun setupViews() {
        binding.subscribe.setOnClickListener { backgroundSubscribe() }
        binding.unsubscribe.setOnClickListener { unSubscribe() }
    }

    private fun backgroundSubscribe() {
        Log.d(TAG, "Subscribing for background updates.")
        val options = SubscribeOptions.Builder().setStrategy(Strategy.BLE_ONLY).build()
        messagesClient.subscribe(pendingIntent, options)
    }

    private fun unSubscribe() {
        Log.d(TAG, "unsubscribing for background updates.")
        messagesClient.unsubscribe(pendingIntent)
    }

    private val pendingIntent: PendingIntent
        get() = PendingIntent.getBroadcast(
            this,
            0,
            Intent(this, BeaconMessageReceiver::class.java),
            PendingIntent.FLAG_MUTABLE
        )

    private val messagesClient: MessagesClient
        get() {
            val options = MessagesOptions.Builder().setPermissions(NearbyPermissions.BLE).build()
            return Nearby.getMessagesClient(applicationContext, options)
        }
}