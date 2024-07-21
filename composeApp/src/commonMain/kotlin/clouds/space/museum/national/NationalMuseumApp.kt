package clouds.space.museum.national

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import clouds.space.museum.national.ui.navigation.NationalMuseumNavigationHost
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.network.ktor.KtorNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import okio.FileSystem
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun NationalMuseumApp() {
    MaterialTheme {
        KoinContext {
            setSingletonImageLoaderFactory { context ->
                context.imageLoader()
            }

            CompositionLocalProvider(LocalIndication provides NoIndication) {
                NationalMuseumNavigationHost()
            }
        }
    }
}

private fun PlatformContext.imageLoader(): ImageLoader = ImageLoader.Builder(this)
    .components {
        add(KtorNetworkFetcherFactory())
    }
    .memoryCache {
        MemoryCache.Builder()
            .maxSizePercent(this, 0.25)
            .build()
    }
    .diskCache {
        DiskCache.Builder()
            .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
            .build()
    }
    .logger(DebugLogger())
    .crossfade(false)
    .build()

private object NoIndication : Indication {
    private object NoIndicationInstance : IndicationInstance {
        override fun ContentDrawScope.drawIndication() {
            drawContent()
        }
    }

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        return NoIndicationInstance
    }
}