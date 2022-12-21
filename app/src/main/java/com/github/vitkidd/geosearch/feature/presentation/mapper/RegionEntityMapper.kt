package com.github.vitkidd.geosearch.feature.presentation.mapper

import com.github.vitkidd.geosearch.feature.domain.entity.RegionEntity
import com.github.vitkidd.geosearch.feature.presentation.model.RegionModel

class RegionEntityMapper {

    fun map(regionModel: RegionModel) = RegionEntity(
        minLongitude = regionModel.minLongitude,
        minLatitude = regionModel.minLatitude,
        maxLongitude = regionModel.maxLongitude,
        maxLatitude = regionModel.maxLatitude
    )
}