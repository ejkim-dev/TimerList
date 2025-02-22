package com.example.timerlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.timerlist.databinding.ItemAutoDeleteBinding
import com.example.timerlist.databinding.ItemNameBinding
import com.example.timerlist.item.AutoDeleteItem
import com.example.timerlist.item.BaseItem

class AutoDeleteAdapter(private val clickListener: OnItemClickListener) : ListAdapter<BaseItem, RecyclerView.ViewHolder>(AutoDeleteDiffUtil) {

    private inner class DeleteIconWithNameViewHolder(
        binding: ItemNameBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.textName

        init {
            binding.root.setOnClickListener {
                val currentPosition = absoluteAdapterPosition
                if (currentPosition != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(getItem(currentPosition), isOrdinaryItem = true)
                }
            }
        }

    }

    private inner class AutoDeleteViewHolder(binding: ItemAutoDeleteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.textDeleteName

        init {
            binding.root.setOnClickListener {
                val currentPosition = absoluteAdapterPosition
                if (currentPosition != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(getItem(currentPosition), isOrdinaryItem = false)
                }
            }
        }
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
        return if (getItem(position) is AutoDeleteItem) 1 else 0
    }

    interface OnItemClickListener {
        fun onItemClick(item: BaseItem, isOrdinaryItem: Boolean)
    }
}