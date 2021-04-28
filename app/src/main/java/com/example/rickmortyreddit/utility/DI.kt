package com.example.rickmortyreddit.utility

import com.example.rickmortyreddit.model.Repository
import com.example.rickmortyreddit.model.RepositoryImpl

object DI {
    fun provideRepository(): Repository = RepositoryImpl()
}