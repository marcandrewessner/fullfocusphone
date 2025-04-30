package com.marcandre.ma9launcher.functions


import android.content.ComponentName
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.marcandre.ma9launcher.R


class HotspotFunction(activity: AppCompatActivity) : AbstractFunctionWidget(activity) {

    override fun onCreate(rootView: ViewGroup): View {
        return activity.layoutInflater.inflate(R.layout.function_widget, rootView, false).apply {
            // Set the icon
            val iconView = findViewById<ImageView>(R.id.function_widget_imageview_icon)
            val icon = activity.resources.getDrawable(R.drawable.function_icon_hotspot, null)
            iconView.setImageDrawable(icon)
        }
    }

    override fun onRequested() {
        openHotspotSettings()
    }

    override fun onDestroy() {
        Log.d("MarcAndre", "Hotspot onDestroy")
        // Any cleanup if needed
    }

    private fun openHotspotSettings() {
        try {
            val intent = Intent().apply {
                component = ComponentName("com.android.settings", "com.android.settings.TetherSettings")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            activity.startActivity(intent)
        } catch (e: Exception) {
            Log.e("HotspotFunction", "Error opening Wi-Fi tether settings", e)
        }
    }
}