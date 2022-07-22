package com.example.newpaserapp.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newpaserapp.main.ProgramAndFavorite
import com.example.newpaserapp.model.FavoriteModel
import kotlinx.coroutines.launch

/**
 * @author xxm
 * 2022/7/22
 **/
class FavoriteViewModel : ViewModel() {
    private var _favoriteProgram = MutableLiveData<ArrayList<ProgramAndFavorite>>()
    val favoriteProgram: LiveData<ArrayList<ProgramAndFavorite>> = _favoriteProgram

    private var mFavoriteModel: FavoriteModel = FavoriteModel()

    fun getFavoriteProgram(){
        viewModelScope.launch {
            _favoriteProgram.value = mFavoriteModel.getFavoriteProgram()
        }
    }
}