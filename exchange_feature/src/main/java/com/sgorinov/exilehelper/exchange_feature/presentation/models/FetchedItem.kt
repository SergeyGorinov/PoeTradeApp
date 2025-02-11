package com.sgorinov.exilehelper.exchange_feature.presentation.models

import android.text.SpannableStringBuilder

internal data class FetchedItem(
    val name: String?,
    val typeLine: String,
    val iconUrl: String,
    val sockets: List<Socket>?,
    val frameType: Int,
    val influenceIcons: List<Int?>,
    val itemTextData: SpannableStringBuilder,
    val hybridTypeLine: String? = null,
    val hybridItemTextData: SpannableStringBuilder? = null
)