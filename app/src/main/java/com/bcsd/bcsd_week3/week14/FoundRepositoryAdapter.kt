package com.bcsd.bcsd_week3.week14

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bcsd.bcsd_week3.databinding.ItemFoundRepoBinding
import com.bcsd.bcsd_week3.databinding.ItemProgressBinding
import com.bcsd.bcsd_week3.week14.FoundRepositoryAdapter.Companion.diffUtil
import com.bcsd.bcsd_week3.week14.model.GithubRepository

class FoundRepositoryAdapter(
    private val onClick: (GithubRepository.Owner) -> Unit,
) : ListAdapter<GithubRepository, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM)
            return FoundRepoViewHolder(
                ItemFoundRepoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else
            return LoadingViewHolder(
                ItemProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) LOADING else ITEM
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is FoundRepoViewHolder)
            holder.bind(getItem(position))
    }

    inner class FoundRepoViewHolder(val binding: ItemFoundRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: GithubRepository) {
            binding.textViewRepoName.text = repo.name
            binding.root.setOnClickListener() {
                onClick(repo.owner)
            }
        }
    }
    private inner class LoadingViewHolder(binding: ItemProgressBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val ITEM = 0
        private const val LOADING = 1

        private val diffUtil = object : DiffUtil.ItemCallback<GithubRepository>() {
            override fun areItemsTheSame(
                oldItem: GithubRepository,
                newItem: GithubRepository,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GithubRepository,
                newItem: GithubRepository,
            ): Boolean {
                return oldItem.htmlUrl == newItem.htmlUrl
            }
        }
    }
}
