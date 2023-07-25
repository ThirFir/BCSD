package com.bcsd.bcsd_week3.week14

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bcsd.bcsd_week3.databinding.ItemProgressBinding
import com.bcsd.bcsd_week3.databinding.ItemUserReposBinding
import com.bcsd.bcsd_week3.week14.model.GithubRepository

class UserRepositoryAdapter(
    private val onItemClicked: (GithubRepository) -> Unit,
) : ListAdapter<GithubRepository, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM)
            UserRepositoryViewHolder(
                ItemUserReposBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            ) else LoadingViewHolder(
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
        if (holder is UserRepositoryViewHolder)
            holder.bind(getItem(position))
    }

    private inner class LoadingViewHolder(binding: ItemProgressBinding) :
        RecyclerView.ViewHolder(binding.root)


    inner class UserRepositoryViewHolder(val binding: ItemUserReposBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repository: GithubRepository) {
            binding.apply {
                textViewRepositoryName.text = repository.name
                textViewRepositoryDescription.text = repository.description
                textViewStars.text = repository.stargazersCount.toString()
                textViewForks.text = repository.forksCount.toString()
                root.setOnClickListener {
                    onItemClicked(repository)
                }
            }
        }
    }

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