package com.marcandre.ma9launcher

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.marcandre.ma9keyboard.inputmethodkeyboards.KEYCODE_MAPPING
import com.marcandre.ma9launcher.functions.AbstractFunctionWidget
import com.marcandre.ma9launcher.functions.HotspotFunction
import com.marcandre.ma9launcher.functions.TorchFunction


class MainActivity : AppCompatActivity() {

    private lateinit var appPackages: List<String>

    private lateinit var watchTimeKeeper: WatchTimeKeeper
    private lateinit var appListViewManager:AppListViewManager
    private lateinit var functionsList:List<AbstractFunctionWidget>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appPackages = resources.getStringArray(R.array.app_package_list_emulator).toList()


        appListViewManager = AppListViewManager(
            packageManager,
            layoutInflater,
            findViewById<LinearLayout>(R.id.activity_main_linearlayout_applist)){
            packageName -> openApp(packageName)
        }
        appListViewManager.initialize(appPackages)
        appListViewManager.requestFocus(appPackages[0])


        watchTimeKeeper = WatchTimeKeeper(
            mainLooper,
            findViewById<TextView>(R.id.activity_main_textview_time),
            findViewById<TextView>(R.id.activity_main_textview_date)
        )
        watchTimeKeeper.startTicking()

        // Create the functions
        initializeFunctions(
            TorchFunction(this),
            HotspotFunction(this),
        )
    }

    private fun initializeFunctions(vararg functions:AbstractFunctionWidget){
        functionsList = functions.toList()
        val functionListView = findViewById<LinearLayout>(R.id.activity_main_linearlayout_functionlist)
        functionListView.removeAllViews()

        // Check if should remove the functionListView
        val shouldHideFunctionList = resources.getBoolean(R.bool.hideFunctionsSideBar)
        if(shouldHideFunctionList){
            functionListView.visibility = View.GONE
            return
        }

        functionsList.forEach{ func ->
            val funcView = func.onCreate(functionListView)
            funcView.setOnClickListener{ func.onRequested() }
            functionListView.addView(funcView)
        }
    }

    private fun openApp(appPackage:String){
        Log.d("MarcAndre", "Open App $appPackage")
        val launchIntent: Intent? = packageManager.getLaunchIntentForPackage(appPackage)
        launchIntent?.let { startActivity(it) }
    }


    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        val virtualKey = KEYCODE_MAPPING.getOrDefault(keyCode, -9999)
        Log.d("MarcAndre", "keyUp: $virtualKey")

        // Open app for hardware keys 1 .. 9
        if(virtualKey in 1 until appPackages.size+1){
            val appPackage = appPackages[virtualKey-1]
            //appListViewManager.requestFocus(appPackage)
            openApp(appPackage)
            return true
        }

        return super.onKeyUp(keyCode, event)
    }


    override fun onDestroy() {
        watchTimeKeeper.destroy()
        super.onDestroy()
    }
}