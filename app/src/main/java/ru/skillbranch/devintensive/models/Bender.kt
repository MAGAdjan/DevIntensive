package ru.skillbranch.devintensive.models

import android.util.Log

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when(question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        return if (question.answers.contains(answer.toLowerCase())) {
            Log.d("log bleat", "$question")
            if (question == Question.NAME && !Character.isUpperCase(answer[0])) {
                Log.d("log bleat", "da zashli v etu xuinu")
                Log.d("log bleat", answer)
                Log.d("log bleat", "${question == Question.NAME}")
                Log.d("log bleat", "${Character.isUpperCase(answer[0])}")
                return "Имя должно начинаться с заглавной буквы\n${question.question}" to status.color
            } else if (question == Question.PROFESSION && Character.isUpperCase(answer[0])) {
                return "Профессия должна начинаться со строчной буквы\n${question.question}" to status.color
            } else if (question == Question.MATERIAL && answer.contains("[0-9]")) {
                return "Материал не должен содержать цифр\n${question.question}" to status.color
            } else if (question == Question.BDAY && !answer.contains("[A-Za-z]")) {
               return "Год моего рождения должен содержать только цифры\n${question.question}" to status.color
            } else if (question == Question.SERIAL && !answer.contains("[A-Za-z") && answer.length != 7) {
                return "Серийный номер содержит только цифры, и их 7\n${question.question}" to status.color
            }
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        } else {
            if (status == Status.CRITICAL) {
                status = Status.NORMAL
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color

            } else {
                status = status.nextStatus()
                "Это неправильный ответ\n${question.question}" to status.color
            }
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)) ,
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0)) ;

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