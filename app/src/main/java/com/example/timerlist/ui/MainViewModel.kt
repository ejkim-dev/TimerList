package com.example.timerlist.ui

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timerlist.item.AutoDeleteItem
import com.example.timerlist.item.BaseItem
import com.example.timerlist.item.OrdinaryItem

class MainViewModel : ViewModel() {

    val itemList: LiveData<List<OrdinaryItem>>
        get() = _itemList
    private val _itemList: MutableLiveData<List<OrdinaryItem>> = MutableLiveData(listOf())

    val autoDeleteList: LiveData<List<AutoDeleteItem>>
        get() = _autoDeleteList
    private val _autoDeleteList: MutableLiveData<List<AutoDeleteItem>> = MutableLiveData(listOf())

    private val countdownTimers = mutableMapOf<Int, CountDownTimer>() // 아이템 ID별 타이머 저장

    init {
        initializeData()
    }

    fun addOrdinaryItem(item: BaseItem) {
        _itemList.value?.let {
            val newItems = it.toMutableList()
            val ordinaryItem = when (item) {
                is OrdinaryItem -> item as OrdinaryItem
                is AutoDeleteItem -> OrdinaryItem(item.id, item.name)
                else -> throw IllegalArgumentException("Unknown item type")
            }
            newItems.add(ordinaryItem)
            _itemList.postValue(newItems)
        }
    }

    fun deleteItemList(item: OrdinaryItem) {
        _itemList.value?.let {
            val newItems = it.toMutableList()
            newItems.remove(item)
            _itemList.postValue(newItems)
        }
    }

    fun addAutoDeleteItem(item: BaseItem) {
        _autoDeleteList.value?.let {
            val newItems = it.toMutableList()
            val autoDeleteItem = when (item) {
                is AutoDeleteItem -> item as AutoDeleteItem
                is OrdinaryItem -> AutoDeleteItem(item.id, item.name)
                else -> throw IllegalArgumentException("Unknown item type")
            }

            countdownDelete(autoDeleteItem)
            newItems.add(autoDeleteItem)
            _autoDeleteList.postValue(newItems)
        }
    }

    fun deleteAutoDeleteItem(item: AutoDeleteItem) {
        _autoDeleteList.value?.let {
            val newItems = it.toMutableList()
            newItems.remove(item)
            cancelCountdown(item)
            _autoDeleteList.postValue(newItems)
        }
    }

    private fun countdownDelete(item: AutoDeleteItem) {
        cancelCountdown(item) // 중복 타이머 방지

        val timer = object : CountDownTimer((item.time * 1000).toLong(), 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("#####", "onTick: ${item.name} | $millisUntilFinished")
            }

            override fun onFinish() {
                _autoDeleteList.value?.let {
                    if (it.contains(item)) { // 중복 방지
                        deleteAutoDeleteItem(item)
                        addOrdinaryItem(item)
                    }
                }
                countdownTimers.remove(item.id) // onFinish 호출 시 타이머 삭제
            }
        }

        countdownTimers[item.id] = timer
        timer.start()
    }

    private fun cancelCountdown(item: AutoDeleteItem) {
        countdownTimers[item.id]?.cancel()
        countdownTimers.remove(item.id)
    }

    private fun initializeData() = _itemList.postValue(
        (0..49).map {
            OrdinaryItem(it, randomAlphabet())
        }
    )

    private fun randomAlphabet(): String {
        val allowedChars = ('A'..'Z')
        return allowedChars.random().toString()
    }
}