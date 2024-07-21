package clouds.space.museum.national.ui.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import clouds.space.museum.national.domain.usecase.GetCollectionsUseCase
import clouds.space.museum.national.domain.usecase.GetNationalTreasureCollectionsUseCase
import clouds.space.museum.national.utils.debug
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class CollectionsViewModel(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val getNationalTreasureCollectionsUseCase: GetNationalTreasureCollectionsUseCase
) : ViewModel() {

    val collections = flow {
        emit(getCollectionsUseCase())
    }.catch {
        emit(emptyList())
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val nationalTreasureCollections = flow {
        emit(getNationalTreasureCollectionsUseCase())
    }.catch {
        emit(emptyList())
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}