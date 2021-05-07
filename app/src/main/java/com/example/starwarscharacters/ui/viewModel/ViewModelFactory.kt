package com.example.starwarscharacters.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.starwarscharacters.domain.Repository

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return modelClass.getConstructor(Repository::class.java).newInstance(repository)
    }
}