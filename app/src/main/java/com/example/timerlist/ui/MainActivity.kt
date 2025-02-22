package com.example.timerlist.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.timerlist.adapter.AutoDeleteAdapter
import com.example.timerlist.databinding.ActivityMainBinding
import com.example.timerlist.item.AutoDeleteItem
import com.example.timerlist.item.BaseItem
import com.example.timerlist.item.OrdinaryItem


class MainActivity : AppCompatActivity(), AutoDeleteAdapter.OnItemClickListener {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeData()
        initializeUi()
        subscribeUi()
        subscribeViewModel()
    }

    private fun initializeData() {

    }

    private fun initializeUi() {
        binding.recyclerView.adapter = AutoDeleteAdapter(this)
        binding.recyclerView2.adapter = AutoDeleteAdapter(this)
    }

    private fun subscribeUi() {
        with(binding) {
            recyclerView
        }
    }

    private fun subscribeViewModel() {

        viewModel.itemList.observe(this) {
            (binding.recyclerView.adapter as AutoDeleteAdapter).submitList(it)
        }

        viewModel.autoDeleteList.observe(this) {
            (binding.recyclerView2.adapter as AutoDeleteAdapter).submitList(it)
        }
    }

    override fun onItemClick(item: BaseItem, isOrdinaryItem: Boolean) {
        if (isOrdinaryItem) {
            viewModel.deleteItemList(item as OrdinaryItem)
            viewModel.addAutoDeleteItem(item)
        } else {
            viewModel.deleteAutoDeleteItem(item as AutoDeleteItem)
            viewModel.addOrdinaryItem(item)
        }
    }

}