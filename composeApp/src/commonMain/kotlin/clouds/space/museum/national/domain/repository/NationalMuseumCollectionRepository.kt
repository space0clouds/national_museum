package clouds.space.museum.national.domain.repository

import clouds.space.museum.national.model.NationalMuseumObject
import me.tatarka.inject.annotations.Inject

interface NationalMuseumCollectionRepository {

    suspend fun getCollections(): List<NationalMuseumObject>

    suspend fun getNationalTreasureCollections(): List<NationalMuseumObject>

    suspend fun getCollectionDetail(id: String): NationalMuseumObject
}