package clouds.space.museum.national.domain.usecase

import clouds.space.museum.national.domain.repository.NationalMuseumCollectionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetCollectionDetailUseCase(
    private val repository: NationalMuseumCollectionRepository,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(id: String) = withContext(dispatcher) {
        repository.getCollectionDetail(id)
    }
}