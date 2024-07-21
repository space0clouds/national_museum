package clouds.space.museum.national.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkNationalMuseumObject(
    val id: String,
    val name: String,
    @SerialName("nameKr") val nameKR: String? = null,
    @SerialName("nameCn") val nameCN: String? = null,
    @SerialName("author") val creator: String? = null,
    val museumCode: String,
    val nationalityCode: String,
    val materialCode: String? = null,
    val purposeCode: String? = null,
    val sizeRangeCode: String? = null,
    val placeLandCode: String? = null,
    val designationCode: String? = null,
    @SerialName("indexWord") val keyword: String? = null,
    @SerialName("glsv") val publicDomainType: Int,
    @SerialName("relicNo") val inventoryNumber: String,
    @SerialName("relicSubNo") val inventorySubNumber: String,
    val museumCode1: String,
    val museumName1: String,
    val museumCode2: String,
    val museumName2: String,
    val museumCode3: String,
    val museumName3: String,
    @SerialName("imgUri") val imageUrl: String,
    @SerialName("imgThumUriS") val smallThumbnailUrl: String,
    @SerialName("imgThumUriM") val mediumThumbnailUrl: String,
    @SerialName("imgThumUriL") val largeThumbnailUrl: String,
)

