package com.bcsd.bcsd_week3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import com.bcsd.bcsd_week3.databinding.FragmentWeek5MainBinding


class Week5MainFragment : Fragment() {
    private lateinit var binding: FragmentWeek5MainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeek5MainBinding.inflate(inflater, container, false)

        var count = 0

        binding.buttonToast.setOnClickListener {
            //Toast.makeText(this@Week4Activity, "Toast : ${binding.textCountNumber.text}", Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(requireContext()).setMessage(count.toString())
                .setPositiveButton("OK"){
                        _, _ -> count = 0; binding.textCountNumber.text = "0"
                }
                .setNegativeButton("Cancel", null)
                .setNeutralButton("Neutral") {
                        _, _ -> Toast.makeText(requireContext(), "NEUTRAL", Toast.LENGTH_SHORT).show()
                }.show()
        }
        binding.buttonCount.setOnClickListener {
            ++count
            binding.textCountNumber.text = count.toString()
        }

        binding.buttonRandom.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.layout_week5_activity, Week5Fragment().apply {
                arguments = bundleOf("count" to count)
            }, "tag").addToBackStack("tag").commit()
        }

        return binding.root
    }

}