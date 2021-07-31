package com.ds.swipesmartrecyclerview.other

import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ds.swipesmartrecyclerview.R
import com.ds.swipesmartrecyclerview.databinding.ActivityOtherBinding
import com.ds.swipesmartrecyclerview.model.ItemModel
import com.ds.swipesmartrecyclerview.toast


class OtherActivity : AppCompatActivity() {
    lateinit var binding: ActivityOtherBinding
    private val adapter by lazy { OtherAdapter() }

    private val p: Paint = Paint()
    private var edit_position = -1
    private var flag_swiped = false
    private val add = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()

    }

    private fun initAdapter() {
        binding.rvItem.setHasFixedSize(true)
       /* binding.rvItem.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )*/
        binding.rvItem.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.rvItem) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                var buttons = listOf<UnderlayButton>()
                val deleteButton = deleteButton(position)
                val markAsUnreadButton = markAsUnreadButton(position)
                val archiveButton = archiveButton(position)
                when (position) {
                    1 -> buttons = listOf(deleteButton)
                    2 -> buttons = listOf(deleteButton, markAsUnreadButton)
                    3 -> buttons = listOf(deleteButton, markAsUnreadButton, archiveButton)
                    else -> buttons = listOf(deleteButton, markAsUnreadButton)
                }
                return listOf(deleteButton, markAsUnreadButton, archiveButton)
            }

        })

        itemTouchHelper.attachToRecyclerView(binding.rvItem)

        adapter.submitList(getItems())
    }

    private fun deleteButton(position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            this,
            "Hızlı Emir",
            14.0f,
            R.color.green,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    toast("Hızlı Emir item $position")
                }
            })
    }

    private fun markAsUnreadButton(position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            this,
            "Taşı",
            14.0f,
            R.color.yellow,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    toast("Taşı item $position")
                }
            })
    }

    private fun archiveButton(position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            this,
            "Listeden Çıkar",
            14.0f,
            R.color.gray_700,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    toast("Listeden Çıkar item $position")
                }
            })
    }

    /*private fun initAdapter2() {
        binding.rvItem.setHasFixedSize(true)
        binding.rvItem.adapter = adapter
        //initSwipe()
        val swipeHelper = object : SwipeHelper(this, binding.rvItem) {
            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder,
                underlayButtons: MutableList<UnderlayButton>
            ) {
                underlayButtons.add(UnderlayButton(
                    "Delete",
                    0,
                    Color.parseColor("#FF3C30")
                ) {
                    // TODO: onDelete
                })
                underlayButtons.add(UnderlayButton(
                    "Transfer",
                    0,
                    Color.parseColor("#FF9502")
                ) {
                    // TODO: OnTransfer
                })
                underlayButtons.add(UnderlayButton(
                    "Unshare",
                    0,
                    Color.parseColor("#C7C7CB")
                ) {
                    // TODO: OnUnshare
                })
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHelper)
        itemTouchHelper.attachToRecyclerView(binding.rvItem)
        adapter.submitList(getItems())
    }*/


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


    private fun initSwipe() {
        val simpleCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun getSwipeDirs(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val position = viewHolder.bindingAdapterPosition
                if (position != edit_position) {
                    if (edit_position != -1 && flag_swiped) {
                        adapter.notifyItemChanged(edit_position)
                        flag_swiped = false
                        edit_position = position
                    }
                }
                return super.getSwipeDirs(recyclerView, viewHolder)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                flag_swiped = true
                if (direction == ItemTouchHelper.LEFT) {
                    adapter.notifyItemRemoved(position)
                } else {
                    edit_position = position
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val icon: Drawable?
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView: View = viewHolder.itemView
                    val height: Int = itemView.bottom - itemView.top
                    val width: Int = itemView.right - itemView.left
                    val iconH = resources.displayMetrics.density * 28
                    val iconW = resources.displayMetrics.density * 28
                    if (dX > 0) {
                        val background = RectF(
                            itemView.left.toFloat(),
                            itemView.top.toFloat(),
                            dX,
                            itemView.bottom.toFloat()
                        )
                        p.color = Color.parseColor("#FF9800")
                        c.drawRect(background, p)
                        icon = ContextCompat.getDrawable(baseContext, R.drawable.ic_add)
                        icon!!.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                        // you can also use icon size
                        // int iconWidth = icon.getIntrinsicWidth();
                        // int iconHeight = icon.getIntrinsicHeight();
                        val rate = Math.abs(dX) / width
                        val iconLeft = (itemView.left - iconW + width / 3 * rate).toInt()
                        val iconTop = (itemView.top + height / 2 - iconH / 2).toInt()
                        val iconRight = (itemView.left + width / 3 * rate).toInt()
                        val iconBottom = (itemView.bottom - height / 2 + iconH / 2).toInt()
                        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        icon.draw(c)
                    } else if (dX < 0) {
                        val background = RectF(
                            itemView.right.toFloat() + dX,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat()
                        )
                        p.color = Color.parseColor("#E91E63")
                        c.drawRect(background, p)
                        icon = ContextCompat.getDrawable(baseContext, R.drawable.ic_delete)
                        icon!!.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                        val rate = Math.abs(dX) / width
                        val iconLeft = (itemView.right - width / 3 * rate).toInt()
                        val iconTop = (itemView.top + height / 2 - iconH / 2).toInt()
                        val iconRight = (itemView.right + iconW - width / 3 * rate).toInt()
                        val iconBottom = (itemView.bottom - height / 2 + iconH / 2).toInt()
                        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        icon.draw(c)
                    }
                }
                super.onChildDraw(
                    c,
                    recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvItem)
    }


}