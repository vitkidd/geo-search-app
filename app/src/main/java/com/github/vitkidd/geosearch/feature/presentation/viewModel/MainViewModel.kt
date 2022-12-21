package com.github.vitkidd.geosearch.feature.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.vitkidd.geosearch.feature.presentation.model.RegionModel
import com.github.vitkidd.geosearch.feature.di.DEFAULT_QUERY
import com.github.vitkidd.geosearch.feature.domain.interactor.MainInteractor
import com.github.vitkidd.geosearch.feature.domain.entity.SearchEntity
import com.github.vitkidd.geosearch.feature.presentation.mapper.PhotoModelMapper
import com.github.vitkidd.geosearch.feature.presentation.mapper.RegionEntityMapper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val mainInteractor: MainInteractor,
    private val photoModelMapper: PhotoModelMapper,
    private val regionEntityMapper: RegionEntityMapper,
) : ViewModel() {

    private val querySubject = BehaviorSubject.createDefault(DEFAULT_QUERY)
    private val regionSubject = BehaviorSubject.create<RegionModel>()
    private var subscription: Disposable? = null
    private val _state = MutableLiveData<MainViewState>()
    val state: LiveData<MainViewState> get() = _state

    override fun onCleared() = subscription?.dispose() ?: Unit

    fun onViewEvent(event: MainViewEvent) {
        when (event) {
            is OnQueryTextChanged -> handleQueryChanged(event)
            is OnRegionChanged -> handleRegionChanged(event)
            OnViewCreated -> subscribe()
        }
    }

    private fun handleQueryChanged(event: OnQueryTextChanged) {
        querySubject.onNext(event.query ?: DEFAULT_QUERY)
    }

    private fun handleRegionChanged(event: OnRegionChanged) {
        regionSubject.onNext(event.regionModel)
    }

    private fun subscribe() {
        subscription = Observable.combineLatest(
            searchObservable(),
            regionObservable()
        ) { query, region -> SearchEntity(query, regionEntityMapper.map(region)) }
            .switchMap(mainInteractor::searchPhotos)
            .map {
                if (it.isEmpty()) MainViewState.Empty
                else MainViewState.Data(it.map(photoModelMapper::map))
            }
            .onErrorReturn {
                it.printStackTrace()
                MainViewState.Error
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _state.value = it
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

    private companion object {
        private const val SEARCH_QUERY_DELAY = 600L
        private const val REGION_DELAY = 300L
    }
}