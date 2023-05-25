package com.bcsd.bcsd_week3.week7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bcsd.bcsd_week3.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pos = arguments?.getChar(CHARACTER) ?: 'A'
        if(pos == '0' + 10)
            binding.textPage.text = "10"
        else
            binding.textPage.text = pos.toString()
    }

    companion object {

        val CHARACTER = "character"
        fun newInstance(pos: Char) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putChar(CHARACTER, pos)
                }
            }
    }
}