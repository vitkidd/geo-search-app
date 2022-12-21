package com.github.vitkidd.geosearch.feature.presentation.mapper

import com.github.vitkidd.geosearch.feature.domain.entity.SearchEntity
import com.github.vitkidd.geosearch.feature.presentation.model.RegionModel

class SearchEntityMapper(
    private val regionEntityMapper: RegionEntityMapper,
) {

    fun map(query: String, region: RegionModel) =
        SearchEntity(query, regionEntityMapper.map(region))
}