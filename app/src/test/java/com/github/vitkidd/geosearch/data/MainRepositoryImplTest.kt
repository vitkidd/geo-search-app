package com.github.vitkidd.geosearch.data

import com.github.vitkidd.geosearch.BaseTest
import com.github.vitkidd.geosearch.feature.data.network.MainApi
import com.github.vitkidd.geosearch.feature.data.repo.MainRepositoryImpl
import com.github.vitkidd.geosearch.searchResultDto
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Test

internal class MainRepositoryImplTest: BaseTest() {

    private lateinit var repository: MainRepositoryImpl
    private val api: MainApi = mock()
    private val region = "4.63144775480032,52.376537420804254,4.640275910496712,52.38637202434913"
    private val query = "motorcycle"
    private val searchResultDto = searchResultDto()

    override fun setUp() {
        super.setUp()

        repository = MainRepositoryImpl(api)
    }

    @Test
    fun searchPhotos_success() {
        whenever(api.search(any(), any())).thenReturn(Observable.just(searchResultDto))

        repository.searchPhotos(query, region).test().apply {
            assertSubscribed()
            assertComplete()
            assertValue(searchResultDto.photos.photo)
            assertNoErrors()
        }
    }

    @Test
    fun searchPhotos_failed() {
        val exceptionStub = Exception()
        whenever(api.search(any(), any())).thenReturn(Observable.error(exceptionStub))

        repository.searchPhotos(query, region).test().apply {
            assertSubscribed()
            assertNotComplete()
            assert(errorCount() == 1)
            assertNoValues()
            assertError(exceptionStub)
        }
    }
}