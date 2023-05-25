package com.bcsd.bcsd_week3.week7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bcsd.bcsd_week3.databinding.FragmentFirstABinding


class FirstAFragment : Fragment() {
    private lateinit var binding: FragmentFirstABinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstABinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val color = arguments?.getInt(COLOR) ?: 0
        binding.root.setBackgroundColor(requireActivity().getColor(color))
    }

    companion object {
        val COLOR = "color"
        fun newInstance(color: Int): FirstAFragment {
            val fragment = FirstAFragment()
            val args = Bundle().apply {
                putInt(COLOR, color)
            }
            fragment.arguments = args
            return fragment
        }
    }
}