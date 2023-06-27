package com.example.bootcounter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class ActionReceiver : BroadcastReceiver() {

    companion object {
        private const val CHANNEL_ID = "my_channel"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationMessage = getNotificationMessage(context)
        showNotification(context, notificationMessage)
    }

    private fun showNotification(context: Context?, timestamp: String) {
        val ctx = context ?: return
        val notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Download Notification Channel", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "This notification contains download information"
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(ctx, CHANNEL_ID)
            .setContentTitle("Boot counter app")
            .setContentText(timestamp)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager.notify(1, notification.build())
    }

    private fun saveFirstTimestamp(context: Context?, firstTimestamp: Long) {
        val sharedPreferences = context?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putLong("timestamp_of_the_boot_event", firstTimestamp)?.apply()
    }

    private fun saveSecondTimestamp(context: Context?, secondTimestamp: Long) {
        val sharedPreferences = context?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putLong("time_between_2_last_boot_events", secondTimestamp)?.apply()
    }

    private fun getNotificationMessage(context: Context?) : String {
        val sharedPreferences = context?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val firstTimestamp = sharedPreferences?.getLong("timestamp_of_the_boot_event", -1L)
        val secondTimestamp = System.currentTimeMillis()
        return if (firstTimestamp == -1L) {
            saveFirstTimestamp(context, secondTimestamp)
            "The boot was detected with the timestamp = $secondTimestamp"
        } else {
            saveFirstTimestamp(context, secondTimestamp)
            saveSecondTimestamp(context, secondTimestamp)
            val delta = secondTimestamp - (firstTimestamp ?: -1)
            "Last boots time delta = $delta"
        }
    }
}