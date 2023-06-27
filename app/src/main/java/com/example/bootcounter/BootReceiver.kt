package com.example.bootcounter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

//        TODO: Restore the notification with the updated information (if needed)
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            AlarmScheduler.schedulePeriodicAction(context)
        }
    }
}