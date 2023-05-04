package org.amfoss.findme.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import org.amfoss.findme.Repository.FossRepository
import org.amfoss.findme.Repository.FossRepositoryImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class FossRepositoryModule {
    @Binds
    abstract fun bindsFossRepository(repository: FossRepositoryImpl?): FossRepository?
}