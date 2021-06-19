package com.rsschool.quiz

import java.io.Serializable


class Quiz (private val questionArray: ArrayList<Question>): Serializable{

    private val quizQuestions = Array(questionArray.size){
            i -> QuestionStatus(questionArray[i], -1)
    }
    private var currentQuestion = 0




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

    fun isTrueAnswer(questionNum: Int): Boolean{
        return quizQuestions[questionNum].userAnswer == quizQuestions[questionNum].question.answerNum
    }

    fun isQuizFinish(): Boolean{
        for (question in quizQuestions){
            if (question.userAnswer == -1){
                return false
            }
        }
        return true
    }




    private data class QuestionStatus(val question: Question, var userAnswer: Int){}
}