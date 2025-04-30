package com.marcandre.ma9keyboard.inputmethodkeyboards

import android.graphics.Color
import android.inputmethodservice.InputMethodService
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import com.marcandre.ma9keyboard.R
import com.marcandre.ma9keyboard.utils.dp


// This class is mainly used to manage the view on thisplay
class KeyboardModeLettersViewManager(private val ims:InputMethodService) {

    private var keyboardView:View? = null
    private var bar:LinearLayout? = null

    fun loadView():View{
        keyboardView = ims.layoutInflater.inflate(R.layout.keyboard_main_letters, null)
        bar = keyboardView!!.findViewById(R.id.keyboard_main_linearlayout_bar)
        return keyboardView!!
    }

    fun setUppercaseMode(shift:Boolean, capslock:Boolean){
        var text = "abc"
        if (shift) text = "Abc"
        else if(capslock) text = "ABC"
        keyboardView?.findViewById<TextView>(R.id.keyboard_main_textview_mode)?.text = text
    }

    fun setLetters(letters:String){
        clearLetters()

        for(letter in letters){
            TextView(keyboardView!!.context).apply {
                text = letter.toString()
                gravity = Gravity.CENTER

                setPadding(12.dp, 0, 12.dp, 0)

                layoutParams = LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT
                ).apply{
                    rightMargin = 4.dp
                }

                bar?.addView(this)
            }
        }
    }

    fun setActiveLetter(letterIndex:Int){
        bar?.children?.forEachIndexed { index, view ->

            val isActive = letterIndex==index
            val backgroundColor = if(isActive) Color.BLUE else Color.WHITE
            val textColor = if(isActive) Color.WHITE else Color.BLACK

            (view as TextView).apply {
                setBackgroundColor(backgroundColor)
                setTextColor(textColor)
            }
        }
    }

    fun clearLetters(){
        bar?.removeAllViews()
    }

    fun destroy(){
        bar = null
        keyboardView = null
    }
}

