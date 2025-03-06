package com.example.timerlist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.timerlist.item.AutoDeleteItem
import com.example.timerlist.item.BaseItem

object AutoDeleteDiffUtil : DiffUtil.ItemCallback<BaseItem>() {
    override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem == newItem
    }

    // 변경된 payload 값 만 반환, 아무것도 변경되지 않았다면 null 반환(이 경우 아이템이 다시 바인딩됨)
    override fun getChangePayload(oldItem: BaseItem, newItem: BaseItem): Any? {
        return if (oldItem is AutoDeleteItem && newItem is AutoDeleteItem && oldItem.time != newItem.time) {
            newItem.time // 변경된 time 값만 전달
        } else {
            super.getChangePayload(oldItem, newItem)
        }
    }
}