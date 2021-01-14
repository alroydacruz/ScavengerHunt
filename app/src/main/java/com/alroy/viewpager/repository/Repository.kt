package com.alroy.viewpager.repository

import android.app.Application

import com.alroy.viewpager.data.DataStoreRepository
import com.alroy.viewpager.data.FirebaseFireStore
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataStore: DataStoreRepository,
) {

    var currentLevelFlow = dataStore.currentLevelFlow
    var currentBranchFlow = dataStore.currentBranchFlow

    suspend fun updateCurrentLevelAndBranchToFirebase(currentLevel: Int, currentBranch: Int) =
        FirebaseFireStore.updateCurrentLevelAndBranchToFirebase(currentLevel, currentBranch)

    suspend  fun updateCurrentLevel(currentLevel: Int) = dataStore.updateCurrentLevel(currentLevel)

    suspend fun updateChosenBranch(currentBranch: Int) = dataStore.updateChosenBranch(currentBranch)

}