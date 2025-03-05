package com.dave.github.view.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dave.github.databinding.ItemUserBinding
import com.dave.github.model.User

class UserAdapter : ListAdapter<User, UserAdapter.ViewHolder>(Companion) {

    class ViewHolder(val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.equals(newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.user = getItem(position)

        Glide.with(holder.itemView).load(getItem(position).avatarUrl).into(holder.binding.ivProfile)

    }
}