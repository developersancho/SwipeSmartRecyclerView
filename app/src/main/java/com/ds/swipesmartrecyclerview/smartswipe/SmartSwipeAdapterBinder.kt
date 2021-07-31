package com.ds.swipesmartrecyclerview.smartswipe

import android.os.Bundle
import androidx.annotation.IdRes
import com.ds.swipesmartrecyclerview.swipe.SwipeMenuStatus
import com.ds.swipesmartrecyclerview.swipe.SwipeRevealLayout
import com.ds.swipesmartrecyclerview.swipe.ViewBinderHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import smartadapter.SmartRecyclerAdapter
import smartadapter.extension.SmartViewHolderBinder
import smartadapter.extension.findView
import smartadapter.listener.OnBindViewHolderListener
import smartadapter.viewholder.SmartViewHolder

class SmartSwipeAdapterBinder(
    @IdRes private val swipeId: Int,
    private var isOpenFirstMenuItem: Boolean = false,
    override val identifier: Any = SmartSwipeAdapterBinder::class
) : SmartViewHolderBinder, OnBindViewHolderListener {

    private val binderHelper = ViewBinderHelper()
    private val scope = CoroutineScope(Dispatchers.Main)
    private val firstItem = "0"

    init {
        binderHelper.setOpenOnlyOne(true)
        if (isOpenFirstMenuItem) {
            scope.launch {
                delay(SwipeMenuStatus.OPEN.delay)
                binderHelper.openLayout(firstItem)
                delay(SwipeMenuStatus.CLOSE.delay)
                binderHelper.closeLayout(firstItem)
            }
        }
    }

    override fun onBindViewHolder(adapter: SmartRecyclerAdapter, viewHolder: SmartViewHolder<Any>) {
        val swipeRevealLayout = findView(swipeId, viewHolder) as SwipeRevealLayout
        binderHelper.bind(swipeRevealLayout, viewHolder.bindingAdapterPosition.toString())
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in [android.app.Activity.onSaveInstanceState]
     */
    fun saveStates(outState: Bundle?) {
        binderHelper.saveStates(outState)
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in [android.app.Activity.onRestoreInstanceState]
     */
    fun restoreStates(inState: Bundle?) {
        binderHelper.restoreStates(inState)
    }
}