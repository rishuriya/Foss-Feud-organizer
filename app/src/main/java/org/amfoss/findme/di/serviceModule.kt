package org.amfoss.findme.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.amfoss.findme.service.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @get:Provides
    @get:Singleton
    val apiService: ApiService = Retrofit.Builder()
        .baseUrl("https://geoeditiors.toolforge.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiService::class.java)
}
