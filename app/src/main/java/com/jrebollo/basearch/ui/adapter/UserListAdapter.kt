package com.jrebollo.basearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jrebollo.basearch.databinding.ItemUserBinding
import com.jrebollo.domain.entity.User

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private val items: ArrayList<User> = arrayListOf()

    var onItemClick: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(userList: List<User>) {
        items.clear()
        items.addAll(userList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            with(binding) {
                item = user
            }

            itemView.setOnClickListener {
                onItemClick?.invoke(user)
            }
        }
    }
}
