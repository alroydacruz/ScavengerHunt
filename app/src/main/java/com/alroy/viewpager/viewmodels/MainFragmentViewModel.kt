package com.alroy.viewpager.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.alroy.viewpager.models.FireBaseModel
import com.alroy.viewpager.repository.Repository
import com.alroy.viewpager.util.Resource
import kotlinx.coroutines.launch


class MainFragmentViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    var currentLevel = repository.currentLevelFlow.asLiveData()
    var currentBranch = repository.currentBranchFlow.asLiveData()

//    private val _proceededToNextLevelStatus = MutableLiveData<Resource<FireBaseModel>>()
//    val proceededToNextLevelStatus: LiveData<Resource<FireBaseModel>> = _proceededToNextLevelStatus

    fun proceedToNextLevel(currentLevel: Int, currentBranch: Int) {
        viewModelScope.launch {
          repository.updateCurrentLevelAndBranchToFirebase(currentLevel, currentBranch)
//            _proceededToNextLevelStatus.postValue(response)
        }
    }

    fun updateCurrentLevel(currentLevel: Int) =
        viewModelScope.launch { repository.updateCurrentLevel(currentLevel)
        }

    fun updateCurrentBranch(currentBranch: Int) =
        viewModelScope.launch { repository.updateChosenBranch(currentBranch) }
}
