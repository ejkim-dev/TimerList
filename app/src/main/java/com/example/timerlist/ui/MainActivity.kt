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


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        initializeUi()
        observeViewModel()
    }

    private fun initializeUi() {
        setLayoutMargin()
        setupRecyclerViews()
    }

    private fun setupRecyclerViews() {
        binding.recyclerView.adapter = AutoDeleteAdapter { item ->
            handleItemClick(item)
        }

        binding.recyclerView2.adapter = AutoDeleteAdapter { item ->
            handleItemClick(item)
        }
    }

    private fun observeViewModel() {
        viewModel.itemList.observe(this) { list ->
            (binding.recyclerView.adapter as? AutoDeleteAdapter)?.submitList(list)
        }

        viewModel.autoDeleteList.observe(this) { list ->
            (binding.recyclerView2.adapter as? AutoDeleteAdapter)?.submitList(list)
        }
    }

    private fun handleItemClick(item: BaseItem) {
        when(item) {
            is OrdinaryItem -> {
                viewModel.deleteItemList(item)
                viewModel.addAutoDeleteItem(item)
            }
            is AutoDeleteItem -> {
                viewModel.deleteAutoDeleteItem(item)
                viewModel.addOrdinaryItem(item)
            }
        }
    }

    private fun setLayoutMargin() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val currentInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                setMargins(currentInsets.left, currentInsets.top, currentInsets.right, currentInsets.bottom)
            }
            insets
        }
    }
}