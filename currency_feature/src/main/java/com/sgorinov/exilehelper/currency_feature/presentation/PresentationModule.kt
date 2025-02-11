package com.sgorinov.exilehelper.currency_feature.presentation

import com.sgorinov.exilehelper.core.presentation.FragmentScopes
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val presentationModule = module {
    scope(named(FragmentScopes.CURRENCY_FEATURE)) {
        viewModel {
            CurrencyExchangeViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get()
            )
        }
    }
}