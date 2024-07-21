package clouds.space.museum.national.domain.usecase

import clouds.space.museum.national.domain.repository.NationalMuseumCollectionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject

class GetCollectionsUseCase(
    private val repository: NationalMuseumCollectionRepository,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke() = withContext(dispatcher) {
        repository.getCollections()
    }
}