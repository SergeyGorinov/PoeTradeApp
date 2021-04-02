package com.poe.tradeapp.core.domain

import com.poe.tradeapp.core.data.models.*
import retrofit2.Response

interface ICoreRepository {

    val leagues: List<String>
    val staticData: List<StaticGroup>
    val itemData: List<ItemGroup>
    val statData: List<StatGroup>

    suspend fun getCurrencyItems()
    suspend fun getItems()
    suspend fun getStats()
    suspend fun getLeagues()
    suspend fun setNotificationRequestRemote(request: RemoteNotificationRequest): Response<Void>
    suspend fun setNotificationRequestLocal(request: NotificationRequest)
    suspend fun syncRemoteNotificationRequests(
        messagingToken: String,
        authorizationToken: String? = null
    )

    suspend fun getNotificationRequestsLocal(): List<NotificationRequest>
    suspend fun addToken(messagingToken: String, authorizationToken: String): Boolean
}