package com.example.timerlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.timerlist.databinding.ItemAutoDeleteBinding
import com.example.timerlist.databinding.ItemNameBinding
import com.example.timerlist.item.AutoDelete

class AutoDeleteAdapter : ListAdapter<AutoDelete, RecyclerView.ViewHolder>(AutoDeleteDiffUtil) {

    private inner class DeleteIconWithNameViewHolder(binding: ItemNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.textName
    }

    private inner class AutoDeleteViewHolder(binding: ItemAutoDeleteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.textDeleteName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == 1) {
            val binding = ItemAutoDeleteBinding.inflate(inflater, parent, false)
            AutoDeleteViewHolder(binding)
        } else {
            val binding = ItemNameBinding.inflate(inflater, parent, false)
            DeleteIconWithNameViewHolder(binding)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)

        when (holder) {
            is DeleteIconWithNameViewHolder -> {
                holder.name.text = currentItem.name
            }
            is AutoDeleteViewHolder -> {
                holder.name.text = currentItem.name
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isRunning) 1 else 0
    }
}