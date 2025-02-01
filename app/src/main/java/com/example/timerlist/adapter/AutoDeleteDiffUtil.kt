package com.example.timerlist.adapter

import androidx.recyclerview.widget.DiffUtil
import item.AutoDelete

object AutoDeleteDiffUtil: DiffUtil.ItemCallback<AutoDelete>() {
    override fun areItemsTheSame(oldItem: AutoDelete, newItem: AutoDelete): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AutoDelete, newItem: AutoDelete): Boolean {
        return oldItem == newItem
    }
}