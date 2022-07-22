package com.example.newpaserapp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newpaserapp.main.live.recycler.SelectStatus
import com.example.newpaserapp.model.FavoriteModel
import kotlinx.coroutines.launch

/**
 * @author xxm
 * 2022/7/22
 **/
class FavoriteStatusViewModel : ViewModel() {
    private var _favoriteNumber = MutableLiveData<SelectStatus>()
    val favoriteNumber: LiveData<SelectStatus> = _favoriteNumber

    private lateinit var mViewModel: FavoriteModel

    fun addFavorite(number: String, position: Int) {
        viewModelScope.launch {
            var status = mViewModel.addFavorite(number)
            var selectStatus = SelectStatus(position, status, number)
            _favoriteNumber.value = selectStatus
        }
    }

    fun deleteFavorite(number: String, position: Int) {
        viewModelScope.launch {
            var status = mViewModel.removeFavorite(number)
            var selectStatus = SelectStatus(position, status, number)
            _favoriteNumber.value = selectStatus
        }
    }

    init {
        mViewModel = FavoriteModel()
    }
}