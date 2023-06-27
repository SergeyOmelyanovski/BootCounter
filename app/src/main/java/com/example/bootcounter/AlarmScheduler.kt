package com.example.bootcounter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent


class AlarmScheduler {

    companion object {

        private const val REQUEST_CODE: Int = 1001
        private const val TIME_INTERVAL: Long = 15 * 60 * 1000

        fun schedulePeriodicAction(context: Context?) {
            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, ActionReceiver::class.java)

            val pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), TIME_INTERVAL, pendingIntent)
        }
    }
}