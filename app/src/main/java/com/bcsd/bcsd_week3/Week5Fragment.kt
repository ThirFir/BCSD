package com.bcsd.bcsd_week3

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bcsd.bcsd_week3.databinding.FragmentWeek5RandomBinding
import kotlin.random.Random

class Week5Fragment: Fragment() {
    private lateinit var binding: FragmentWeek5RandomBinding
    private lateinit var callback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeek5RandomBinding.inflate(inflater, container, false)

        val r = arguments?.getInt("count", 0)!!
        val random = if (r != 0) Random.nextInt(r) else 1
        binding.textRandomNumber.text = random.toString()
        binding.textRandomText.text = "Random Number between 0 and $r"


        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}