package com.example.teamprojectapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class DdayViewModel : ViewModel() {
    private val repository = DdayRepository()

    private val _ddays = MutableLiveData<ArrayList<Dday>>()
    val ddays : LiveData<ArrayList<Dday>> = _ddays

    fun retrieveDdays() {
        viewModelScope.launch {

        }
    }
}