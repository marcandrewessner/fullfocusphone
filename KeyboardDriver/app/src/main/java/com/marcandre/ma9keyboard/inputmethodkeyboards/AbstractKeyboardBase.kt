package com.marcandre.ma9keyboard.inputmethodkeyboards

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo

abstract class AbstractKeyboardBase(
    protected val imeContext: InputMethodService,
    protected val writeInterface: KeyWriteInterface) {

    abstract fun onCreateInputView(): View

    abstract fun onStartInputView(editorInfo: EditorInfo?, restarting: Boolean)

    abstract fun onFinishInputView(finishingInput: Boolean)

    abstract fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean

    abstract fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean

    abstract fun onDestroy()
}