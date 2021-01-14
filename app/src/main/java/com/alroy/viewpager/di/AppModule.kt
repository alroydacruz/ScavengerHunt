package com.alroy.viewpager.di

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import com.alroy.viewpager.data.DataStoreRepository
import com.alroy.viewpager.data.PREFERENCE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataStoreDatabase(
        @ApplicationContext context: Context
    ) = context.createDataStore(
        name = PREFERENCE_NAME
    )

    @Singleton
    @Provides
    fun provideDataStoreDatabaseRepository(
        dataStore : DataStore<Preferences>
    ) = DataStoreRepository(dataStore)

}















