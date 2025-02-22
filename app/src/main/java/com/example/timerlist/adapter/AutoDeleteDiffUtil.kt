package com.example.timerlist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.timerlist.item.AutoDeleteItem
import com.example.timerlist.item.BaseItem
import com.example.timerlist.item.OrdinaryItem

object AutoDeleteDiffUtil: DiffUtil.ItemCallback<BaseItem>() {
    override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return when {
            oldItem is OrdinaryItem && newItem is OrdinaryItem -> oldItem as OrdinaryItem == newItem as OrdinaryItem
            oldItem is AutoDeleteItem && newItem is AutoDeleteItem -> oldItem as AutoDeleteItem == newItem as AutoDeleteItem
            else -> false
        }
    }
}