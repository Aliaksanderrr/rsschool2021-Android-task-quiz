package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment


class QuizFragment : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var optionOneRButton: RadioButton
    private lateinit var optionTwoRButton: RadioButton
    private lateinit var optionThreeRButton: RadioButton
    private lateinit var optionFourRButton: RadioButton
    private lateinit var optionFiveRButton: RadioButton
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button

    interface ClickQuizFragmentButtons{
        fun clickNextButton(choice: Int)
        fun clickPreviousButton(choice: Int)
    }

    private lateinit var listener: ClickQuizFragmentButtons

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (context is ClickQuizFragmentButtons){
            listener = context as ClickQuizFragmentButtons
        }
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionText = view.findViewById(R.id.question)
        radioGroup = view.findViewById(R.id.radio_group)
        optionOneRButton = view.findViewById(R.id.option_one)
        optionTwoRButton = view.findViewById(R.id.option_two)
        optionThreeRButton = view.findViewById(R.id.option_three)
        optionFourRButton = view.findViewById(R.id.option_four)
        optionFiveRButton = view.findViewById(R.id.option_five)
        previousButton = view.findViewById(R.id.previous_button)
        nextButton = view.findViewById(R.id.next_button)

        questionText.text = arguments?.getString(ARG_QUESTION)
        optionOneRButton.text = arguments?.getString(OPTION_ONE)
        optionTwoRButton.text = arguments?.getString(OPTION_TWO)
        optionThreeRButton.text = arguments?.getString(OPTION_THREE)
        optionFourRButton.text = arguments?.getString(OPTION_FOUR)
        optionFiveRButton.text = arguments?.getString(OPTION_FIVE)

//        radioGroup.setOnCheckedChangeListener { arg0, selectedId ->
//            var selectedId = selectedId
//            selectedId = genderselected.getCheckedRadioButtonId()
//            val genderchoosed = findViewById(selectedId) as RadioButton
//            val gender = genderchoosed.text.toString()
//        }


        previousButton.setOnClickListener {
            listener.clickPreviousButton(radioGroup.checkedRadioButtonId)
        }

        nextButton.setOnClickListener {
            listener.clickNextButton(radioGroup.checkedRadioButtonId)
        }

    }

    fun refreshFragment(question: String, option_one: String, option_two: String, option_three: String, option_four: String, option_five: String, userChoice: Int){
        questionText.text = question
        radioGroup.check(userChoice)
        optionOneRButton.text = option_one
        optionTwoRButton.text = option_two
        optionThreeRButton.text = option_three
        optionFourRButton.text = option_four
        optionFiveRButton.text = option_five
    }



    companion object {
        @JvmStatic
        fun newInstance(question: String, option_one: String, option_two: String, option_three: String, option_four: String, option_five: String): QuizFragment{
           val fragment = QuizFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_QUESTION, question)
                putString(OPTION_ONE, option_one)
                putString(OPTION_TWO, option_two)
                putString(OPTION_THREE, option_three)
                putString(OPTION_FOUR, option_four)
                putString(OPTION_FIVE, option_five)
            }
            return fragment
        }

        private const val ARG_QUESTION: String = "Question"
        private const val OPTION_ONE: String = "option_one"
        private const val OPTION_TWO: String = "option_two"
        private const val OPTION_THREE: String = "option_three"
        private const val OPTION_FOUR: String = "option_four"
        private const val OPTION_FIVE: String = "option_five"
    }

}