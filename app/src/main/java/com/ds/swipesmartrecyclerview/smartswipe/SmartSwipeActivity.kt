package com.ds.swipesmartrecyclerview.smartswipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ds.swipesmartrecyclerview.R
import com.ds.swipesmartrecyclerview.databinding.ActivitySmartSwipeBinding
import com.ds.swipesmartrecyclerview.model.ItemModel
import com.ds.swipesmartrecyclerview.toast
import smartadapter.SmartRecyclerAdapter
import smartadapter.diffutil.DiffUtilExtension
import smartadapter.diffutil.SimpleDiffUtilExtension
import smartadapter.viewevent.listener.OnClickEventListener

class SmartSwipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySmartSwipeBinding
    lateinit var adapter: SmartRecyclerAdapter

    private val predicate = object : DiffUtilExtension.DiffPredicate<ItemModel> {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmartSwipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
    }

    private fun initAdapter() {
        adapter = SmartRecyclerAdapter.items(getItems())
            .map(ItemModel::class, SmartSwipeViewHolder::class)
            .add(OnClickEventListener(SmartSwipeViewHolder::class, R.id.btnRemoveFromList) {
                val item = it.adapter.getItem(it.position) as ItemModel
                toast("removed ${item.title}")
            })
            .add(OnClickEventListener(SmartSwipeViewHolder::class, R.id.btnMove) {
                val item = it.adapter.getItem(it.position) as ItemModel
                toast("moved ${item.title}")
            })
            .add(OnClickEventListener(SmartSwipeViewHolder::class, R.id.btnSendOrder) {
                val item = it.adapter.getItem(it.position) as ItemModel
                toast("sent order ${item.title}")
            })
            .add(SimpleDiffUtilExtension(predicate))
            .add(SmartSwipeAdapterBinder(R.id.rootSwipe, true))
            .into(binding.rvSwipe)
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