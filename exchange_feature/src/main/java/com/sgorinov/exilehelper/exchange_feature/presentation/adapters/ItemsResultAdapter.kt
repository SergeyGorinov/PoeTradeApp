package com.sgorinov.exilehelper.exchange_feature.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sgorinov.exilehelper.exchange_feature.R
import com.sgorinov.exilehelper.exchange_feature.presentation.models.FetchedItem
import com.sgorinov.exilehelper.exchange_feature.presentation.viewholders.ItemsResultViewHolder

internal class ItemsResultAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffUtilCallback = object : DiffUtil.ItemCallback<FetchedItem?>() {
        override fun areItemsTheSame(oldItem: FetchedItem, newItem: FetchedItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: FetchedItem, newItem: FetchedItem): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncDiffer = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewLayout =
            if (viewType == 0) R.layout.result_loading else R.layout.items_result_item
        val view = LayoutInflater.from(parent.context).inflate(viewLayout, parent, false)
        return if (viewType == 0) LoaderViewHolder(view) else ItemsResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = asyncDiffer.currentList.getOrNull(position)
        if (item != null && holder is ItemsResultViewHolder) {
            holder.bind(item)
        }
    }

    override fun getItemCount() = asyncDiffer.currentList.size

    override fun getItemViewType(position: Int) =
        if (asyncDiffer.currentList.getOrNull(position) == null) 0 else 1

    fun setFetchedItems(data: List<FetchedItem>) {
        asyncDiffer.submitList(data)
    }

    fun addLoader() {
        if (!asyncDiffer.currentList.contains(null)) {
            val currentList = asyncDiffer.currentList + null
            asyncDiffer.submitList(currentList)
        }
    }

    internal class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}