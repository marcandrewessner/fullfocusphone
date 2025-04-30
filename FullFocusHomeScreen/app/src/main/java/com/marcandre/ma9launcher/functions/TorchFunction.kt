package com.marcandre.ma9launcher.functions

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.marcandre.ma9launcher.R


class TorchFunction(activity:AppCompatActivity) : AbstractFunctionWidget(activity) {

    private var isTorchOn: Boolean = false
    private val cameraManager: CameraManager by lazy {
        activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }


    override fun onCreate(rootView: ViewGroup): View {
        return activity.layoutInflater.inflate(R.layout.function_widget, rootView, false).apply {
            // Set the icon
            val iconView = findViewById<ImageView>(R.id.function_widget_imageview_icon)
            val icon = activity.resources.getDrawable(R.drawable.function_icon_torch, null)
            iconView.setImageDrawable(icon)
        }
    }

    override fun onRequested() {
        toggleTorch()
    }

    override fun onDestroy() {
        Log.d("MarcAndre", "Torch onDestroy")
        // Turn off the torch when the widget is destroyed
        setTorchMode(false)
    }

    private fun toggleTorch() {
        setTorchMode(!isTorchOn)
    }

    private fun setTorchMode(turnOn: Boolean) {
        try {
            val cameraId = cameraManager.cameraIdList.find {
                cameraManager.getCameraCharacteristics(it)
                    .get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
            } ?: return // No camera with a flash

            cameraManager.setTorchMode(cameraId, turnOn)
            isTorchOn = turnOn
        } catch (e: CameraAccessException) {
            Log.e("TorchFunction", "Error accessing camera for torch", e)
        } catch (e: IllegalArgumentException) {
            Log.e("TorchFunction", "Error setting torch mode", e)
        }
    }
}