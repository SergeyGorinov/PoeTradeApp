package com.sgorinov.exilehelper.core.domain.usecases

import com.sgorinov.exilehelper.core.domain.ICoreRepository
import com.sgorinov.exilehelper.core.domain.models.NotificationItemData
import com.sgorinov.exilehelper.core.domain.models.NotificationRequest

class GetNotificationRequestsUseCase(private val repository: ICoreRepository) {

    suspend fun execute(
        messagingToken: String?,
        authorizationToken: String? = null,
        type: String,
        league: String
    ): List<NotificationRequest> {
        messagingToken ?: return emptyList()
        repository.syncRemoteNotificationRequests(messagingToken, authorizationToken, type, league)
        return repository.getNotificationRequestsLocal().map { request ->
            NotificationRequest(
                request.remoteId,
                NotificationItemData(request.buyingItem.itemName, request.buyingItem.itemIcon),
                NotificationItemData(request.payingItem.itemName, request.payingItem.itemIcon),
                request.payingAmount
            )
        }
    }
}