package com.rsschool.quiz

data class Question (val question: String,
                     val variant1 : String,
                     val variant2 : String,
                     val variant3 : String,
                     val variant4 : String,
                     val variant5 : String,
                     val answerNum: Int) {
}

class QuestionsPool(){

    companion object{
        private val pool = arrayListOf(
            Question("first", "a","b","c", "d", "i", 1),
            Question("second", "a","23","c", "d", "i", 2),
            Question("fourth", "111","b","c", "d", "i", 4)
        )

        fun getQuestions(num: Int = 3): ArrayList<Question>{
            return pool
        }
    }
}