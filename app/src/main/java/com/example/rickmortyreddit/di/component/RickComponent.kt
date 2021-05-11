package com.example.rickmortyreddit.di.component

import com.example.rickmortyreddit.di.modules.RepositoryModule
import com.example.rickmortyreddit.view.CharacterListFragment
import dagger.Component

@Component(modules = [RepositoryModule::class])
interface RickComponent {
    fun inject(fragment: CharacterListFragment)
}