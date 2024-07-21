package clouds.space.museum.national.model

data class NationalMuseumObject(
    val id: String,
    val name: String,
    val creator: String?,
    val description: String?,
    val thumbnailUrl: String,
    val imageUrls: List<String>,
)