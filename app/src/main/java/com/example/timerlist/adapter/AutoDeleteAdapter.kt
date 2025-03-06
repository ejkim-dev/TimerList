package com.example.timerlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.timerlist.databinding.ItemAutoDeleteBinding
import com.example.timerlist.databinding.ItemNameBinding
import com.example.timerlist.item.AutoDeleteItem
import com.example.timerlist.item.BaseItem
import com.example.timerlist.item.OrdinaryItem

class AutoDeleteAdapter(
    private val onItemClick: (BaseItem) -> Unit
) : ListAdapter<BaseItem, RecyclerView.ViewHolder>(AutoDeleteDiffUtil) {

    class DeleteIconWithNameViewHolder(
        private val binding: ItemNameBinding,
        private val onItemClick: (BaseItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OrdinaryItem) {
            binding.textName.text = item.name
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }

    class AutoDeleteViewHolder(
        private val binding: ItemAutoDeleteBinding,
        private val onItemClick: (BaseItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AutoDeleteItem) {
            binding.textDeleteName.text = item.name
            binding.textRemainingSecondsValue.text = "${item.time}"
            binding.root.setOnClickListener { onItemClick(item) }
        }

        fun updateTime(time: Int) {
            binding.textRemainingSecondsValue.text = "$time"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ViewType.entries[viewType]) {
            ViewType.ORDINARY -> {
                val binding = ItemNameBinding.inflate(inflater, parent, false)
                DeleteIconWithNameViewHolder(binding, onItemClick)
            }

            ViewType.AUTO_DELETE -> {
                val binding = ItemAutoDeleteBinding.inflate(inflater, parent, false)
                AutoDeleteViewHolder(binding, onItemClick)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is DeleteIconWithNameViewHolder -> holder.bind(item as OrdinaryItem)
            is AutoDeleteViewHolder -> holder.bind(item as AutoDeleteItem)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any> // 변경된 데이터만 업데이트
    ) {
        if (payloads.isNotEmpty() && holder is AutoDeleteViewHolder) {
            val time = payloads[0] as Int
            holder.updateTime(time) // time 값만 변경
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is AutoDeleteItem -> ViewType.AUTO_DELETE.ordinal
            is OrdinaryItem -> ViewType.ORDINARY.ordinal
        }
    }

    enum class ViewType { ORDINARY, AUTO_DELETE }
}