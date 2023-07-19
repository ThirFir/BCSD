package com.bcsd.bcsd_week3.week14

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bcsd.bcsd_week3.databinding.ItemFoundUserBinding

class FoundUserAdapter(private val users: MutableList<GithubUser>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FoundUserViewHolder(ItemFoundUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = users[position]
        with(holder as FoundUserViewHolder) {
            binding.textViewUserName.text = user.login
        }
    }

    fun updateList(items: List<GithubUser>?) {
        items?.let {
            val diffCallback = DiffUtilCallback(this.users, items)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            this.users.run {
                clear()
                addAll(items)
                diffResult.dispatchUpdatesTo(this@FoundUserAdapter)
            }
        }
    }

    fun addList(items: List<GithubUser>?) {
        items?.let {
            val diffCallback = DiffUtilCallback(this.users, items)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            this.users.run {
                addAll(items)
                diffResult.dispatchUpdatesTo(this@FoundUserAdapter)
            }
        }
    }

    inner class FoundUserViewHolder(val binding: ItemFoundUserBinding) : RecyclerView.ViewHolder(binding.root)

    inner class DiffUtilCallback(private val oldList: List<Any>, private val newList: List<Any>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return if (oldItem is GithubUser && newItem is GithubUser) {
                oldItem.login == newItem.login
            } else {
                false
            }
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}


