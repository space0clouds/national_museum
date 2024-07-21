package clouds.space.museum.national.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import clouds.space.museum.national.model.NationalMuseumObject
import clouds.space.museum.national.ui.designsystem.component.NationalMuseumImage
import clouds.space.museum.national.ui.designsystem.theme.MaterialColors
import clouds.space.museum.national.ui.designsystem.theme.get
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CollectionDetailRoute(
    id: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CollectionDetailViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getCollectionDetail(id)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner = LocalLifecycleOwner.current)

    CollectionDetailScreen(uiState, onBackClick, modifier)
}

@Composable
private fun CollectionDetailScreen(
    uiState: CollectionDetailUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        Box(Modifier.fillMaxSize()) {
            when (uiState) {
                CollectionDetailUiState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                is CollectionDetailUiState.Success -> {
                    Content(onBackClick, uiState.collection)
                }
                CollectionDetailUiState.Error -> {
                    Text(
                        text = "죄송해요 오류가 발생했어요 :(",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

internal val TopAppBarHeight: Dp = 56.dp

@Composable
private fun TopAppBar(onBackClick: () -> Unit) {
    Box(Modifier.fillMaxWidth().height(TopAppBarHeight)) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart),
        ) {
            Icon(
                Icons.AutoMirrored.Rounded.ArrowBack,
                "뒤로 가기",
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Composable
private fun Content(
    onBackClick: () -> Unit,
    collection: NationalMuseumObject,
) = with(collection) {
    Column(Modifier.background(Color.White).fillMaxSize()) {
        TopAppBar(onBackClick)

        Column(Modifier.weight(1F).verticalScroll(rememberScrollState())) {
            Box(
                modifier = Modifier.background(MaterialColors.Gray[100])
                    .fillMaxWidth()
                    .aspectRatio(1F)
            ) {
                if (imageUrls.isNotEmpty()) {
                    HorizontalPager(rememberPagerState { imageUrls.size }) { page ->
                        NationalMuseumImage(
                            imageUrl = imageUrls[page],
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                } else {
                    NationalMuseumImage(thumbnailUrl)
                }
            }

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(name, style = MaterialTheme.typography.headlineSmall)

                if (!description.isNullOrEmpty()) {
                    Text(description, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
internal fun CollectionDetailScreenContentPreview() {
    MaterialTheme {
        Content(
            onBackClick = {},
            collection = NationalMuseumObject(
                id = "id",
                name = "감로도",
                creator = null,
                description = "감로도는 중생들에게 감로甘露 즉 이슬과 같은 법문을 베풀어 해탈시키는 의식의 모습을 그린 불화다. 『우란분경于蘭盆經』에서는 부처의 수제자인 목련존자目連尊者가 아귀도에서 먹지 못하는 고통에 빠진 어머니를 구하기 위해 부처에게 방법을 묻고 그대로 행함으로써 어머니를 구했다고 한다. 감로도는 억울하게 죽은 모든 영혼이 부처의 가르침을 깨달아 다음 생에서는 좋은 모습으로 태어나기를 기원하는 목적에 의해서 많이 제작되었다. 대부분의 감로도에 등장하는 음식이 차려진 제단과 법회장면이 이 그림에서는 생략되었지만, 그림 아래 부분에 전생의 업을 표현한 고통 받는 중생의 모습을 더욱 확대해서 묘사했다. 중생들이 받는 고통은 중앙에 커다랗게 등장시킨 아귀상에 집약하여 나타냈는데, 위쪽에 표현된 부처들은 이를 구제하기 위해 인간 세상에 신속히 내려오는 듯 한 속도감 있는 모습으로 표현되었다.",
                thumbnailUrl = "https://picsum.photos/200",
                imageUrls = buildList(5) {
                    repeat(5) {
                        add("https://picsum.photos/200")
                    }
                },
            )
        )
    }
}