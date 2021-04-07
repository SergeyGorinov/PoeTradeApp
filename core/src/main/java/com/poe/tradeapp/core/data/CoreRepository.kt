package com.poe.tradeapp.core.data

import android.util.Log
import com.poe.tradeapp.core.data.models.*
import io.objectbox.kotlin.boxFor
import kotlinx.serialization.json.Json
import retrofit2.Response
import retrofit2.await

class CoreRepository(
    private val staticApi: StaticApi,
    private val poeTradeApi: PoeTradeApi
) : BaseCoreRepository() {

    override var leagues: List<String> = listOf()
    override var staticData: List<StaticGroup> = listOf()
    override var itemData: List<ItemGroup> = listOf()
    override var statData: List<StatGroup> = listOf()

    override suspend fun getCurrencyItems() {
        staticData = staticApi.getStaticData().await().result
    }

    override suspend fun getItems() {
        itemData = staticApi.getItemsData().await().result
    }

    override suspend fun getStats() {
        statData = staticApi.getStatsData().await().result
    }

    override suspend fun getLeagues() {
        leagues = staticApi.getLeagueData().await().result.map { it.id }
    }

    override suspend fun setNotificationRequestRemote(request: RemoteNotificationRequest): Response<Void> {
        return poeTradeApi.sendRequest(request)
    }

    override suspend fun setNotificationRequestLocal(request: NotificationRequest) {
        ObjectBox.objectBox.boxFor<NotificationRequest>().put(request)
    }

    override suspend fun syncRemoteNotificationRequests(
        messagingToken: String,
        authorizationToken: String?,
        type: String
    ) {
        if (authorizationToken != null) {
            val result = try {
                val response = poeTradeApi.getRequests(authorizationToken, messagingToken, type)
                response.body()
            } catch (e: Exception) {
                Log.e("GET REQUESTS ERROR", e.stackTraceToString())
                null
            }?.map {
                NotificationRequest(
                    0L,
                    it.firebaseAuthenticationToken,
                    it.firebaseMessagingToken,
                    it.requestType,
                    it.requestPayload,
                    Json.decodeFromString(ItemData.serializer(), it.buyingItem),
                    Json.decodeFromString(ItemData.serializer(), it.payingItem),
                    it.payingAmount
                )
            }
            if (result != null) {
                val localRequests = ObjectBox.objectBox.boxFor<NotificationRequest>().all
                localRequests.filterNot { result.contains(it) }.forEach {
                    ObjectBox.objectBox.boxFor<NotificationRequest>().remove(it.id)
                }
            } else {
                ObjectBox.objectBox.boxFor<NotificationRequest>().removeAll()
            }
            ObjectBox.objectBox.boxFor<NotificationRequest>().put(result)
        }
    }

    override suspend fun getNotificationRequestsLocal(): List<NotificationRequest> {
        return ObjectBox.objectBox.boxFor<NotificationRequest>().all
    }

    override suspend fun addToken(messagingToken: String, authorizationToken: String): Boolean {
        return try {
            poeTradeApi.addToken(authorizationToken, messagingToken).isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}