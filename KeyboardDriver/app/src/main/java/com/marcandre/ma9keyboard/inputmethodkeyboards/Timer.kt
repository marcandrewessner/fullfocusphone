package com.marcandre.ma9keyboard.inputmethodkeyboards

import android.os.Handler
import android.os.Looper

class Timer(looper: Looper, private val ring:()->Unit) : Handler(looper){

    private val runnable = Runnable {
        ring()
    }

    fun startTimer(milliseconds:Long){
        removeCallbacks(runnable)
        postDelayed(runnable, milliseconds)
    }

    fun stopTimer(){
        removeCallbacks(runnable)
    }

}