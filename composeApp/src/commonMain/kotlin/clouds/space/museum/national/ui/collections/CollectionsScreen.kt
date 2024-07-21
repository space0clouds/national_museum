package clouds.space.museum.national.ui.collections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import clouds.space.museum.national.model.NationalMuseumObject
import clouds.space.museum.national.ui.designsystem.component.NationalMuseumImage
import clouds.space.museum.national.ui.designsystem.component.TextShape
import coil3.compose.AsyncImagePainter
import nationalmuseum.composeapp.generated.resources.Res
import nationalmuseum.composeapp.generated.resources.national_treasure
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CollectionsRoute(
    onCollectionClick: (id: String) -> Unit,
    viewModel: CollectionsViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val collections by viewModel.collections.collectAsStateWithLifecycle(lifecycleOwner = LocalLifecycleOwner.current)
    val nationalTreasureCollections by viewModel.nationalTreasureCollections.collectAsStateWithLifecycle(lifecycleOwner = LocalLifecycleOwner.current)

    Box(modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(nationalTreasureCollections) { collection ->
                NationalTreasureCollectionItem(collection, onCollectionClick)
            }
        }
    }
}

@Composable
internal fun NationalTreasureCollectionItem(
    collection: NationalMuseumObject,
    onClick: (id: String) -> Unit,
    modifier: Modifier = Modifier
) = with(collection) {
    CollectionItem(
        thumbnailUrl,
        stringResource(Res.string.national_treasure),
        name,
        onClick = { onClick(id) },
        modifier
    )
}

@Composable
internal fun CollectionItem(
    imageUrl: String,
    creator: String?,
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier.clickable(onClick = onClick)) {
        NationalMuseumImage(
            imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            horizontalAlignment = Alignment.End,
        ) {
            creator?.let {
                Text(
                    it,
                    modifier = Modifier
                        .background(Color.Black)
                        .padding(horizontal = 4.dp),
                    color = Color.White
                )
            }

            var textShape by remember { mutableStateOf<TextShape?>(null) }

            Text(
                name,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .background(Color.Black, textShape ?: RectangleShape),
                color = Color.White,
                textAlign = TextAlign.End,
                maxLines = 2,
                onTextLayout = { textLayoutResult ->
                    textShape = TextShape(textLayoutResult)
                }
            )
        }
    }
}