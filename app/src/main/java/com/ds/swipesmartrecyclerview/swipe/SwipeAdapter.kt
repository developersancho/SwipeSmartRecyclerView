package com.ds.swipesmartrecyclerview.swipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ds.swipesmartrecyclerview.base.BListAdapter
import com.ds.swipesmartrecyclerview.base.BaseViewHolder
import com.ds.swipesmartrecyclerview.base.ItemDiffCallback
import com.ds.swipesmartrecyclerview.base.toBinding
import com.ds.swipesmartrecyclerview.databinding.RowItemInfoBinding
import com.ds.swipesmartrecyclerview.model.ItemModel
import com.ds.swipesmartrecyclerview.toast

class SwipeAdapter : BListAdapter<ItemModel, SwipeAdapter.ItemViewHolder>(ItemDiffCallback(
    onItemsTheSame = { old, new -> old.title == new.title },
    onContentsTheSame = { old, new -> old == new }
)) {
    val binderHelper = ViewBinderHelper()

    init {
        binderHelper.setOpenOnlyOne(true)
    }

    inner class ItemViewHolder(binding: RowItemInfoBinding) :
        BaseViewHolder<RowItemInfoBinding, ItemModel>(binding) {

        override fun bindItem(item: ItemModel) {
            binding.tvStockShortName.text = item.title
            binding.tvStockFullName.text = item.description

            binding.btnRemoveFromList.setOnClickListener {
                //binderHelper.closeLayout(getItem(bindingAdapterPosition).title)
                binding.root.context.toast(getItem(bindingAdapterPosition).title)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(parent.toBinding())
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
        // put an unique string id as value, can be any string which uniquely define the data
        binderHelper.bind(holder.binding.rootSwipe, getItem(position).title)
        holder.bindItem(getItem(position))
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