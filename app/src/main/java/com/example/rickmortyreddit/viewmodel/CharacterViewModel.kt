package com.example.rickmortyreddit.viewmodel

import androidx.lifecycle.*
import com.example.rickmortyreddit.model.Repository
import com.example.rickmortyreddit.model.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterViewModel(private val repository: Repository): ViewModel() {

    class CharacterViewmodelProvider(private val repository: Repository): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharacterViewModel(repository) as T
        }
    }

    private val mutableCharacterData = MutableLiveData<RepositoryImpl.AppState>()
    val characterLiveData: LiveData<RepositoryImpl.AppState>
    get() = mutableCharacterData

    fun getCharacters(page: Int = 1){
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getCharacters(page)
            withContext(Dispatchers.Main){
                mutableCharacterData.value = data
            }
        }
    }
}