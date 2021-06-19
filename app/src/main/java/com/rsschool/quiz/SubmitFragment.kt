package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentSubmitBinding

class SubmitFragment: Fragment() {

    private lateinit var listener: ClickSubmitFragmentButtons
    private var _binding: FragmentSubmitBinding? = null
    private val binding get() = _binding!!

    interface ClickSubmitFragmentButtons{
        fun clickShareButton()
        fun clickBackButton()
        fun clickExitButton()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (context is ClickSubmitFragmentButtons){
            listener = context as ClickSubmitFragmentButtons
        }
        _binding = FragmentSubmitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val res = arguments?.getInt(QUIZ_RESULT, 0)
        binding.quizResult.text = "Your result: $res %"

        binding.share.setOnClickListener {
            //TODO
            Toast.makeText(context, "Share was pressed", Toast.LENGTH_SHORT).show()
        }

        binding.back.setOnClickListener {
            //TODO
            Toast.makeText(context, "Share was pressed", Toast.LENGTH_SHORT).show()
        }

        binding.exit.setOnClickListener {
            //TODO
            Toast.makeText(context, "Share was pressed", Toast.LENGTH_SHORT).show()
        }

    }


    companion object{
        @JvmStatic
        fun newInstance(quizResult: Int): SubmitFragment{
            val fragment = SubmitFragment()
            fragment.arguments = Bundle().apply {
                putInt(QUIZ_RESULT, quizResult)
            }
            return fragment
        }

        private const val QUIZ_RESULT = "your quiz result"
    }
}