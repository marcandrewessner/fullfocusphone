package com.marcandre.ma9keyboard.inputmethodkeyboards

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.children
import com.marcandre.ma9keyboard.R

class KeyboardModeSymbols(imeContext:InputMethodService, writeInterface: KeyWriteInterface) : AbstractKeyboardBase(imeContext, writeInterface) {

    private var currentPageIndex = 0
    private val pages = listOf<List<String>>(
        ".:?!_,()\"".map { it.toString() },
        "*+-/%=$@#".map { it.toString() },
        listOf("😂", "🤣", "😁", "😄", "🥺", "😎", "😘", "😊", "😭",),
        listOf("😔", "😍", "😡", "😱", "😩", "🤗", "❤️", "👌", "💪",),
        listOf("👍", "✌️", "👊", "👉", "👀", "🎉", "🔥", "✨", "💯",),
        listOf("💙", "💀", "💋", "💔", "🎁", "💥", "✅", "❗",)
    )

    private lateinit var keyboardView:View

    override fun onCreateInputView(): View {
        keyboardView = imeContext.layoutInflater.inflate(R.layout.keyboard_main_symbols, null)
        populatePage(pages[currentPageIndex], currentPageIndex+1, pages.size)
        return keyboardView
    }

    override fun onStartInputView(editorInfo: EditorInfo?, restarting: Boolean) {}

    override fun onFinishInputView(finishingInput: Boolean) {}

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean = false

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        val virtualKey = KEYCODE_MAPPING.getOrDefault(keyCode, -9999)

        // Handle only if keyboard key or #
        if(virtualKey<0 && virtualKey!=SPECIAL_HW_KEY.HASH.value)
            return false

        // Handle the 0 Key - switch page
        if(virtualKey == 0){
            currentPageIndex = (currentPageIndex+1) % pages.size
            populatePage(pages[currentPageIndex], currentPageIndex+1, pages.size)
            return true
        }
        else if(virtualKey==SPECIAL_HW_KEY.HASH.value){
            writeInterface.writeText("del", true)
            return true
        }

        // Handle the number keys => write the emoji
        val symbol = pages[currentPageIndex].getOrElse(virtualKey-1){""}
        writeInterface.writeText(symbol)
        return true
    }

    private fun populatePage(pageItems:List<String>, pageNumber:Int, totalPages:Int){
        // Fill the grid
        val grid = keyboardView.findViewById<GridLayout>(R.id.keyboard_main_gridlayout_symbols)
        grid.children.forEachIndexed { index, view ->
            val symbol = pageItems.getOrElse(index){""}
            val symbolFrame = view as FrameLayout
            val symbolTextV = symbolFrame.children.toList().first() as TextView
            symbolTextV.text = symbol
        }
        // Set the page
        keyboardView.findViewById<TextView>(R.id.keyboard_main_textview_page).text = "$pageNumber/$totalPages"
        keyboardView.requestLayout()
    }

    override fun onDestroy() {}

}