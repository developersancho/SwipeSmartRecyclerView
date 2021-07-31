package com.ds.swipesmartrecyclerview.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ds.swipesmartrecyclerview.R
import com.ds.swipesmartrecyclerview.model.ItemModel
import smartadapter.viewholder.SmartViewHolder

class MainViewHolder(parentView: ViewGroup) :
    SmartViewHolder<ItemModel>(parentView, R.layout.item_info) {

    val context: Context = parentView.context

    private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
    private val tvDescription = itemView.findViewById<TextView>(R.id.tvDesc)


    override fun bind(item: ItemModel) {
        tvTitle.text = item.title
        tvDescription.text = item.description
    }
}