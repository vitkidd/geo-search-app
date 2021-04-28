package com.github.vitkidd.geosearch.feature.di

import com.github.vitkidd.geosearch.feature.presentation.viewModel.MainViewModel
import com.github.vitkidd.geosearch.feature.data.network.MainApi
import com.github.vitkidd.geosearch.feature.data.repo.MainRepositoryImpl
import com.github.vitkidd.geosearch.feature.domain.interactor.MainInteractor
import com.github.vitkidd.geosearch.feature.domain.interactor.MainInteractorImpl
import com.github.vitkidd.geosearch.feature.data.repo.MainRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

const val DEFAULT_QUERY = "motorcycle"
const val DEFAULT_LATITUDE = 52.381455
const val DEFAULT_LONGITUDE = 4.635862

fun featureModule() = module {
    single { MainApi.create() }
    single { MainRepositoryImpl(get()) } bind MainRepository::class
    single { MainInteractorImpl(get()) } bind MainInteractor::class
    viewModel { MainViewModel(get()) }
}