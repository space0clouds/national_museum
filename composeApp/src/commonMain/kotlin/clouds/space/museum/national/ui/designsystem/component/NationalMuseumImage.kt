package clouds.space.museum.national.ui.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter.State
import coil3.compose.rememberAsyncImagePainter

@Composable
fun NationalMuseumImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    AsyncImage(
        imageUrl,
        contentDescription,
        modifier = modifier,
        contentScale = contentScale,
    )
}

@Composable
fun NationalMuseumImage(
    imageUrl: String,
    onLoading: @Composable () -> Unit,
    onError: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val painter = rememberAsyncImagePainter(imageUrl, contentScale = contentScale)

    Box(modifier) {
        when (painter.state) {
            State.Empty -> {}
            is State.Error -> onError()
            is State.Loading -> onLoading()
            is State.Success -> Image(painter, contentDescription)
        }
    }
}