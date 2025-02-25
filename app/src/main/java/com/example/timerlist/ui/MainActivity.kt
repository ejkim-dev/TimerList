package com.example.timerlist.ui

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
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
        enableEdgeToEdge()
        setContentView(binding.root)

        initializeUi()
        subscribeViewModel()
    }

    private fun initializeUi() {
        setLayoutMargin()
        binding.recyclerView.adapter = AutoDeleteAdapter(this)
        binding.recyclerView2.adapter = AutoDeleteAdapter(this)
    }

    private fun subscribeViewModel() {
        viewModel.itemList.observe(this) {
            (binding.recyclerView.adapter as AutoDeleteAdapter).submitList(it)
        }

        viewModel.autoDeleteList.observe(this) {
            (binding.recyclerView2.adapter as AutoDeleteAdapter).submitList(it)
        }
    }

    private fun setLayoutMargin() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val currentInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = currentInsets.top
                bottomMargin = currentInsets.bottom
                leftMargin = currentInsets.left
                rightMargin = currentInsets.right
            }
            insets
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