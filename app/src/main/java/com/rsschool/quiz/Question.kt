package com.rsschool.quiz

import java.io.Serializable

data class Question (val question: String,
                     val variant1 : String,
                     val variant2 : String,
                     val variant3 : String,
                     val variant4 : String,
                     val variant5 : String,
                     val answerNum: Int) : Serializable{

}

class QuestionsPool(): Serializable{

    companion object{
        private val pool = arrayListOf(
            Question("Shoose first", "first","b","c", "d", "e", 1),
            Question("Shoose second", "a","second","c", "d", "e", 2),
            Question("Shoose third", "a","b","third", "d", "e", 3),
            Question("Shoose fourth", "a","b","c", "fourth", "e", 4),
            Question("Shoose fifth", "a","b","c", "d", "fifth", 5)
        )

        fun getQuestions(num: Int = 3): ArrayList<Question>{
            return pool
        }
    }
}