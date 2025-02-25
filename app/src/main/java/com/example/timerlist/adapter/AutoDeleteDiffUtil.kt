package com.example.timerlist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.timerlist.item.BaseItem

object AutoDeleteDiffUtil: DiffUtil.ItemCallback<BaseItem>() {
    override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem == newItem
    }
}