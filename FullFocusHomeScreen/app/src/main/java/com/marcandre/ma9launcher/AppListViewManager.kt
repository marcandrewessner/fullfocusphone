package com.marcandre.ma9launcher

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class AppListViewManager(
    private val packageManager: PackageManager,
    private val layoutInflater: LayoutInflater,
    private val listView: ViewGroup,
    private val openApp: (packageName:String)->Unit) {

    private var appPackages:List<String>? = null

    fun initialize(appPackages:List<String>){

        this.appPackages = appPackages
        listView.removeAllViews()

        val packages = appPackages.map {
            packageManager.getApplicationInfo(it, PackageManager.GET_META_DATA)
        }

        packages.forEachIndexed { index, applicationInfo ->
            // Push the app to screen
            populateApp(
                listView,
                applicationInfo.packageName,
                packageManager.getApplicationLabel(applicationInfo).toString(),
                applicationInfo.loadIcon(packageManager),
                if (index+1<=9) "${index+1}" else ""
            )
        }
    }

    private fun populateApp(
        rootView: ViewGroup,
        appPackage: String,
        appName: String,
        appIcon: Drawable,
        virtualKeyCodeText: String,
    ) {
        layoutInflater.inflate(R.layout.app_list_widget, rootView, false).apply {
            // Open the App when pressed
            setOnClickListener {
                openApp(appPackage)
            }
            // Set the app name
            findViewById<TextView>(R.id.app_list_widget_textview_appname)
                .text = appName
            // Set the app icon
            findViewById<ImageView>(R.id.app_list_widget_imageview_image)
                .setImageDrawable(appIcon)
            // Set the key text
            findViewById<TextView>(R.id.app_list_widget_textview_keyboardnumber)
                .text = virtualKeyCodeText
            // Now attach it to the rootview
            rootView.addView(this)
        }
    }

    fun requestFocus(appPackage: String){
        // Find the index to focus on
        val index = appPackages?.indexOf(appPackage)
        Log.d("INDEX", index.toString())
        // Do nothing if not in package list
        if(index==-1 || index==null)
            return
        // Now focus
        listView.getChildAt(index).requestFocus()
    }

    fun getFocusedPackage():String? {
        val focusedView:View? = listView.focusedChild
        for (i in 0 until listView.childCount) {
            if (listView.getChildAt(i) == focusedView) {
                return appPackages?.get(i)
            }
        }
        return null
    }
}