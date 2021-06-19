package com.rsschool.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding


class QuizFragment : Fragment() {

    private lateinit var listener: ClickQuizFragmentButtons
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    interface ClickQuizFragmentButtons{
        fun clickNextButton(choice: Int)
        fun clickPreviousButton(choice: Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (context is ClickQuizFragmentButtons){
            listener = context as ClickQuizFragmentButtons
        }
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.question.text = arguments?.getString(ARG_QUESTION)
        binding.optionOne.text = arguments?.getString(OPTION_ONE)
        binding.optionTwo.text = arguments?.getString(OPTION_TWO)
        binding.optionThree.text = arguments?.getString(OPTION_THREE)
        binding.optionFour.text = arguments?.getString(OPTION_FOUR)
        binding.optionFive.text = arguments?.getString(OPTION_FIVE)

        binding.radioGroup.setOnCheckedChangeListener{ _, idButton ->
            if(idButton != -1){
                activateButton(binding.nextButton)
            }
        }

        binding.previousButton.setOnClickListener {
            listener.clickPreviousButton(binding.radioGroup.checkedRadioButtonId)
        }

        binding.nextButton.setOnClickListener {
            listener.clickNextButton(binding.radioGroup.checkedRadioButtonId)
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun refreshFragment(question: String,
                        option_one: String,
                        option_two: String,
                        option_three: String,
                        option_four: String,
                        option_five: String,
                        userChoice: Int,
                        firstQuestion: Boolean,
                        lastQuestion: Boolean){
        binding.question.text = question
        binding.radioGroup.check(userChoice)
        binding.optionOne.text = option_one
        binding.optionTwo.text = option_two
        binding.optionThree.text = option_three
        binding.optionFour.text = option_four
        binding.optionFive.text = option_five
        if(!firstQuestion) {
            activateButton(binding.previousButton)
        } else {
            deActivateButton(binding.previousButton)
        }
        if(userChoice != -1){
            activateButton(binding.nextButton)
        } else{
            deActivateButton(binding.nextButton)
        }
        if (lastQuestion){
            binding.nextButton.text = "SUBMIT"
        } else {
            binding.nextButton.text = "NEXT"
        }
    }


    /////////////////////////////////////////////////////////////
    //////ACTIVATED
    private fun activateButton(button: Button){
        button.isEnabled = true
        button.isEnabled = true
    }

    private fun deActivateButton(button: Button){
        button.isActivated = false
        button.isEnabled = false
    }
    ///////////////////////////////////////////////////////////

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
