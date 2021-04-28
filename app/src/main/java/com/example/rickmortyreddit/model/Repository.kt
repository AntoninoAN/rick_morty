package com.example.rickmortyreddit.model

interface Repository {
    suspend fun getCharacters(): RepositoryImpl.AppState
}