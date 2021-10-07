package com.example.nearbymessagescanner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.Message
import com.google.android.gms.nearby.messages.MessageListener

const val TAG = "BeaconMessageReceiver"

class BeaconMessageReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        Nearby.getMessagesClient(context!!).handleIntent(intent!!, object : MessageListener() {
            override fun onFound(p0: Message) {
                Log.d(TAG, "Found message via PendingIntent: $p0")
            }

            override fun onLost(p0: Message) {
                Log.d(TAG, "Lost message via PendingIntent:  + $p0")
            }
        })
    }
}