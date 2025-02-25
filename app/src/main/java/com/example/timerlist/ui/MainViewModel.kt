package com.example.timerlist.ui

import android.os.CountDownTimer
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

            // 중복 아이템 방지
            if (newItems.find { it.id == ordinaryItem.id } != null) return

            newItems.add(ordinaryItem)
            _itemList.value = newItems
        }
    }

    fun deleteItemList(item: OrdinaryItem) {
        _itemList.value?.let {
            val newItems = it.toMutableList()
            newItems.remove(item)
            _itemList.value = newItems
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
            _autoDeleteList.value = newItems
        }
    }

    fun deleteAutoDeleteItem(item: AutoDeleteItem) {
        _autoDeleteList.value?.let {
            val newItems = it.toMutableList()
            newItems.removeAll { it.id == item.id }
            cancelCountdown(item)
            _autoDeleteList.value = newItems
        }
    }

    private fun countdownDelete(item: AutoDeleteItem) {
        cancelCountdown(item) // 중복 타이머 방지

        var currentTime = 10
        val timer = object : CountDownTimer(currentTime * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                _autoDeleteList.value?.let {
                    val newItems = it.toMutableList()
                    val index = newItems.indexOfFirst { it.id == item.id }
                    if (index >= 0) {
                        newItems[index] = AutoDeleteItem(item.id, item.name, currentTime - 1)
                        _autoDeleteList.value = newItems
                    }
                    currentTime -= 1
                }
            }

            override fun onFinish() {
                deleteAutoDeleteItem(item)
                addOrdinaryItem(item)
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