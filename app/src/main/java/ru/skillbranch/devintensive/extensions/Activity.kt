package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import ru.skillbranch.devintensive.MainActivity
import android.R.attr.bottom
import android.graphics.Rect
import android.opengl.ETC1.getHeight
import android.view.ViewTreeObserver
import kotlinx.android.synthetic.main.activity_main.*


fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view!!.windowToken, 0)
}

fun Activity.isKeyboardOpen(): Boolean {
    var screenHeight = 0
    var keypadHeight = 0
    mainLay.viewTreeObserver.addOnGlobalLayoutListener {
        val r = Rect()
        mainLay.getWindowVisibleDisplayFrame(r)
        screenHeight = mainLay.rootView.height
        keypadHeight = screenHeight - r.bottom
    }
    return keypadHeight > screenHeight * 0.15
}

fun Activity.isKeyboardClosed(): Boolean {
    var screenHeight = 0
    var keypadHeight = 0
    mainLay.viewTreeObserver.addOnGlobalLayoutListener {
        val r = Rect()
        mainLay.getWindowVisibleDisplayFrame(r)
        screenHeight = mainLay.rootView.height
        keypadHeight = screenHeight - r.bottom
    }
    return keypadHeight < screenHeight * 0.15
}