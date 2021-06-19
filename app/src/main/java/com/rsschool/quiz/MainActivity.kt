package com.rsschool.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.rsschool.quiz.databinding.ActivityMainBinding

/*Bar color -
val window = activity?.mindow
window?.statusBarColor = Color.Red
*/
private const val NUMBER_OF_QUESTIONS = 3
private const val KEY_QUIZ_INSTANCE = "quiz instance"

class MainActivity : AppCompatActivity(), QuizFragment.ClickQuizFragmentButtons {

    private var quiz = Quiz(QuestionsPool.getQuestions(NUMBER_OF_QUESTIONS))
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState != null) {
            quiz = savedInstanceState.getSerializable(KEY_QUIZ_INSTANCE) as Quiz
        }
        openQuizFragment()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_QUIZ_INSTANCE, quiz)
    }

    private fun  openQuizFragment() {
        val question = quiz.getCurrentQuestion()
        val quizFragment = QuizFragment.newInstance(question.question,
                                                    question.variant1,
                                                    question.variant2,
                                                    question.variant3,
                                                    question.variant4,
                                                    question.variant5)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.quiz_fragment_container, quizFragment)
        transaction.commit()
    }

    private fun openSubmitFragment(){
        val submitFragment = SubmitFragment.newInstance(quiz.getResult())
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.quiz_fragment_container, submitFragment)
        transaction.commit()
    }

    override fun clickNextButton(choice: Int) {
        quiz.setAnswer(converterRButtonsIdToAnswer(choice))
        if (quiz.isLastQuestion() && quiz.isQuizFinish()){
            openSubmitFragment()
        } else{
            quiz.moveToNextQuestion()
            refreshFragment()
        }
    }

    override fun clickPreviousButton(choice: Int) {
        quiz.setAnswer(converterRButtonsIdToAnswer(choice))
        quiz.moveToPreviousQuestion()
        refreshFragment()
    }

    private fun refreshFragment(){
        val fragment = supportFragmentManager.findFragmentById(R.id.quiz_fragment_container) as QuizFragment
        val question = quiz.getCurrentQuestion()

        fragment.refreshFragment(
            question.question,
            question.variant1,
            question.variant2,
            question.variant3,
            question.variant4,
            question.variant5,
            converterAnswerToRButtonId(quiz.getAnswer()),
            quiz.isFirstQuestion(),
            quiz.isLastQuestion())

    }

    private fun converterRButtonsIdToAnswer(rButtonId: Int): Int{
        return when(rButtonId){
            R.id.option_one -> 1
            R.id.option_two -> 2
            R.id.option_three -> 3
            R.id.option_four -> 4
            R.id.option_five -> 5
            else -> -1
        }
    }

    private fun converterAnswerToRButtonId(buttonNum: Int): Int {
        return when(buttonNum){
            1 -> R.id.option_one
            2 -> R.id.option_two
            3 -> R.id.option_three
            4 -> R.id.option_four
            5 -> R.id.option_five
            else -> -1
        }
    }
}