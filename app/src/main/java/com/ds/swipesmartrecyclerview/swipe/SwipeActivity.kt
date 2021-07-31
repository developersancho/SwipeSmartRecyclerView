package com.ds.swipesmartrecyclerview.swipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ds.swipesmartrecyclerview.databinding.ActivitySwipeBinding
import com.ds.swipesmartrecyclerview.model.ItemModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SwipeActivity : AppCompatActivity() {
    lateinit var binding: ActivitySwipeBinding
    private val adapter by lazy { SwipeAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
    }

    private fun initAdapter() {
        binding.rvSwipe.setHasFixedSize(true)
        binding.rvSwipe.layoutManager = LinearLayoutManager(this)
        binding.rvSwipe.adapter = adapter
        adapter.submitList(getItems())
        CoroutineScope(Dispatchers.Main).launch {
            adapter.binderHelper.openLayout(getItems().first().title)
            delay(1000)
            adapter.binderHelper.closeLayout(getItems().first().title)
        }
    }

    private fun getItems(): MutableList<ItemModel> {
        return mutableListOf(
            ItemModel(title = "title-1", description = "desc-1"),
            ItemModel(title = "title-2", description = "desc-2"),
            ItemModel(title = "title-3", description = "desc-3"),
            ItemModel(title = "title-4", description = "desc-4"),
            ItemModel(title = "title-5", description = "desc-5"),
            ItemModel(title = "title-6", description = "desc-6"),
            ItemModel(title = "title-7", description = "desc-7"),
            ItemModel(title = "title-8", description = "desc-8"),
            ItemModel(title = "title-9", description = "desc-9"),
            ItemModel(title = "title-1", description = "desc-1"),
            ItemModel(title = "title-2", description = "desc-2"),
            ItemModel(title = "title-3", description = "desc-3"),
            ItemModel(title = "title-4", description = "desc-4"),
            ItemModel(title = "title-5", description = "desc-5"),
            ItemModel(title = "title-6", description = "desc-6"),
            ItemModel(title = "title-7", description = "desc-7"),
            ItemModel(title = "title-8", description = "desc-8"),
            ItemModel(title = "title-9", description = "desc-9")
        )
    }
}