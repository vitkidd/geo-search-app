package com.github.vitkidd.geosearch.feature.di

import com.github.vitkidd.geosearch.feature.presentation.viewModel.MainViewModel
import com.github.vitkidd.geosearch.feature.data.network.Api
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val DEFAULT_QUERY = "motorcycle"
const val DEFAULT_LATITUDE = 52.381455
const val DEFAULT_LONGITUDE = 4.635862

fun featureModule() = module {
    single { Api.create() }
    viewModel { MainViewModel(get()) }
}