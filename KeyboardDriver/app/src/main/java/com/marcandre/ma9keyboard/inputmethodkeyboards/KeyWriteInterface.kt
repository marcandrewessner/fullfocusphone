package com.marcandre.ma9keyboard.inputmethodkeyboards

interface KeyWriteInterface {

    // Function to write text
    fun writeText(text:String, special:Boolean = false): Boolean

}