package com.marcandre.ma9launcher

import android.icu.text.SimpleDateFormat
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import java.util.Date
import java.util.Locale

class WatchTimeKeeper(
    private val mainLooper: Looper,
    private val timeView: TextView,
    private val dateView: TextView,
) {

    private val handler = Handler(mainLooper)
    private val tickRunnable = Runnable{ tick() }

    fun startTicking(){
        // push the first tick
        tick()
    }

    private fun tick(){
        // Update the date and time
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val dateFormat = SimpleDateFormat("dd.MMM", Locale.getDefault())
        val currentTime = Date()

        timeView.text = timeFormat.format(currentTime)
        dateView.text = dateFormat.format(currentTime)
        // Schedule the next update at the start of the next second
        val nextUpdateMillis = 1000 - (System.currentTimeMillis() % 1000)
        handler.postDelayed(tickRunnable, nextUpdateMillis)
    }

    fun destroy(){
        handler.removeCallbacks(tickRunnable)
    }
}