package com.example.timerlist.item

sealed interface BaseItem {
    val id: Int
    val name: String
}

data class OrdinaryItem(
    override val id: Int,
    override val name: String
) : BaseItem

data class AutoDeleteItem(
    override val id: Int,
    override val name: String,
    val time: Int = 0
): BaseItem

