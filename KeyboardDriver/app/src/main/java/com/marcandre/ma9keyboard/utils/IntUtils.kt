package com.marcandre.ma9keyboard.utils

import android.content.res.Resources

// Convert integer to dp value
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()