package com.example.timerlist

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<List<T>>.updateList(action: (MutableList<T>) -> Unit) {
    val updatedList = value?.toMutableList() ?: mutableListOf()
    action(updatedList)
    value = updatedList
}