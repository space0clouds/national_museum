package clouds.space.museum.national.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkNationalMuseumObjectDetail(
    val id: String,
    val name: String,
    @SerialName("nameKr") val nameKR: String? = null,
    @SerialName("nameCn") val nameCN: String? = null,
    @SerialName("author") val creator: String? = null,
    val museumCode: String? = null,
    // 국적 / 시대
    val nationalityCode: String? = null,
    val nationalityCode1: String? = null,
    val nationalityName1: String? = null,
    val nationalityCode2: String? = null,
    val nationalityName2: String? = null,
    // 재질
    val materialCode: String? = null,
    val materialCode1: String? = null,
    val materialName1: String? = null,
    val materialCode2: String? = null,
    val materialName2: String? = null,
    // 용도 분류
    val purposeCode: String? = null,
    val purposeCode1: String? = null,
    val purposeName1: String? = null,
    val purposeCode2: String? = null,
    val purposeName2: String? = null,
    val purposeCode3: String? = null,
    val purposeName3: String? = null,
    val purposeCode4: String? = null,
    val purposeName4: String? = null,
    // 크기
    val sizeRangeCode: String? = null,
    val sizeRangeName: String? = null,
    val sizeInfo: String? = null,
    // 출토지
    val placeLandCode: String? = null,
    val placeLandCode1: String? = null,
    val placeLandName1: String? = null,
    val placeLandCode2: String? = null,
    val placeLandName2: String? = null,
    // 지정문화재
    val designationCode: String? = null,
    val designationCode1: String? = null,
    val designationName1: String? = null,
    val designationCode2: String? = null,
    val designationName2: String? = null,
    val designationInfo: String? = null,
    @SerialName("desc") val description: String? = null,
    @SerialName("indexWord") val keyword: String? = null,
    @SerialName("glsv") val publicDomainType: Int? = null,
    @SerialName("relicNo") val artifactNumber: String? = null,
    @SerialName("relicSubNo") val artifactSubNumber: String? = null,
    val museumCode1: String? = null,
    val museumName1: String? = null,
    val museumCode2: String? = null,
    val museumName2: String? = null,
    val museumCode3: String? = null,
    val museumName3: String? = null,
    @SerialName("imgUri") val imageUrl: String,
    @SerialName("imgThumUriS") val smallThumbnailUrl: String,
    @SerialName("imgThumUriM") val mediumThumbnailUrl: String,
    @SerialName("imgThumUriL") val largeThumbnailUrl: String,
)
