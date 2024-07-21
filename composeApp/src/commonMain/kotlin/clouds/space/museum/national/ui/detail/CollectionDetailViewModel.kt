package clouds.space.museum.national.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import clouds.space.museum.national.domain.usecase.GetCollectionDetailUseCase
import clouds.space.museum.national.model.NationalMuseumObject
import clouds.space.museum.national.utils.debug
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionDetailViewModel(
    private val getCollectionDetailUseCase: GetCollectionDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CollectionDetailUiState>(CollectionDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getCollectionDetail(id: String) {
        viewModelScope.launch {
            val collection = try {
                getCollectionDetailUseCase(id)
            } catch (e: Exception) {
                Logger.debug(e) { "getCollectionDetail failed" }
                null
            }

            _uiState.update {
                if (collection != null) {
                    CollectionDetailUiState.Success(collection)
                } else {
                    CollectionDetailUiState.Error
                }
            }
        }
    }
}

sealed interface CollectionDetailUiState {
    data object Loading : CollectionDetailUiState
    data class Success(val collection: NationalMuseumObject) : CollectionDetailUiState
    data object Error : CollectionDetailUiState
}