package com.josephhopson.hiringapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josephhopson.hiringapp.data.HiringApiResult
import com.josephhopson.hiringapp.data.HiringItem
import com.josephhopson.hiringapp.data.HiringRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * the current UI state for the home screen
 * @property currentState initialized as Landing
 */
data class HomeUiState(
    val currentState: HomeUiStates = HomeUiStates.Landing
)

/**
 * UI states for the HomeScreen
 * 4 possible states for the homeScreen UI: Success with data, Error, Loading, Landing.
 * Landing is what is used to represent a fresh UI State, like when the app is started.
 */
sealed interface HomeUiStates {
    data class Success(val items: Map<Int, List<HiringItem>>) : HomeUiStates
    data object Error : HomeUiStates
    data object Loading : HomeUiStates
    data object Landing : HomeUiStates
}

/**
 * ViewModel for the HomeScreen
 * @param [repository] the DI Injected image repository used to get the images
 */
class HomeViewModel(private val repository: HiringRepository) : ViewModel() {

    // backing properties to avoid state updates from other classes
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /**
     * on Init, get the hiring results from the repository and updates the UI State
     */
    init {
        _uiState.value = HomeUiState(
            currentState = HomeUiStates.Loading
        )
        viewModelScope.launch {
            _uiState.value = HomeUiState(
                currentState = getHiringResultsFromRepository()
            )
        }
    }

    /**
     * Get hiring results from repository
     * @return [HomeUiStates] the api result wrapped in a sealed class
     */
    private suspend fun getHiringResultsFromRepository(): HomeUiStates {
        return when (
            val result = repository.getHiringResults()
        ) {
            is HiringApiResult.Success -> {
                HomeUiStates.Success(result.items)
            }
            is HiringApiResult.Error -> HomeUiStates.Error
        }
    }

}