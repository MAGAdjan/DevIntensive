package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECONDS = 1000L
const val MINUTES = SECONDS * 60
const val HOUR = MINUTES * 60
const val DAY = HOUR * 24

fun Date.humanizeDiff(date: Date = Date()): String {
    val isPast = time < date.time
    val diff = Math.abs(time - date.time)
    val diffDays = (diff / DAY).toInt()
    val diffHours = (diff / HOUR).toInt()
    val diffMinutes = (diff / MINUTES).toInt()
    val diffSeconds = (diff / SECONDS).toInt()
    return if (isPast) when {
        diffDays > 360 -> "более года назад"
        diffHours > 26 -> {
            when {
                diffDays.toString().last().toInt() in 2..4 && diffDays !in 12..14 -> "$diffDays дня назад"
                else -> "$diffDays дней назад"
            }
        }
        diffHours in 23..26 -> "день назад"
        diffHours <= 22 && diffMinutes > 75 -> {
            when {
                diffHours.toString().last().toInt() in 2..4 && diffHours !in 12..14  -> "$diffHours часа назад"
                else -> "$diffHours часов назад"
            }
        }
        diffMinutes in 46..75 -> "час назад"
        diffMinutes <= 45 && diffSeconds > 75 -> {
            when {
                diffMinutes.toString().last().toInt() in 2..4 && diffMinutes !in 12..14 -> "$diffMinutes минуты назад"
                else -> "$diffMinutes минут назад"
            }
        }
        diffSeconds in 46..75 -> "минуту назад"
        diffSeconds in 2..45 -> "несколько секунд назад"
        else -> "только что"
    } else when {
        diffDays > 360 -> "более чем через год"
        diffHours > 26 -> {
            when {
                diffDays.toString().last().toInt() in 2..4 && diffDays !in 12..14 -> "через $diffDays дня"
                else -> "через $diffDays дней"
            }
        }
        diffHours in 23..26 -> "через день"
        diffHours <= 22 && diffMinutes > 75 -> {
            when {
                diffHours.toString().last().toInt() in 2..4 && diffHours !in 12..14  -> "через $diffHours часа"
                else -> "через $diffHours часов"
            }
        }
        diffMinutes in 46..75 -> "через час"
        diffMinutes <= 45 && diffSeconds > 75 -> {
            when {
                diffMinutes.toString().last().toInt() in 2..4 && diffMinutes !in 12..14 -> "через $diffMinutes минуты"
                else -> "через $diffMinutes минут"
            }
        }
        diffSeconds in 46..75 -> "через минуту"
        diffSeconds in 2..45 -> "через несколько секунд"
        else -> "только что"
    }

}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when(units) {
        TimeUnits.SECOND -> value * SECONDS
        TimeUnits.MINUTE -> value * MINUTES
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}