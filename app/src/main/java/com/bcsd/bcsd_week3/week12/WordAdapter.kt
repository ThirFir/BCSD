package com.bcsd.bcsd_week3.week12

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bcsd.bcsd_week3.databinding.ItemWordBinding
import com.bumptech.glide.Glide

class WordAdapter(private var wordList: List<Word>, private val onItemSelected: (Word) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class WordViewHolder(val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        WordViewHolder(ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = wordList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val word = wordList[position]
        with(holder as WordViewHolder) {
            binding.textViewWord.text = word.name
            binding.textViewMeaning.text = word.meaning
            Glide.with(binding.imageViewWord.context).load(word.imageUri).into(binding.imageViewWord)
            binding.root.setOnClickListener { onItemSelected(word) }
        }
    }

    fun setWordList(newWordList: List<Word>) {
        wordList = newWordList
        notifyDataSetChanged()
    }

}