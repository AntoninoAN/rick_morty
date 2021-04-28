package com.example.rickmortyreddit.model

import com.example.rickmortyreddit.model.remote.CharacterApi

class RepositoryImpl : Repository {

    override suspend fun getCharacters(): AppState {
        val response =
            CharacterApi.getCharacterApi().getCharacters()
        return if (response.isSuccessful) {
            if (response.body() != null) AppState.SUCCESS(response.body()!!)
            else AppState.SUCCESS()
        } else
            AppState.ERROR(response.message())
    }

    sealed class AppState {
        data class SUCCESS(val data: CharacterResponse? = null) : AppState()
        data class ERROR(val errorMessage: Data) : AppState()
        object LOADING : AppState()
    }
}