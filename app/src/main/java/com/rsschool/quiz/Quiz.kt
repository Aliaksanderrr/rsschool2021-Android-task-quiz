package com.rsschool.quiz

import java.io.Serializable

class Quiz (private val questionArray: ArrayList<Question>): Serializable{


    private val quizQuestions = Array(questionArray.size){
            i -> QuestionStatus(questionArray[i], -1)
    }
    private var currentQuestion = 0

    fun getQuestionNumber(): Int{
        return currentQuestion + 1
    }

    fun getCurrentQuestion(): Question{
        return quizQuestions[currentQuestion].question
    }

    fun getAnswer(): Int{
        return quizQuestions[currentQuestion].userAnswer
    }

    fun setAnswer(userAnswer: Int){
        quizQuestions[currentQuestion].userAnswer = userAnswer

    }

    fun moveToNextQuestion(){
        currentQuestion = ++currentQuestion%quizQuestions.size
    }

    fun moveToPreviousQuestion(){
        if (currentQuestion > 0){
            --currentQuestion
        }
    }
    fun isFirstQuestion(): Boolean{
        return currentQuestion == 0
    }

    fun isLastQuestion(): Boolean{
        return currentQuestion == quizQuestions.lastIndex
    }

    fun isQuizFinish(): Boolean{
        for (question in quizQuestions){
            if (question.userAnswer == -1){
                return false
            }
        }
        return true
    }

    fun getResult(): Int{
        var corractAnswers = 0
        for (question in quizQuestions){
            if (question.userAnswer == question.question.answerNum){
                corractAnswers++
            }
        }
        return  (corractAnswers*100) / quizQuestions.size
    }

    fun getStringResult(): String{
        var result = "Your result: ${getResult()} %\n"
        var counter = 1
        for (question in quizQuestions){
            result += "\n$counter) ${question.question.question}"
            val ans = when (question.userAnswer){
                1 -> question.question.variant1
                2 -> question.question.variant2
                3 -> question.question.variant3
                4 -> question.question.variant4
                5 -> question.question.variant5
                else -> "no answer"
            }
            result += "\nYour answer:$ans\n"
            counter++
        }
        return result
    }

    private data class QuestionStatus(val question: Question, var userAnswer: Int): Serializable
}