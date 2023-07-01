package com.aamirashraf.janitriassignment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aamirashraf.janitriassignment.repository.ColorDetailsRepository

class ColorDetailsViewModelProviderFactory(val colorDetailsRepository: ColorDetailsRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ColorDetailsViewModel(colorDetailsRepository) as T
    }
}