package com.rsschool.quiz

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.rsschool.quiz.databinding.ActivityMainBinding

private const val NUMBER_OF_QUESTIONS = 5
private const val KEY_QUIZ_INSTANCE = "quiz instance"

enum class ActualFragment{
    QUIZ, SUBMIT;
}

class MainActivity : AppCompatActivity(), QuizFragment.ClickQuizFragmentButtons, SubmitFragment.ClickSubmitFragmentButtons {

    private var quiz = Quiz(QuestionsPool.getQuestions(NUMBER_OF_QUESTIONS))
    private lateinit var binding: ActivityMainBinding
    private var actualFragment: ActualFragment? = null
    private val themesPool = listOf(R.style.Theme_Quiz_First,
                                    R.style.Theme_Quiz_Second,
                                    R.style.Theme_Quiz_Third,
                                    R.style.Theme_Quiz_Fourth,
                                    R.style.Theme_Quiz_Fifth)
    private val statusBarColor = listOf(R.color.deep_orange_100_dark,
                                        R.color.yellow_100_dark,
                                        R.color.blue_100_dark,
                                        R.color.cyan_100_dark,
                                        R.color.light_green_100_dark)

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

    override fun onBackPressed() {
        when(actualFragment){
            ActualFragment.QUIZ -> {
                if (quiz.isFirstQuestion()){
                    super.onBackPressed()
                } else {
                    clickPreviousButton(findViewById<RadioGroup>(R.id.radio_group).checkedRadioButtonId)
                }
            }
            ActualFragment.SUBMIT -> clickBackButton()
            else -> super.onBackPressed()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun openQuizFragment() {
        setTheme(themesPool[quiz.getQuestionNumber()-1])
        val question = quiz.getCurrentQuestion()

        val quizFragment = QuizFragment.newInstance(question.question,
                                                    question.variant1,
                                                    question.variant2,
                                                    question.variant3,
                                                    question.variant4,
                                                    question.variant5,
                                                    converterAnswerToRButtonId(quiz.getAnswer()),
                                                    quiz.isFirstQuestion(),
                                                    quiz.isLastQuestion(),
                                                    quiz.getQuestionNumber())
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.quiz_fragment_container, quizFragment)
        transaction.commit()

        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.statusBarBackground, typedValue,true)
        window.statusBarColor = ContextCompat.getColor(this, statusBarColor[quiz.getQuestionNumber()-1])
        actualFragment = ActualFragment.QUIZ
    }

    private fun openSubmitFragment(){
        val submitFragment = SubmitFragment.newInstance(quiz.getResult())
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.quiz_fragment_container, submitFragment)
        transaction.commit()
        actualFragment = ActualFragment.SUBMIT
    }

    @SuppressLint("ResourceType")
    override fun clickNextButton(choice: Int) {
        quiz.setAnswer(converterRButtonsIdToAnswer(choice))
        if (quiz.isLastQuestion() && quiz.isQuizFinish()){
            openSubmitFragment()
        } else{
            quiz.moveToNextQuestion()
            openQuizFragment()
        }
    }

    override fun clickPreviousButton(choice: Int) {
        quiz.setAnswer(converterRButtonsIdToAnswer(choice))
        quiz.moveToPreviousQuestion()
        openQuizFragment()
    }

    override fun clickShareButton() {
        val textMessage = quiz.getStringResult()
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textMessage)
            type = "text/plain"
        }
        try {
            startActivity(sendIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "App not found for sharing result", Toast.LENGTH_LONG).show()
        }
    }

    override fun clickBackButton() {
        quiz = Quiz(QuestionsPool.getQuestions(NUMBER_OF_QUESTIONS))
        openQuizFragment()
    }

    override fun clickExitButton() {
        finish()
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
        return when (buttonNum) {
            1 -> R.id.option_one
            2 -> R.id.option_two
            3 -> R.id.option_three
            4 -> R.id.option_four
            5 -> R.id.option_five
            else -> -1
        }
    }

}