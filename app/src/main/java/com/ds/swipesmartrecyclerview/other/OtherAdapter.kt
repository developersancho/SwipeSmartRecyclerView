package com.ds.swipesmartrecyclerview.other

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ds.swipesmartrecyclerview.base.BListAdapter
import com.ds.swipesmartrecyclerview.base.BaseViewHolder
import com.ds.swipesmartrecyclerview.base.ItemDiffCallback
import com.ds.swipesmartrecyclerview.base.toBinding
import com.ds.swipesmartrecyclerview.databinding.ItemInfoBinding
import com.ds.swipesmartrecyclerview.model.ItemModel


class OtherAdapter : BListAdapter<ItemModel, OtherAdapter.ItemViewHolder>(ItemDiffCallback(
    onItemsTheSame = { old, new -> old.title == new.title },
    onContentsTheSame = { old, new -> old == new }
)) {


    inner class ItemViewHolder(binding: ItemInfoBinding) :
        BaseViewHolder<ItemInfoBinding, ItemModel>(binding) {

        override fun bindItem(item: ItemModel) {
            binding.tvTitle.text = item.title
            binding.tvDesc.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

}