package com.eloinavarro.holocron.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eloinavarro.holocron.data.SWCharacterRepository
import com.eloinavarro.holocron.data.retrofit.SwapiRetrofitDatasource
import com.eloinavarro.holocron.domain.SWCharacter
import com.eloinavarro.holocron.domain.usecase.GetCharactersUseCase
import com.eloinavarro.holocron.ui.common.SwPaginator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    //TODO: Add Hilt and change this
    private val getCharacters = GetCharactersUseCase(
        repository = SWCharacterRepository(
            apiDatasource = SwapiRetrofitDatasource()
        )
    )

    var uiStateFlow = MutableStateFlow(UIState())
        private set

    private val onLoadUpdated: (Boolean) -> Unit = { isUpdated ->
        uiStateFlow.update { it.copy(loading = isUpdated) }
    }
    private val onRequest: suspend (Int) -> Result<List<SWCharacter>> = { nextPage ->
        getCharacters(nextPage)
    }
    private val getNextPage: suspend (Int) -> Int = { currentKey ->
        currentKey + 1
    }
    private val onError: suspend (Throwable?) -> Unit = { error ->
        uiStateFlow.update {
            it.copy(
                error = error?.localizedMessage
            )
        }
    }
    private val onSuccess: suspend (List<SWCharacter>, Int) -> Unit = { newItems, newKey ->
        uiStateFlow.update {
            it.copy(
                characters = (uiStateFlow.value.characters + newItems),
                page = newKey,
                endReached = newItems.isEmpty()
            )
        }
    }

    private val paginator = SwPaginator(
        initialPage = uiStateFlow.value.page,
        isLoading = onLoadUpdated,
        onRequest = onRequest,
        getNextPage = getNextPage,
        onError = onError,
        onSuccess = onSuccess
    )

    fun loadNextPage() {
        viewModelScope.launch {
            paginator.loadNextPage()
        }
    }

    init {
        viewModelScope.launch {
            paginator.loadNextPage()
        }
    }

    data class UIState(
        val loading: Boolean = false,
        val characters: List<SWCharacter> = emptyList(),
        val error: String? = null,
        val endReached: Boolean = false,
        val page: Int = 1
    )
}

// Not needed here, just when ListViewModel has parameters
class ListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListViewModel() as T
    }
}