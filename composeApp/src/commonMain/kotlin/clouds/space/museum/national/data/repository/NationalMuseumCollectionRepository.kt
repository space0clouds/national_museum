package clouds.space.museum.national.data.repository

import clouds.space.museum.national.data.mapper.NationalMuseumObjectDetailMapper
import clouds.space.museum.national.data.mapper.NationalMuseumObjectMapper
import clouds.space.museum.national.data.network.api.NationalMuseumApi
import clouds.space.museum.national.data.network.model.NetworkNationalMuseumObject
import clouds.space.museum.national.domain.repository.NationalMuseumCollectionRepository
import clouds.space.museum.national.model.NationalMuseumObject
import clouds.space.museum.national.utils.debug
import co.touchlab.kermit.Logger
import io.kamel.core.mapper.Mapper

class NationalMuseumCollectionDataRepository(
    private val nationalMuseumApi: NationalMuseumApi,
) : NationalMuseumCollectionRepository {

    override suspend fun getCollections(): List<NationalMuseumObject> {
        return nationalMuseumApi.getCollections(1, 100)
            .data
            .mapNotNull {
                try {
                    NationalMuseumObjectMapper.map(it)
                } catch (e: Exception) {
                    Logger.debug(e) { "map failed" }
                    null
                }
            }
    }

    override suspend fun getNationalTreasureCollections(): List<NationalMuseumObject> {
        return nationalMuseumApi.search(1, 100, "PS12001") // TODO
            .data
            .mapNotNull {
                try {
                    NationalMuseumObjectMapper.map(it)
                } catch (e: Exception) {
                    null
                }
            }
    }

    override suspend fun getCollectionDetail(id: String): NationalMuseumObject =
        nationalMuseumApi.getDetail(id)
            .let(NationalMuseumObjectDetailMapper::map)
}