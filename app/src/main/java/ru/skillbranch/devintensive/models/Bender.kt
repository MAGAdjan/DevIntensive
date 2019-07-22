package ru.skillbranch.devintensive.models

import android.util.Log
import androidx.core.text.isDigitsOnly

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

//    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
//        Log.d("log bleat", "$question")
//        if (question == Question.NAME && !Character.isUpperCase(answer[0])) {
//            Log.d("log bleat", "da zashli v etu xuinu")
//            Log.d("log bleat", answer)
//            Log.d("log bleat", "${question == Question.NAME}")
//            Log.d("log bleat", "${Character.isUpperCase(answer[0])}")
//            return "Имя должно начинаться с заглавной буквы\n${question.question}" to status.color
//        } else if (question == Question.PROFESSION && Character.isUpperCase(answer[0])) {
//            return "Профессия должна начинаться со строчной буквы\n${question.question}" to status.color
//        } else if (question == Question.MATERIAL && answer.matches(Regex(".*\\d.*"))) {
//            return "Материал не должен содержать цифр\n${question.question}" to status.color
//        } else if (question == Question.BDAY && !answer.isDigitsOnly()) {
//            return "Год моего рождения должен содержать только цифры\n${question.question}" to status.color
//        } else if (question == Question.SERIAL && !answer.isDigitsOnly() && (answer.length != 7)) {
//            print(answer.isDigitsOnly())
//            print(answer.length)
//            return "Серийный номер содержит только цифры, и их 7\n${question.question}" to status.color
//        }
//        return if (question.answers.contains(answer.toLowerCase())) {
//            question = question.nextQuestion()
//            "Отлично - ты справился\n${question.question}" to status.color
//        } else {
//            if (status == Status.CRITICAL) {
//                status = Status.NORMAL
//                question = Question.NAME
//                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
//
//            } else {
//                status = status.nextStatus()
//                Log.d("log bleat", answer.length.toString())
//                Log.d("log bleat", answer.isDigitsOnly().toString())
//                "Это неправильный ответ\n${question.question}" to status.color
//            }
//        }
//    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        val valid = validate(answer, question)
        if (valid.isNotEmpty()) {
            return "$valid\n${question.question}" to status.color
        }

        if (question.answers.contains(answer.toLowerCase())) {
            question = question.nextQuestion()

            return "Отлично - ты справился\n${question.question}" to status.color
        } else {

            if (status == Status.CRITICAL) {
                status = Status.NORMAL
                question = Question.NAME

                return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }

            status = status.nextStatus()
            return "Это неправильный ответ\n${question.question}"  to status.color
        }

    }

    fun validate(answer: String, question: Question): String {
        return when (question) {
            Question.NAME ->  if (answer.isNotEmpty() && Character.isUpperCase(answer[0])) "" else "Имя должно начинаться с заглавной буквы"
            Question.PROFESSION -> if (answer.isNotEmpty() && !Character.isUpperCase(answer[0])) "" else "Профессия должна начинаться со строчной буквы"
            Question.MATERIAL -> if (!answer.matches(Regex(".*\\d.*"))) "" else "Материал не должен содержать цифр"
            Question.BDAY -> if (answer.isDigitsOnly()) "" else "Год моего рождения должен содержать только цифры"
            Question.SERIAL -> if (answer.isDigitsOnly() && (answer.length == 7)) "" else "Серийный номер содержит только цифры, и их 7"
            Question.IDLE -> ""
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }
}