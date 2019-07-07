package ru.skillbranch.devintensive.extensions

fun String.truncate(i: Int = 16): String {
    if (length <= i) return this

//    if (last().toString() == " ") trim()
    return trim().substring(0 until i).trim() + "..."
}

