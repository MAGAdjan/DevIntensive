package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")

        if (fullName.isNullOrBlank()) {
            return Pair(null, null)
        }
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return Pair(firstName, lastName)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstInitial = firstName?.trim()?.firstOrNull()?.toUpperCase()
        val secondInitial = lastName?.trim()?.firstOrNull()?.toUpperCase()
        return when {
            firstInitial == null && secondInitial == null -> null
            firstInitial == null -> "$secondInitial"
            secondInitial == null -> "$firstInitial"
            else -> "$firstInitial$secondInitial"
        }
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val map = mapOf("а" to "a",

            "б" to "b",

            "в" to "v",

            "г" to "g",

            "д" to "d",

            "е" to "e",

            "ё" to "e",

            "ж" to "zh",

            "з" to "z",

            "и" to "i",

            "й" to "i",

            "к" to "k",

            "л" to "l",

            "м" to "m",

            "н" to "n",

            "о" to "o",

            "п" to "p",

            "р" to "r",

            "с" to "s",

            "т" to "t",

            "у" to "u",

            "ф" to "f",

            "х" to "h",

            "ц" to "c",

            "ч" to "ch",

            "ш" to "sh",

            "щ" to "sh'",

            "ъ" to "",

            "ы" to "i",

            "ь" to "",

            "э" to "e",

            "ю" to "yu",

            "я" to "ya")

        var res = ""
        for (c in payload) {
            res += when {
                c.toString().isBlank() -> divider
                c in 'A'..'z' -> c.toString()
                map[c.toString()] == null -> map[c.toString().toLowerCase()]?.toUpperCase()
                else -> map[c.toString().toLowerCase()]
            }
        }
        return res
    }
}