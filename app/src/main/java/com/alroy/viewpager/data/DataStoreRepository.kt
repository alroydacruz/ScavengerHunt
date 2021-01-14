package com.alroy.viewpager.data

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

const val PREFERENCE_NAME = "scavanger_preffs"


class DataStoreRepository @Inject  constructor(var dataStore :DataStore<Preferences>) {

        private object PreferenceKeys {
            val currentLevel = preferencesKey<Int>("currentLevel")
            val currentBranch = preferencesKey<Int>("currentBranch")
        }

        suspend fun updateCurrentLevel(currentLevel: Int) {
            dataStore.edit { preferences ->
                preferences[PreferenceKeys.currentLevel] = currentLevel
            }
        }

        suspend fun updateChosenBranch(currentBranch: Int) {
            dataStore.edit { preferences ->
                preferences[PreferenceKeys.currentBranch] = currentBranch
            }
        }


        val currentLevelFlow: Flow<Int> = dataStore.data
            .catch { e ->
                if (e is IOException) {
                    Log.d("nigger", e.message.toString())
                    emit(emptyPreferences())
                } else {
                    throw e
                }
            }.map { preferences ->
                preferences[PreferenceKeys.currentLevel] ?: 0
            }

        val currentBranchFlow: Flow<Int> = dataStore.data
            .catch { e ->
                if (e is IOException) {
                    Log.d("nigger", e.message.toString())
                    emit(emptyPreferences())
                } else {
                    throw e
                }
            }.map { preferences ->
                preferences[PreferenceKeys.currentBranch] ?: 0
            }
    }