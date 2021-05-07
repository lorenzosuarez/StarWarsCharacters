package com.example.starwarscharacters.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharacters.data.model.Item

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Item>?) {
    val adapter = recyclerView.adapter as MainItemsAdapter
    adapter.submitList(data)
    recyclerView.scheduleLayoutAnimation()
}
