package com.sgorinov.exilehelper.core.domain.models

import kotlinx.serialization.Serializable

data class NotificationRequest(
    var id: Long?,
    var buyingItem: NotificationItemData,
    var payingItem: NotificationItemData,
    var payingAmount: Int
)

@Serializable
data class NotificationItemData(
    val itemName: String,
    val itemIcon: String
)
