package com.example.starwarscharacters.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharacters.R
import com.example.starwarscharacters.data.model.*
import com.example.starwarscharacters.databinding.ItemRowBinding

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

class MainItemsAdapter(private val context: Context, private val onClickListener: OnClickListener) :
    ListAdapter<Item, MainItemsAdapter.ItemViewHolder>(DiffCallback) {

    class ItemViewHolder(private var binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Item, onClickListener: OnClickListener, context: Context) {

            //Set title text depending on obj type
            binding.title.text = when (item) {
                is Character -> item.name
                is Race -> item.name
                is Starship -> item.name
                is Planet -> item.name
                else -> context.getString(R.string.unknown)
            }
            //Set description text depending on obj type
            binding.description.text = when (item) {
                is Character -> "${context.getString(R.string.films)}: ${item.films.size}"
                is Race -> "${context.getString(R.string.language)}: ${item.language}"
                is Starship -> "${context.getString(R.string.manufacturer)}: ${item.manufacturer}"
                is Planet -> "${context.getString(R.string.terrain)}: ${item.terrain}"
                else -> context.getString(R.string.unknown)
            }
            //Set drawable depending on obj type
            binding.img.setImageResource(
                when (item) {
                    is Character -> R.drawable.ic_characters
                    is Race -> R.drawable.ic_species
                    is Starship -> R.drawable.ic_starships
                    is Planet -> R.drawable.ic_planets
                    else -> R.drawable.ic_league
                }
            )

            binding.executePendingBindings()
            binding.card.setOnClickListener {
                when (item) {
                    is Character -> onClickListener.onClick(item.name, item)
                    is Race -> onClickListener.onClick(item.name, item)
                    is Starship -> onClickListener.onClick(item.name, item)
                    is Planet -> onClickListener.onClick(item.name, item)
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Item>() {

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return when (oldItem) {
                is Character -> oldItem.url == (newItem as Character).url
                is Race -> oldItem.url == (newItem as Race).url
                is Starship -> oldItem.url == (newItem as Starship).url
                is Planet -> oldItem.url == (newItem as Planet).url
                else -> false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener, context)
    }

    class OnClickListener(val clickListener: (title : String, item: Item) -> Unit) {
        fun onClick(title : String, item: Item) = clickListener(title, item)
    }

}