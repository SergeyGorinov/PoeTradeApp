package com.sgorinov.exilehelper.charts_feature.domain

import com.sgorinov.exilehelper.charts_feature.domain.usecases.*
import com.sgorinov.exilehelper.core.presentation.FragmentScopes
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val domainModule = module {
    scope(named(FragmentScopes.CHARTS_FEATURE)) {
        scoped {
            GetCurrencyHistoryUseCase(get())
        }
        scoped {
            GetItemsGroupsUseCase(get())
        }
        scoped {
            GetCurrenciesOverviewUseCase(get())
        }
        scoped {
            GetItemsOverviewUseCase(get())
        }
        scoped {
            GetItemHistoryUseCase(get())
        }
        scoped {
            GetOverviewUseCase(get())
        }
    }
}