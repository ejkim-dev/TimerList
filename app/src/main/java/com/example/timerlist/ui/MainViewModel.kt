package com.example.timerlist.ui

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timerlist.item.AutoDeleteItem
import com.example.timerlist.item.BaseItem
import com.example.timerlist.item.OrdinaryItem
import com.example.timerlist.updateList

class MainViewModel : ViewModel() {

    private val _itemList = MutableLiveData<List<OrdinaryItem>>(emptyList())
    val itemList: LiveData<List<OrdinaryItem>> = _itemList

    private val _autoDeleteList = MutableLiveData<List<AutoDeleteItem>>(emptyList())
    val autoDeleteList: LiveData<List<AutoDeleteItem>> = _autoDeleteList

    private val countdownTimers = mutableMapOf<Int, CountDownTimer>() // 아이템별 타이머 관리

    init {
        initializeData()
    }

    fun addOrdinaryItem(item: BaseItem) {
        val ordinaryItem = when (item) {
            is OrdinaryItem -> item
            is AutoDeleteItem -> OrdinaryItem(item.id, item.name)
        }

        _itemList.updateList { list ->
            if (list.none { it.id == ordinaryItem.id }) list.add(ordinaryItem)
        }
    }

    fun deleteItemList(item: OrdinaryItem) {
        _itemList.updateList { list -> list.remove(item) }
    }

    fun addAutoDeleteItem(item: BaseItem) {
        val autoDeleteItem = when (item) {
            is AutoDeleteItem -> item
            is OrdinaryItem -> AutoDeleteItem(item.id, item.name)
        }

        _autoDeleteList.updateList { list ->
            if (list.none { it.id == autoDeleteItem.id }) list.add(autoDeleteItem)
        }
        startCountdown(autoDeleteItem)
    }

    fun deleteAutoDeleteItem(item: AutoDeleteItem) {
        _autoDeleteList.updateList { list -> list.removeAll { it.id == item.id } }
        cancelCountdown(item)
    }

    private fun startCountdown(item: AutoDeleteItem) {
        cancelCountdown(item) // 중복 타이머 방지

        val timer = object : CountDownTimer(10_000L, 1000L) {
            var remainingTime = 10

            override fun onTick(millisUntilFinished: Long) {
                remainingTime -= 1
                _autoDeleteList.updateList { list ->
                    val index = list.indexOfFirst { it.id == item.id }
                    if (index != -1) list[index] = list[index].copy(time = remainingTime)
                }
            }

            override fun onFinish() {
                deleteAutoDeleteItem(item)
                addOrdinaryItem(item)
                countdownTimers.remove(item.id)
            }
        }

        countdownTimers[item.id] = timer
        timer.start()
    }

    private fun cancelCountdown(item: AutoDeleteItem) {
        countdownTimers.remove(item.id)?.cancel()
    }

    private fun initializeData() {
        _itemList.postValue(List(50) { index ->
            OrdinaryItem(index, ('A'..'Z').random().toString())
        })
    }
}