package com.sgorinov.exilehelper.exchange_feature.presentation.fragments

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sgorinov.exilehelper.core.DI
import com.sgorinov.exilehelper.core.presentation.*
import com.sgorinov.exilehelper.exchange_feature.R
import com.sgorinov.exilehelper.exchange_feature.databinding.FragmentItemsSearchResultBinding
import com.sgorinov.exilehelper.exchange_feature.presentation.ItemsSearchViewModel
import com.sgorinov.exilehelper.exchange_feature.presentation.SeparatorHelper
import com.sgorinov.exilehelper.exchange_feature.presentation.adapters.ItemsResultAdapter
import com.sgorinov.exilehelper.exchange_feature.presentation.models.FetchedItem
import com.sgorinov.exilehelper.exchange_feature.presentation.models.ItemResultViewData
import kotlinx.coroutines.launch
import org.koin.core.component.inject

internal class ItemsSearchResultFragment : BottomSheetDialogFragment() {

    private val viewModel by scopedViewModel<ItemsSearchViewModel>(
        FragmentScopes.EXCHANGE_FEATURE.scopeId,
        FragmentScopes.EXCHANGE_FEATURE
    )
    private val settings by DI.inject<ApplicationSettings>()

    private var viewBinding: FragmentItemsSearchResultBinding? = null

    private var isLoading = false

    var data: List<ItemResultViewData> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_BaseBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_items_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentItemsSearchResultBinding.bind(view)

        val layoutManager = LinearLayoutManager(context)
        val adapter = ItemsResultAdapter()

        viewBinding?.results?.layoutManager = layoutManager
        viewBinding?.results?.adapter = adapter
        viewBinding?.results?.addOnScrollListener(
            OnResultsScrollListener(viewModel.totalItemsCount) {
                if (!isLoading) {
                    isLoading = true
                    viewBinding?.results?.post {
                        adapter.addLoader()
                    }
                    lifecycleScope.launch {
                        val results = viewModel.fetchPartialResults(settings.league, it)
                        adapter.setFetchedItems(populateResponse(results))
                        isLoading = false
                    }
                }
            }
        )
        adapter.setFetchedItems(populateResponse(data))
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    private fun populateResponse(items: List<ItemResultViewData>): List<FetchedItem> {
        return items.map { currentItem ->
            var hybridItemText: SpannableStringBuilder? = null

            val itemText = SpannableStringBuilder()

            listOfNotNull(
                SeparatorHelper.getSpannableTextGroup(currentItem.itemData.properties, "\n"),
                SeparatorHelper.getSpannableTextGroup(currentItem.itemData.requirements, ", "),
                currentItem.itemData.secDescrText,
                currentItem.itemData.implicitMods,
                currentItem.itemData.explicitMods,
                currentItem.itemData.note
            ).forEach {
                populateItemText(it, itemText, currentItem.frameType)
            }

            if (currentItem.hybridData != null) {
                hybridItemText = SpannableStringBuilder()

                listOfNotNull(
                    SeparatorHelper.getSpannableTextGroup(currentItem.hybridData.properties, "\n"),
                    SeparatorHelper.getSpannableTextGroup(
                        currentItem.hybridData.requirements,
                        ", "
                    ),
                    currentItem.hybridData.secDescrText,
                    currentItem.hybridData.implicitMods,
                    currentItem.hybridData.explicitMods
                ).forEach {
                    populateItemText(it, hybridItemText, currentItem.frameType)
                }
            }

            FetchedItem(
                currentItem.name,
                currentItem.typeLine,
                currentItem.iconUrl,
                currentItem.sockets,
                currentItem.frameType ?: 0,
                SeparatorHelper.getInfluenceIcons(currentItem),
                itemText,
                currentItem.hybridTypeLine,
                hybridItemText
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun populateItemText(
        properties: Any,
        itemText: SpannableStringBuilder,
        frameType: Int?
    ) {
        if (itemText.isNotEmpty())
            itemText.insertSeparator(SeparatorHelper.getSeparator(frameType))
        when (properties) {
            is List<*> -> {
                itemText.append(properties.joinToString("\n"))
            }
            is String -> {
                itemText.append(properties)
            }
            is SpannableStringBuilder -> {
                itemText.append(properties)
            }
        }
    }

    private fun SpannableStringBuilder.insertSeparator(image: Int) {
        this.appendLine()
        this.append(' ')
        this.setSpan(
            CenteredImageSpan(requireContext(), image),
            this.length - 1,
            this.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.appendLine()
    }

    companion object {
        fun newInstance(data: List<ItemResultViewData>): ItemsSearchResultFragment {
            return ItemsSearchResultFragment().apply {
                this.data = data
            }
        }
    }
}