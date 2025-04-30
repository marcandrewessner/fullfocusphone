package com.marcandre.ma9keyboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private var isKeyLoggingActivated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initKeyCodeToggleButton()
        initOpenSettingsButton()
    }

    private fun initKeyCodeToggleButton(){
        findViewById<Button>(R.id.activity_main_button_togglekeylogging).setOnClickListener{
            val thisButton = it as Button

            isKeyLoggingActivated = !isKeyLoggingActivated
            val stateString = if (isKeyLoggingActivated) "ON" else "OFF"

            val buttonText = "KeyCode Logging: $stateString"
            thisButton.text = buttonText
        }
    }

    private fun initOpenSettingsButton(){
        findViewById<Button>(R.id.activity_main_button_settings).setOnClickListener {
            val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
            startActivity(intent)
        }
    }

    private val keyLoggingTag = "MarcAndre Activity KeyLogging"

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if(!isKeyLoggingActivated)
            return super.onKeyUp(keyCode, event)

        Log.d(keyLoggingTag, "onKeyUp keyCode: $keyCode")
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(!isKeyLoggingActivated)
            return super.onKeyDown(keyCode, event)

        Log.d(keyLoggingTag, "onKeyDown keyCode: $keyCode")
        return true
    }

}