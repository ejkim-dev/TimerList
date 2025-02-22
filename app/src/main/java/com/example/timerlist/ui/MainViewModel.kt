package com.example.timerlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timerlist.item.AutoDeleteItem
import com.example.timerlist.item.BaseItem
import com.example.timerlist.item.OrdinaryItem

class MainViewModel : ViewModel() {

    val itemList : LiveData<List<OrdinaryItem>>
        get() = _itemList
    private val _itemList: MutableLiveData<List<OrdinaryItem>> = MutableLiveData(listOf())

    val autoDeleteList : LiveData<List<AutoDeleteItem>>
        get() = _autoDeleteList
    private val _autoDeleteList: MutableLiveData<List<AutoDeleteItem>> = MutableLiveData(listOf())

    init {
        initializeData()
    }

    fun addOrdinaryItem(item: BaseItem) {
        _itemList.value?.let {
            val newItems = it.toMutableList()
            val ordinaryItem = when(item) {
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
            val autoDeleteItem = when(item) {
                is AutoDeleteItem -> item as AutoDeleteItem
                is OrdinaryItem -> AutoDeleteItem(item.id, item.name)
                else -> throw IllegalArgumentException("Unknown item type")
            }

            newItems.add(autoDeleteItem)
            _autoDeleteList.postValue(newItems)
        }
    }

    fun deleteAutoDeleteItem(item: AutoDeleteItem) {
        _autoDeleteList.value?.let {
            val newItems = it.toMutableList()
            newItems.remove(item)
            _autoDeleteList.postValue(newItems)
        }
    }


    private fun initializeData() = _itemList.postValue(
        (0..49).map {
            OrdinaryItem(it, "Item $it")
        }
    )

}