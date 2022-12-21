package com.github.vitkidd.geosearch.feature.di

import com.github.vitkidd.geosearch.feature.presentation.viewModel.MainViewModel
import com.github.vitkidd.geosearch.feature.data.network.createMainApi
import com.github.vitkidd.geosearch.feature.data.repo.MainRepositoryImpl
import com.github.vitkidd.geosearch.feature.domain.interactor.MainInteractor
import com.github.vitkidd.geosearch.feature.domain.interactor.MainInteractorImpl
import com.github.vitkidd.geosearch.feature.data.repo.MainRepository
import com.github.vitkidd.geosearch.feature.domain.mapper.PhotoEntityMapper
import com.github.vitkidd.geosearch.feature.presentation.mapper.PhotoModelMapper
import com.github.vitkidd.geosearch.feature.presentation.mapper.RegionEntityMapper
import com.github.vitkidd.geosearch.feature.presentation.mapper.SearchEntityMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

const val DEFAULT_QUERY = "motorcycle"
const val DEFAULT_LATITUDE = 52.381455
const val DEFAULT_LONGITUDE = 4.635862

fun featureModule() = module {
    single { createMainApi() }
    single { MainRepositoryImpl(get()) } bind MainRepository::class
    single { MainInteractorImpl(get(), get()) } bind MainInteractor::class
    single { PhotoEntityMapper() }
    single { PhotoModelMapper() }
    single { RegionEntityMapper() }
    single { SearchEntityMapper(get()) }
    viewModel { MainViewModel(get(), get(), get()) }
}