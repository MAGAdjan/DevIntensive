package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

const val SECONDS = 1000L
const val MINUTES = SECONDS * 60
const val HOUR = MINUTES * 60
const val DAY = HOUR * 24

fun Date.humanizeDiff(date: Date = Date()): String {
    val isPast = time < date.time
    val diff = Math.abs(time - date.time)
    val diffDays = (diff / DAY.absoluteValue).toInt()
    val diffHours = (diff / HOUR.absoluteValue).toInt()
    val diffMinutes = (diff / MINUTES.absoluteValue).toInt()
    val diffSeconds = (diff / SECONDS.absoluteValue).toInt()
    return if (isPast) when {
        diffDays > 360 -> "более года назад"
        diffHours > 26 -> {
            when {
                diffDays.toString().last().toString().toInt() in 2..4 && diffDays !in 12..14 -> "$diffDays дня назад"
                else -> "$diffDays дней назад"
            }
        }
        diffHours in 23..26 -> "день назад"
        diffHours <= 22 && diffMinutes > 75 -> {
            when {
                diffHours.toString().last().toString().toInt() in 2..4 && diffHours !in 12..14  -> "$diffHours часа назад"
                else -> "$diffHours часов назад"
            }
        }
        diffMinutes in 46..75 -> "час назад"
        diffMinutes <= 45 && diffSeconds > 75 -> {
            when {
                diffMinutes.toString().last().toString().toInt() in 2..4 && diffMinutes !in 12..14 -> "$diffMinutes минуты назад"
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
                diffDays.toString().last().toString().toInt() in 2..4 && diffDays !in 12..14 -> "через $diffDays дня"
                else -> "через $diffDays дней"
            }
        }
        diffHours in 23..26 -> "через день"
        diffHours <= 22 && diffMinutes > 75 -> {
            when {
                diffHours.toString().last().toString().toInt() in 2..4 && diffHours !in 12..14  -> "через $diffHours часа"
                else -> "через $diffHours часов"
            }
        }
        diffMinutes in 46..75 -> "через час"
        diffMinutes <= 45 && diffSeconds > 75 -> {
            when {
                diffMinutes.toString().last().toString().toInt() in 2..4 && diffMinutes !in 12..14 -> "через $diffMinutes минуты"
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

enum class TimeUnits(val u: String, val y: String, val m: String) {
    SECOND("секунду", "секунды", "секунд"),
    MINUTE("минуту", "минуты", "минут"),
    HOUR("час", "часа", "часов"),
    DAY("день", "дня", "дней");

    fun plural(value: Int): String {
        return when {
            value.toString().last().toString().toInt() == 1 && value.toString().last().toString().toInt()!in 12..14 -> "$value $u"
            value.toString().last().toString().toInt() in 2..4 && value.toString().last().toString().toInt()!in 12..14 -> "$value $y"
            else -> "$value $m"
        }
    }
}