package clouds.space.museum.national.data.network.api

import clouds.space.museum.national.data.network.model.NetworkNationalMuseumObject
import clouds.space.museum.national.data.network.model.NetworkNationalMuseumObjectDetail
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Inject

interface NationalMuseumApi {

    suspend fun search(page: Int, pageSize: Int, designationCode: String): GetCollectionsResponse

    suspend fun getCollections(page: Int, pageSize: Int): GetCollectionsResponse

    suspend fun getDetail(id: String, page: Int = 1, pageSize: Int = 50): GetDetailResponse

    @Serializable
    sealed interface NationalMuseumResponse<T> {
        val page: Int?
        val pageSize: Int?
        val totalCount: Int
        val resultCode: String
        val resultMessage: String
        val data: List<T>
    }

    @Serializable
    data class GetCollectionsResponse(
        @SerialName("pageNo")
        override val page: Int,
        @SerialName("numOfRows")
        override val pageSize: Int,
        override val totalCount: Int,
        override val resultCode: String,
        @SerialName("resultMsg")
        override val resultMessage: String,
        @SerialName("list")
        override val data: List<NetworkNationalMuseumObject>,
    ) : NationalMuseumResponse<NetworkNationalMuseumObject>

    @Serializable
    data class GetDetailResponse(
        @SerialName("pageNo")
        override val page: Int?,
        @SerialName("numOfRows")
        override val pageSize: Int?,
        override val totalCount: Int,
        override val resultCode: String,
        @SerialName("resultMsg")
        override val resultMessage: String,
        @SerialName("list")
        override val data: List<NetworkNationalMuseumObjectDetail>,
        @SerialName("imageList") val images: Images,
        @SerialName("relationList") val relatedArts: RelatedArts,
    ) : NationalMuseumResponse<NetworkNationalMuseumObjectDetail> {

        @Serializable
        data class Images(
            val totalCount: Int,
            @SerialName("list") val data: List<Image>
        )

        @Serializable
        data class Image(
            @SerialName("id") val artId: String,
            @SerialName("imgId") val id: String,
            @SerialName("imgOrder") val order: Int,
            @SerialName("imgUri") val url: String,
            @SerialName("imgThumUriS") val smallThumbnailUrl: String,
            @SerialName("imgThumUriM") val mediumThumbnailUrl: String,
            @SerialName("imgThumUriL") val largeThumbnailUrl: String,
        )

        @Serializable
        data class RelatedArts(
            val totalCount: Int,
            @SerialName("list") val data: List<RelatedArt>
        )

        @Serializable
        data class RelatedArt(
            @SerialName("id") val artId: String,
            @SerialName("reltId") val id: String,
            @SerialName("reltOrder") val order: Int,
            @SerialName("reltMuseumCode1") val museumCode1: String? = null,
            @SerialName("reltMuseumCode2") val museumCode2: String? = null,
            @SerialName("reltMuseumFullName") val museumName: String? = null,
            @SerialName("reltRelicName") val name: String,
            @SerialName("reltRelicNo") val inventoryNumber: String? = null,
            @SerialName("reltRelicSubNo") val inventorySubNumber: String? = null,
            @SerialName("reltImgUri") val imageUrl: String? = null,
            @SerialName("reltImgThumUriS") val smallThumbnailUrl: String? = null,
            @SerialName("reltImgThumUriM") val mediumThumbnailUrl: String? = null,
        )
    }
}

const val NATIONAL_MUSEUM_HOST = "www.emuseum.go.kr"
const val NATIONAL_MUSEUM_PATH = "openapi/relic"
const val NATIONAL_MUSEUM_SERVICE_KEY = "serviceKey"

class KtorNationalMuseumApi(private val client: HttpClient) : NationalMuseumApi {

    companion object {
        private const val COLLECTION = "list"
        private const val DETAIL = "detail"
        private const val PAGE = "pageNo"
        private const val PAGE_SIZE = "numOfRows"
        private const val ID = "id"
        private const val DESIGNATION_CODE = "designationCode"
    }

    override suspend fun search(
        page: Int,
        pageSize: Int,
        designationCode: String
    ): NationalMuseumApi.GetCollectionsResponse {
        return client
            .get("$NATIONAL_MUSEUM_PATH/$COLLECTION?$PAGE=$page&$PAGE_SIZE=$pageSize&designationCode=$designationCode") // TODO
            .body<NationalMuseumApi.GetCollectionsResponse>()
    }

    override suspend fun getCollections(
        page: Int,
        pageSize: Int
    ): NationalMuseumApi.GetCollectionsResponse {
        return client
            .get("$NATIONAL_MUSEUM_PATH/$COLLECTION?$PAGE=$page&$PAGE_SIZE=$pageSize")
            .body<NationalMuseumApi.GetCollectionsResponse>()
    }

    override suspend fun getDetail(
        id: String,
        page: Int,
        pageSize: Int
    ): NationalMuseumApi.GetDetailResponse {
        return client
            .get("$NATIONAL_MUSEUM_PATH/$DETAIL?$ID=$id&$PAGE=$page&$PAGE_SIZE=$pageSize")
            .body<NationalMuseumApi.GetDetailResponse>()
    }
}