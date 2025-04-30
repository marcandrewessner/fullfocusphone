package com.marcandre.ma9launcher.functions

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

abstract class AbstractFunctionWidget(protected val activity:AppCompatActivity) {

    abstract fun onCreate(rootView:ViewGroup): View

    abstract fun onRequested()

    abstract fun onDestroy()
}