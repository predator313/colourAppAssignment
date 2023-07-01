package com.aamirashraf.janitriassignment.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aamirashraf.janitriassignment.model.ColorDetails
import com.aamirashraf.janitriassignment.repository.ColorDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ColorDetailsViewModel(
    val colorDetailsRepository: ColorDetailsRepository
):ViewModel() {
    val colorList=MutableLiveData<List<ColorDetails>>()
    fun insert(colorDetails: ColorDetails)=viewModelScope.launch {
        colorDetailsRepository.insert(colorDetails)
    }
    fun delete(colorDetails: ColorDetails)=viewModelScope.launch {
        colorDetailsRepository.delete(colorDetails)
    }
    fun fetchColorList(){
        viewModelScope.launch(Dispatchers.IO) {
            val data=colorDetailsRepository.fetch()
            colorList.postValue(data)
        }

    }
    //for count
    val userCount:LiveData<Int> = colorDetailsRepository.getCount()
}