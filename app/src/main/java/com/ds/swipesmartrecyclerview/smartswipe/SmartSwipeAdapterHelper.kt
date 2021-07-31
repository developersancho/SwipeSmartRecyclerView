package com.ds.swipesmartrecyclerview.smartswipe

import android.content.Context
import android.view.ViewGroup
import com.ds.swipesmartrecyclerview.R
import com.ds.swipesmartrecyclerview.model.ItemModel
import com.google.android.material.textview.MaterialTextView
import smartadapter.viewholder.SmartViewHolder

class SmartSwipeViewHolder(parentView: ViewGroup) :
    SmartViewHolder<ItemModel>(parentView, R.layout.row_item_info) {

    val context: Context = parentView.context

    private val tvTitle = itemView.findViewById<MaterialTextView>(R.id.tvStockShortName)
    private val tvDescription = itemView.findViewById<MaterialTextView>(R.id.tvStockFullName)


    override fun bind(item: ItemModel) {
        tvTitle.text = item.title
        tvDescription.text = item.description
    }
}