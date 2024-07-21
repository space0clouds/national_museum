package clouds.space.museum.national.ui.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter.State

@Composable
fun NationalMuseumImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    onState: ((State) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    AsyncImage(
        imageUrl,
        contentDescription,
        modifier = modifier,
        onState = onState,
        contentScale = contentScale,
    )
}