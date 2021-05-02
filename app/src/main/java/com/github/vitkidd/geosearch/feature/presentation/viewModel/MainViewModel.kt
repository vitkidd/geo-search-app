package com.github.vitkidd.geosearch.feature.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.vitkidd.geosearch.feature.presentation.model.RegionModel
import com.github.vitkidd.geosearch.feature.di.DEFAULT_QUERY
import com.github.vitkidd.geosearch.feature.domain.entity.PhotoEntity
import com.github.vitkidd.geosearch.feature.domain.entity.RegionEntity
import com.github.vitkidd.geosearch.feature.domain.interactor.MainInteractor
import com.github.vitkidd.geosearch.feature.presentation.model.MainViewState
import com.github.vitkidd.geosearch.feature.domain.entity.SearchEntity
import com.github.vitkidd.geosearch.feature.presentation.model.PhotoModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val mainInteractor: MainInteractor
) : ViewModel() {

    private val querySubject = BehaviorSubject.createDefault(DEFAULT_QUERY)
    private val regionSubject = BehaviorSubject.create<RegionModel>()
    private val state = MutableLiveData<MainViewState>()
    private var subscription: Disposable? = null

    override fun onCleared() = subscription?.dispose() ?: Unit

    fun onQueryTextChange(query: String?) = querySubject.onNext(query ?: DEFAULT_QUERY)

    fun onRegionChanged(regionModel: RegionModel) = regionSubject.onNext(regionModel)

    fun state(): LiveData<MainViewState> = state

    fun subscribe() {
        subscription = Observable.combineLatest(
            searchObservable(),
            regionObservable()
        ) { query, region -> SearchEntity(query, toRegionEntity(region)) }
            .switchMap(mainInteractor::searchPhotos)
            .map {
                if (it.isEmpty()) MainViewState.Empty
                else MainViewState.Data(it.map(::toPhotoModel))
            }
            .onErrorReturn {
                it.printStackTrace()
                MainViewState.Error
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                state.value = it
            }
    }

    private fun searchObservable(): Observable<String> {
        return querySubject
            .distinctUntilChanged()
            .debounce { Observable.timer(SEARCH_QUERY_DELAY, TimeUnit.MILLISECONDS) }
    }

    private fun regionObservable(): Observable<RegionModel> {
        return regionSubject
            .distinctUntilChanged()
            .debounce { Observable.timer(REGION_DELAY, TimeUnit.MILLISECONDS) }
    }

    private fun toRegionEntity(regionModel: RegionModel): RegionEntity {
        return RegionEntity(
            minLongitude = regionModel.minLongitude,
            minLatitude = regionModel.minLatitude,
            maxLongitude = regionModel.maxLongitude,
            maxLatitude = regionModel.maxLatitude
        )
    }

    private fun toPhotoModel(photoEntity: PhotoEntity): PhotoModel {
        return PhotoModel(
            lat = photoEntity.lat,
            lon = photoEntity.lon,
            urlSmall = photoEntity.urlSmall,
            urlMedium = photoEntity.urlMedium,
            title = photoEntity.title,
            tags = photoEntity.tags
        )
    }

    private companion object {
        private const val SEARCH_QUERY_DELAY = 600L
        private const val REGION_DELAY = 300L
    }
}