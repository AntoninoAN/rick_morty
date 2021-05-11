package com.example.rickmortyreddit.di.modules

import com.example.rickmortyreddit.model.Repository
import com.example.rickmortyreddit.model.RepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideRepository(): Repository = RepositoryImpl()
}