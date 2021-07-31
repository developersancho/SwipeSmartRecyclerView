package com.ds.swipesmartrecyclerview

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ds.swipesmartrecyclerview.adapter.MainViewHolder
import com.ds.swipesmartrecyclerview.databinding.ActivityMainBinding
import com.ds.swipesmartrecyclerview.model.ItemModel
import com.ds.swipesmartrecyclerview.other.OtherActivity
import com.ds.swipesmartrecyclerview.swipe.SwipeActivity
import smartadapter.SmartRecyclerAdapter
import smartadapter.diffutil.DiffUtilExtension
import smartadapter.diffutil.SimpleDiffUtilExtension
import smartadapter.extension.SmartViewHolderBinder
import smartadapter.viewevent.model.ViewEvent
import smartadapter.viewevent.swipe.BasicSwipeEventBinder
import smartadapter.viewevent.swipe.SwipeFlags

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: SmartRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()

        binding.btnOtherActivity.setOnClickListener {
            val intent = Intent(this@MainActivity, OtherActivity::class.java)
            startActivity(intent)
        }

        binding.btnSwipeActivity.setOnClickListener {
            val intent = Intent(this@MainActivity, SwipeActivity::class.java)
            startActivity(intent)
        }
    }

    private val predicate = object : DiffUtilExtension.DiffPredicate<ItemModel> {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem == newItem
        }
    }

    private fun initAdapter() {
        adapter = SmartRecyclerAdapter.items(getItems())
            .map(ItemModel::class, MainViewHolder::class)
            .add(SimpleDiffUtilExtension(predicate))
            .add(SwipeRemoveItemBinder(ItemTouchHelper.LEFT) {
                // Remove item from SmartRecyclerAdapter data set
                adapter.removeItem(it.viewHolder.bindingAdapterPosition)
            })
            .into(binding.rvMain)
    }


    class SwipeRemoveItemBinder(
        override var swipeFlags: SwipeFlags,
        override var eventListener: (ViewEvent.OnItemSwiped) -> Unit
    ) : BasicSwipeEventBinder(
        eventListener = eventListener
    ), SmartViewHolderBinder {

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            super.onSwiped(viewHolder, direction)
        }

        override fun onChildDraw(
            canvas: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            val itemView = viewHolder.itemView
            val icon = ContextCompat.getDrawable(
                viewHolder.itemView.context,
                R.drawable.ic_delete_black_24dp
            )
            val background = ColorDrawable(Color.RED)

            val iconMargin = (itemView.height - icon!!.intrinsicHeight) / 2
            val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
            val iconBottom = iconTop + icon.intrinsicHeight

            val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
            val iconRight = itemView.right - iconMargin

            icon.setBounds(
                iconLeft,
                iconTop,
                iconRight,
                iconBottom
            )

            background.setBounds(
                (itemView.right + dX).toInt(),
                itemView.top,
                itemView.right,
                itemView.bottom
            )

            if (dX.toInt() == 0) { // view is unSwiped
                background.setBounds(0, 0, 0, 0)
            }

            background.draw(canvas)

            if (-dX > (icon.intrinsicWidth + iconMargin)) // Draw icon only on full visibility
                icon.draw(canvas)
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
            ItemModel(title = "title-9", description = "desc-9")
        )
    }
}

fun AppCompatActivity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}