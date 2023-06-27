package com.example.bootcounter

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvCounter = findViewById<TextView>(R.id.tv_counter)
        tvCounter.text = getNotificationMessage()
    }

    private fun getNotificationMessage(): String {
//        TODO: Add correct download format for TextView (1 - timestamp etc.)
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val secondTimestamp = sharedPreferences.getLong("time_between_2_last_boot_events", -1L)
        return if (secondTimestamp == -1L) {
            "No boots detected"
        } else {
            secondTimestamp.toString()
        }
    }
}