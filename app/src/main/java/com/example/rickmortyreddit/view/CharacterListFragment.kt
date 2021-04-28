package com.example.rickmortyreddit.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmortyreddit.MainActivity
import com.example.rickmortyreddit.databinding.CharacterLayoutBinding
import com.example.rickmortyreddit.databinding.CharactersFragmentLayoutBinding
import com.example.rickmortyreddit.model.CharacterResponse
import com.example.rickmortyreddit.model.CharacterResult
import com.example.rickmortyreddit.model.Data
import com.example.rickmortyreddit.model.RepositoryImpl
import com.example.rickmortyreddit.utility.DI
import com.example.rickmortyreddit.viewmodel.CharacterViewModel

class CharacterListFragment: Fragment() {

    interface OpenDetails{
        fun openDetailCharacter(data: CharacterResult)
        fun updateLoading(isLoading: Boolean)
        fun retryData()
    }

    private val viewModel by lazy {
        CharacterViewModel.CharacterViewmodelProvider(DI.provideRepository()).create(CharacterViewModel::class.java)
    }

    private lateinit var listener: OpenDetails

    private lateinit var binding: CharactersFragmentLayoutBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when(context){
            is OpenDetails -> listener = context
            else -> throw  ExceptionInInitializerError("Incorrect Activity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = CharactersFragmentLayoutBinding.inflate(inflater)
        initViews()
        initObservers()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getCharacters()
    }

    private fun initObservers() {
        viewModel.characterLiveData.observe(viewLifecycleOwner){
            when(it){
                is RepositoryImpl.AppState.SUCCESS -> updateAdapter(it.data)
                is RepositoryImpl.AppState.ERROR -> showError(it.errorMessage)
                is RepositoryImpl.AppState.LOADING -> isLoading()
            }
        }
    }

    private fun isLoading() {
        listener.updateLoading(true)
    }

    private fun showError(errorMessage: Data) {
        listener.updateLoading(false)
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun updateAdapter(data: CharacterResponse?) {
        listener.updateLoading(false)
        binding.characterList.adapter = CharacterAdapter(data, listener)
    }

    private fun initViews() {
        binding.characterList.layoutManager = LinearLayoutManager(context)
    }

}