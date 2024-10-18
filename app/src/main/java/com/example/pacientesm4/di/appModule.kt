package com.example.pacientesm4.di

import android.content.Context
import com.example.pacientesm4.data.PacientesPreferences
import com.example.pacientesm4.data.PacientesPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext app: Context
    ): PacientesPreferences = PacientesPreferencesImpl(app)

}