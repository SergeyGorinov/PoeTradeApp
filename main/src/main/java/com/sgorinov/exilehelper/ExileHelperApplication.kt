package com.sgorinov.exilehelper

import android.app.Application
import com.sgorinov.exilehelper.charts_feature.chartsFeatureModules
import com.sgorinov.exilehelper.core.DI
import com.sgorinov.exilehelper.core.coreModules
import com.sgorinov.exilehelper.core.data.ObjectBox
import com.sgorinov.exilehelper.currency_feature.currencyFeatureModules
import com.sgorinov.exilehelper.exchange_feature.exchangeFeatureModules
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Suppress("unused")
class ExileHelperApplication : Application() {

    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        DI.init(
            this,
            mainModules +
                    coreModules +
                    currencyFeatureModules +
                    exchangeFeatureModules +
                    chartsFeatureModules
        )
        ObjectBox.init(this)
    }
}