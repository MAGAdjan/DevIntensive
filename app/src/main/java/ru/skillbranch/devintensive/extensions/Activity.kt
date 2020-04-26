package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.graphics.Rect
import android.view.ViewGroup


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

//fun Activity.isKeyboardOpen(): Boolean {
//    var screenHeight = 0
//    var keypadHeight = 0
//    val layout = mainLay
//    val dm = layout.resources.displayMetrics
//    layout.viewTreeObserver.addOnGlobalLayoutListener {
//        val r = Rect()
//        layout.getWindowVisibleDisplayFrame(r)
//        screenHeight = mainLay.rootView.height
//        keypadHeight = screenHeight - r.bottom
//    }
//    return keypadHeight > screenHeight * 0.128
//}
//
//fun Activity.isKeyboardClosed(): Boolean {
//    var screenHeight = 0
//    var keypadHeight = 0
//    val layout = mainLay
//    val dm = layout.resources.displayMetrics
//    layout.viewTreeObserver.addOnGlobalLayoutListener {
//        val r = Rect()
//        layout.getWindowVisibleDisplayFrame(r)
//        screenHeight = mainLay.rootView.height
//        keypadHeight = screenHeight - r.bottom
//    }
//    return keypadHeight < screenHeight * 0.128
//}

fun Activity.isKeyboardShown(): Boolean {
    val SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128

    val rootView = findViewById<ViewGroup>(android.R.id.content)
    val r = Rect()

    rootView.getWindowVisibleDisplayFrame(r)
    val dm = rootView.resources.displayMetrics

    val heightDiff = rootView.bottom - r.bottom

    val isKeyboardShown = heightDiff > SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD * dm.density;
    return isKeyboardShown
}


fun Activity.isKeyboardOpen() = isKeyboardShown()
fun Activity.isKeyboardClosed() = !isKeyboardShown()
