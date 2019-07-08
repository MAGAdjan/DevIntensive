package ru.skillbranch.devintensive.extensions

fun String.truncate(i: Int = 16): String {
    if (length <= i) return this

//    if (last().toString() == " ") trim()
    return trim().substring(0 until i).trim() + "..."
}

fun String.stripHtml() = replace("&(?:[a-z\\d]+|#\\d+|#x[a-f\\d]+);".toRegex(), "")
    .replace("<(.|\\n)*?>".toRegex(), "")
    .replace("[ ]+".toRegex(), " ")