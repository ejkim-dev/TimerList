package com.example.timerlist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.timerlist.adapter.AutoDeleteAdapter
import com.example.timerlist.databinding.ActivityMainBinding
import com.example.timerlist.item.AutoDelete

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeData()
        initializeUi()
        subscribeUi()
        subscribeViewModel()
    }

    private fun initializeData() {

    }

    private fun initializeUi() {
        binding.recyclerView.adapter =  AutoDeleteAdapter().apply {
            submitList(
                listOf(
                    AutoDelete(0, "Item 0", isRunning = false),
                    AutoDelete(1, "Item 1", isRunning = false)
                )
            )
        }
        binding.recyclerView2.adapter = AutoDeleteAdapter().apply {
            submitList(
                listOf(
                    AutoDelete(0, "Item 0", isRunning = true),
                    AutoDelete(1, "Item 1", isRunning = true)
                )
            )
        }
    }

    private fun subscribeUi() {

    }

    private fun subscribeViewModel() {

    }
}