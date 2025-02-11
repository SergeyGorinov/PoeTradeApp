package com.sgorinov.exilehelper.currency_feature.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sgorinov.exilehelper.currency_feature.R
import com.sgorinov.exilehelper.currency_feature.databinding.CurrencyImageButtonBinding
import com.sgorinov.exilehelper.currency_feature.databinding.CurrencyTextButtonBinding
import com.sgorinov.exilehelper.currency_feature.presentation.models.CurrencyViewData
import com.squareup.picasso.Picasso

internal class CurrencyGroupAdapter(
    private val items: List<Pair<Boolean, CurrencyViewData>>,
    private val isTextGroup: Boolean,
    private val onItemClick: (Boolean, String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (isTextGroup) {
            CurrencyTextItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.currency_text_button, parent, false)
            )
        } else {
            CurrencyImageItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.currency_image_button, parent, false)
            )
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = items[position]
        when (holder) {
            is CurrencyTextItemViewHolder -> holder.bind(item.first, item.second, onItemClick)
            is CurrencyImageItemViewHolder -> holder.bind(item.first, item.second, onItemClick)
        }
    }

    override fun getItemCount() = items.size

    private class CurrencyTextItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val viewBinding = CurrencyTextButtonBinding.bind(itemView)

        fun bind(
            isSelected: Boolean,
            item: CurrencyViewData,
            onItemClick: (Boolean, String) -> Unit
        ) {
            viewBinding.root.isSelected = isSelected
            viewBinding.root.text = item.label
            viewBinding.root.setOnClickListener {
                it.isSelected = !it.isSelected
                onItemClick(it.isSelected, item.id)
            }
        }
    }

    private class CurrencyImageItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val viewBinding = CurrencyImageButtonBinding.bind(itemView)

        fun bind(
            isSelected: Boolean,
            item: CurrencyViewData,
            onItemClick: (Boolean, String) -> Unit
        ) {
            viewBinding.root.isSelected = isSelected
            Picasso.get().load(item.imageUrl).into(viewBinding.image)
            viewBinding.root.setOnClickListener {
                it.isSelected = !it.isSelected
                onItemClick(it.isSelected, item.id)
            }
        }
    }
}