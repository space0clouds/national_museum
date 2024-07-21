package clouds.space.museum.national.data.mapper

import clouds.space.museum.national.data.network.api.NationalMuseumApi
import clouds.space.museum.national.data.network.model.NetworkNationalMuseumObject
import clouds.space.museum.national.data.network.model.NetworkNationalMuseumObjectDetail
import clouds.space.museum.national.model.NationalMuseumObject
import io.kamel.core.mapper.Mapper
import kotlin.reflect.KClass

internal val NationalMuseumObjectMapper = object : Mapper<NetworkNationalMuseumObject, NationalMuseumObject> {
    override val inputKClass: KClass<NetworkNationalMuseumObject>
        get() = NetworkNationalMuseumObject::class
    override val outputKClass: KClass<NationalMuseumObject>
        get() = NationalMuseumObject::class

    override fun map(input: NetworkNationalMuseumObject): NationalMuseumObject = with(input) {
        NationalMuseumObject(
            id = id,
            name = nameKR ?: name,
            creator = creator,
            description = null,
            thumbnailUrl = imageUrl,
            imageUrls = emptyList(),
        )
    }
}

internal val NationalMuseumObjectDetailMapper = object : Mapper<NationalMuseumApi.GetDetailResponse, NationalMuseumObject> {
    override val inputKClass: KClass<NationalMuseumApi.GetDetailResponse>
        get() = NationalMuseumApi.GetDetailResponse::class
    override val outputKClass: KClass<NationalMuseumObject>
        get() = NationalMuseumObject::class

    override fun map(input: NationalMuseumApi.GetDetailResponse): NationalMuseumObject = with(input) {
        with(data.first()) {
            NationalMuseumObject(
                id = id,
                name = nameKR ?: name,
                creator = creator,
                description = description,
                thumbnailUrl = imageUrl,
                imageUrls = images.data.map { it.url },
            )
        }
    }
}