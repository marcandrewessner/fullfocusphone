package com.marcandre.ma9keyboard

import android.inputmethodservice.InputMethodService
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import com.marcandre.ma9keyboard.inputmethodkeyboards.AbstractKeyboardBase
import com.marcandre.ma9keyboard.inputmethodkeyboards.KEYCODE_MAPPING
import com.marcandre.ma9keyboard.inputmethodkeyboards.KeyWriteInterface
import com.marcandre.ma9keyboard.inputmethodkeyboards.KeyboardModeLetters
import com.marcandre.ma9keyboard.inputmethodkeyboards.KeyboardModeNumbers
import com.marcandre.ma9keyboard.inputmethodkeyboards.KeyboardModeSymbols
import com.marcandre.ma9keyboard.inputmethodkeyboards.SPECIAL_HW_KEY


class KeyboardMain : InputMethodService(), KeyWriteInterface {

    private var currentKeyboardIndex = 0
    private val keyboardList = listOf("letters", "numbers", "symbols")

    private var editorInfo:EditorInfo? = null
    private var viewBox:FrameLayout? = null
    private var keyboard:AbstractKeyboardBase? = null

    override fun onCreateInputView(): View {
        viewBox = FrameLayout(this)
        keyboard = KeyboardModeLetters(this, this)
        viewBox!!.addView(keyboard!!.onCreateInputView())
        return viewBox!!
    }

    override fun onStartInputView(editorInfo: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(editorInfo, restarting)
        keyboard?.onStartInputView(editorInfo, restarting)
        this.editorInfo = editorInfo
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        super.onFinishInputView(finishingInput)
        keyboard?.onFinishInputView(finishingInput)
        this.editorInfo = null
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean{
        return keyboard?.onKeyUp(keyCode, event) ?: false || super.onKeyUp(keyCode, event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // First check if the keyboard handles this
        val isKeyboardHandled = keyboard?.onKeyDown(keyCode, event) ?: false

        if(isKeyboardHandled)
            return true

        // Astrix button switches
        if(SPECIAL_HW_KEY.ASTRIX.value == KEYCODE_MAPPING.getOrDefault(keyCode, 9999)){
            switchKeyboard()
            return true
        }

        // Handle the enter key if has editorInfo
        if(SPECIAL_HW_KEY.ENTER.value == KEYCODE_MAPPING.getOrDefault(keyCode, 9999))
            if( sendCurrentImeAction() ) return true

        return super.onKeyDown(keyCode, event)
    }

    override fun writeText(text: String, special: Boolean): Boolean {
        if (!special) {
            currentInputConnection.commitText(text, 1)
            return true
        }

        if (text == "del") {
            val selectedText = currentInputConnection.getSelectedText(0)
            if (!selectedText.isNullOrEmpty()) {
                // If there is selected text, replace it with an empty string
                currentInputConnection.commitText("", 1)
            } else {
                // Handle deletion of potentially multi-character emojis
                handleEmojiDeletion()
            }
        }

        return true
    }

    private fun handleEmojiDeletion() {
        val charsBeforeCursor = currentInputConnection.getTextBeforeCursor(2, 0)
        if (!charsBeforeCursor.isNullOrEmpty()) {
            val deleteCount = if (isSurrogatePair(charsBeforeCursor)) 2 else 1
            currentInputConnection.deleteSurroundingText(deleteCount, 0)
        }
    }

    private fun isSurrogatePair(text: CharSequence): Boolean {
        return text.length == 2 && Character.isSurrogatePair(text[0], text[1])
    }

    // Send the enter action => 'Done' 'Search' 'Go'
    private fun sendCurrentImeAction() : Boolean {
        if (currentInputConnection == null || editorInfo == null)
            return false

        val actionId: Int = editorInfo!!.imeOptions and EditorInfo.IME_MASK_ACTION
        currentInputConnection.performEditorAction(actionId)
        return true
    }

    private fun switchKeyboard(){
        Log.d("MarcAndre", "Switching keyboard $currentKeyboardIndex")
        currentKeyboardIndex = (currentKeyboardIndex+1) % keyboardList.size
        keyboard?.onDestroy()
        viewBox?.removeAllViews()

        // Do nothing if no viewbox
        viewBox ?: return

        // Now create the corresponding keyboard
        val newKeyboard = keyboardList[currentKeyboardIndex]
        keyboard = when (newKeyboard) {
            "letters" -> KeyboardModeLetters(this, this)
            "numbers" -> KeyboardModeNumbers(this, this)
            "symbols" -> KeyboardModeSymbols(this, this)
            else -> throw Exception("Keyboard $newKeyboard not found!")
        }

        viewBox!!.addView( keyboard!!.onCreateInputView() )
        viewBox!!.requestLayout()
    }

    override fun onDestroy() {
        keyboard?.onDestroy()
        super.onDestroy()
    }
}