package com.marcandre.ma9keyboard.inputmethodkeyboards

import android.inputmethodservice.InputMethodService
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import com.marcandre.ma9keyboard.R

class KeyboardModeNumbers(imeContext: InputMethodService, writeInterface: KeyWriteInterface) : AbstractKeyboardBase(imeContext, writeInterface){

    private val LOGDTAG = "MarcAndre Keyboard-Numbers"

    override fun onCreateInputView(): View {
        return imeContext.layoutInflater.inflate(R.layout.keyboard_main_letters, null).also {
            (it as LinearLayout).findViewById<TextView>(R.id.keyboard_main_textview_mode).text = "123"
        }
    }

    override fun onStartInputView(editorInfo: EditorInfo?, restarting: Boolean) {}

    override fun onFinishInputView(finishingInput: Boolean) {}

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // First check if there is even something to handle
        if(imeContext.currentInputConnection==null || !KEYCODE_MAPPING.containsKey(keyCode))
            return false

        val virtualKeyCode = KEYCODE_MAPPING.getValue(keyCode)

        // Now handle the key
        Log.d(LOGDTAG, "onKeyDown virtualKeyCode $virtualKeyCode")

        // Handle the numbers directly
        if(virtualKeyCode>=0){
            writeInterface.writeText(virtualKeyCode.toString())
            return true
        }

        if(virtualKeyCode==SPECIAL_HW_KEY.HASH.value){
            writeInterface.writeText("del", true)
            return true
        }

        return false
    }

    override fun onDestroy() {}
}