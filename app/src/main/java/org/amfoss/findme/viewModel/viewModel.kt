package org.amfoss.findme.viewModel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.amfoss.findme.Model.GameRound
import org.amfoss.findme.Model.Participant
import org.amfoss.findme.Model.registerRound
import org.amfoss.findme.Model.updateRound
import org.amfoss.findme.Repository.FossRepository
import org.amfoss.findme.util.Resource

@HiltViewModel
class FossViewModel @Inject constructor(
    val repository: FossRepository,
    private val application: Application
) : AndroidViewModel(application) {
    private val _totalRound = MutableStateFlow(listOf<GameRound>())
    val totalRound = _totalRound.asStateFlow()
    private val _user = MutableStateFlow(listOf<Participant>())
    val user = _user.asStateFlow()
    var isLoading: Boolean  by mutableStateOf(true)
    fun fetchRounds() {
        viewModelScope.launch(Dispatchers.Default) {
            val response = repository.fetchGameRounds()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    val responseListens = response.data!!
                    _totalRound.value = responseListens
                    Log.d("FossViewModel", "fetchRoun: ${_totalRound.value}")
                }
                Resource.Status.LOADING -> {
                    isLoading = true
                }
                Resource.Status.FAILED -> {
                    isLoading = false
                    Log.d("FossViewModel", "fetchRounds: ${response.status}")
                }
            }
        }
    }

    fun fetchUser() {
        viewModelScope.launch(Dispatchers.Default) {
            val response = repository.fetchUser()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    val responseListens = response.data!!
                    _user.value = responseListens
                    Log.d("FossViewModel", "fetchRoun: ${_user.value}")
                }
                Resource.Status.LOADING -> {
                    isLoading = true
                }
                Resource.Status.FAILED -> {
                    isLoading = false
                    Log.d("FossViewModel", "fetchRounds: ${response.status}")
                }
            }
        }
    }

    fun addRound(round: registerRound) {
        viewModelScope.launch(Dispatchers.Default) {
            val response = repository.addRound(round)
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    val responseListens = response.data!!
                    Log.d("FossViewModel", "fetchRoun: $responseListens")
                }
                Resource.Status.LOADING -> {
                    isLoading = true
                }
                Resource.Status.FAILED -> {
                    isLoading = false
                    Log.d("FossViewModel", "fetchRounds: ${response.status}")
                }
            }
        }
    }

    fun updateRound(id:Int,round: updateRound) {
        viewModelScope.launch(Dispatchers.Default) {
            val response = repository.updateRound(id, round)
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    val responseListens = response.data!!
                    Log.d("FossViewModel", "fetchRoun: $responseListens")
                }
                Resource.Status.LOADING -> {
                    isLoading = true
                }
                Resource.Status.FAILED -> {
                    isLoading = false
                    Log.d("FossViewModel", "fetchRounds: ${response.status}")
                }
            }
        }
    }
}
