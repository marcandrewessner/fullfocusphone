package com.marcandre.ma9keyboard.inputmethodkeyboards

import android.inputmethodservice.InputMethodService
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo

class KeyboardModeLetters(imeContext: InputMethodService, writeInterface: KeyWriteInterface) : AbstractKeyboardBase(imeContext, writeInterface) {

    val SHIFT_DOWN_SYMBOL = "\u21D3"
    val SHIFT_UP_SYMBOL = "\u21E7"
    val CAPSLOCK_SYMBOL = "\u21EA"

    // Create a mapping for the lettersets.
    // Note: the 1 key is the delete key
    val LETTERSET_MAPPING = mapOf<Int,String>(
        2 to "abcä",
        3 to "def",
        4 to "ghi",
        5 to "jkl",
        6 to "mnoö",
        7 to "pqrs",
        8 to "tuvü",
        9 to "wxyz",
        0 to " .,?!:",
        SPECIAL_HW_KEY.HASH.value to SHIFT_UP_SYMBOL+CAPSLOCK_SYMBOL+SHIFT_DOWN_SYMBOL
    )

    private val LOGDTAG = "MarcAndre Keyboard-Letters"
    private val TIME_SAME: Long = 800

    private lateinit var timer: Timer
    private val kbLettersVManager = KeyboardModeLettersViewManager(imeContext)

    // Now keep track of the currently active letter
    private var activeLetterSet = ""
    private var activeLetterSetIndex = 0
    private var modifierCapsLock = false
    private var modifierShiftActivated = false
    private var modifierShift = false

    override fun onCreateInputView(): View {
        Log.d(LOGDTAG, "onCreateInputView")
        timer = Timer(imeContext.mainLooper) { ringTimer() }

        val keyboardView = kbLettersVManager.loadView()
        kbLettersVManager.setUppercaseMode(modifierShift, modifierCapsLock)

        return keyboardView
    }

    override fun onStartInputView(editorInfo: EditorInfo?, restarting: Boolean) {
        Log.d(LOGDTAG, "onStartInputView")
        kbLettersVManager.clearLetters()
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        Log.d(LOGDTAG, "onFinishInputView")
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // First check if there is even something to handle
        if(imeContext.currentInputConnection==null || !KEYCODE_MAPPING.containsKey(keyCode))
            return false

        val virtualKeyCode = KEYCODE_MAPPING.getValue(keyCode)

        // If not num_key or astrix or hash => don't handle
        if(!(virtualKeyCode>=0 || virtualKeyCode== SPECIAL_HW_KEY.ASTRIX.value || virtualKeyCode== SPECIAL_HW_KEY.HASH.value))
            return false

        // Now handle the key
        Log.d(LOGDTAG, "onKeyDown virtualKeyCode $virtualKeyCode")

        // Handle the delete key properly
        if(virtualKeyCode==1){
            writeInterface.writeText("del", special = true)
            activeLetterSet = ""
            activeLetterSetIndex = 0
            kbLettersVManager.clearLetters()
            timer.stopTimer()
            return true
        }

        // Now handle the letter sets in the list
        else if(LETTERSET_MAPPING.containsKey(virtualKeyCode)){
            val newLetterSet = LETTERSET_MAPPING.getValue(virtualKeyCode)
            val isSameLetterSet = activeLetterSet==newLetterSet

            // Same letter pressed
            if(isSameLetterSet) {
                activeLetterSetIndex = (activeLetterSetIndex + 1) % activeLetterSet.length
                kbLettersVManager.setActiveLetter(activeLetterSetIndex)
            }
            else {
                modifierShift = modifierShiftActivated
                modifierShiftActivated = false
                activeLetterSet = newLetterSet
                activeLetterSetIndex = 0
                kbLettersVManager.setLetters(activeLetterSet)
                kbLettersVManager.setActiveLetter(activeLetterSetIndex)
            }

            val letter = handleLetter(
                activeLetterSet[activeLetterSetIndex].toString(),
                isSameLetterSet
            )

            if(isSameLetterSet && letter.isNotEmpty())
                writeInterface.writeText("del", true)

            writeInterface.writeText(letter)

            kbLettersVManager.setUppercaseMode(modifierShift||modifierShiftActivated, modifierCapsLock)

            timer.startTimer(TIME_SAME)

            return true
        }

        // Signal that key has been handled
        return false
    }

    private fun handleLetter(letter:String, sameLetterSet:Boolean):String {
        // Handle the modifiers
        if (letter == SHIFT_DOWN_SYMBOL || letter == SHIFT_UP_SYMBOL || letter == CAPSLOCK_SYMBOL) {
            modifierShift = false
            modifierShiftActivated = letter == SHIFT_UP_SYMBOL
            modifierCapsLock = letter == CAPSLOCK_SYMBOL
            return ""
        }

        return if (modifierShift || modifierCapsLock) letter.uppercase() else letter
    }

    private fun ringTimer(){
        Log.d(LOGDTAG, "Timer Rings")
        // Clear the active letters
        activeLetterSet = ""
        activeLetterSetIndex = 0
        modifierShift = false
        kbLettersVManager.clearLetters()
        kbLettersVManager.setUppercaseMode(modifierShift||modifierShiftActivated, modifierCapsLock)
    }

    override fun onDestroy() {
        timer.stopTimer()
        kbLettersVManager.destroy()
    }

}