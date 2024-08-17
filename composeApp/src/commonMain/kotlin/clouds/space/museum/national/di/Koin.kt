package clouds.space.museum.national.di

import clouds.space.museum.national.BuildKonfig
import clouds.space.museum.national.data.network.api.KtorNationalMuseumApi
import clouds.space.museum.national.data.network.api.NATIONAL_MUSEUM_HOST
import clouds.space.museum.national.data.network.api.NATIONAL_MUSEUM_SERVICE_KEY
import clouds.space.museum.national.data.network.api.NationalMuseumApi
import clouds.space.museum.national.data.repository.NationalMuseumCollectionDataRepository
import clouds.space.museum.national.domain.repository.NationalMuseumCollectionRepository
import clouds.space.museum.national.domain.usecase.GetCollectionDetailUseCase
import clouds.space.museum.national.domain.usecase.GetCollectionsUseCase
import clouds.space.museum.national.domain.usecase.GetNationalTreasureCollectionsUseCase
import clouds.space.museum.national.ui.collections.CollectionsViewModel
import clouds.space.museum.national.ui.detail.CollectionDetailViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

// TODO: Json, Service_Key
val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(get())
            }

            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTP

                    host = NATIONAL_MUSEUM_HOST

                    parameters.append(NATIONAL_MUSEUM_SERVICE_KEY, BuildKonfig.apiKey)
                }

                header(HttpHeaders.Accept, ContentType.Application.Json)
            }

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }

    singleOf(::KtorNationalMuseumApi) {
        bind<NationalMuseumApi>()
    }
}

val repositoryModule = module {
    singleOf(::NationalMuseumCollectionDataRepository) {
        bind<NationalMuseumCollectionRepository>()
    }
}

val coroutinesModule = module {
    single(qualifier = named("main")) { Dispatchers.Main }
    single(qualifier = named("io")) { Dispatchers.IO }
    single(qualifier = named("default")) { Dispatchers.Default }
}

val dataModule = module {
    includes(networkModule)
    includes(repositoryModule)
}

val domainModule = module {
    single { GetCollectionsUseCase(get(), get(named("io"))) }
    single { GetNationalTreasureCollectionsUseCase(get(), get(named("io"))) }
    single { GetCollectionDetailUseCase(get(), get(named("io"))) }
}

val uiModule = module {
    viewModelOf(::CollectionsViewModel)
    viewModelOf(::CollectionDetailViewModel)
}

val appModules = module {
    includes(coroutinesModule)
    includes(dataModule)
    includes(domainModule)
    includes(uiModule)
}