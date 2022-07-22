package com.example.newpaserapp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newpaserapp.model.ProgramModel
import kotlinx.coroutines.launch

/**
 * @author xxm
 * 2022/7/21
 **/
class MainViewModel : ViewModel() {
    private var _programList = MutableLiveData<ArrayList<ProgramAndFavorite>>()
    var programList: LiveData<ArrayList<ProgramAndFavorite>> = _programList

    private var mViewModel: ProgramModel = ProgramModel()

    fun getProgramList() {
        viewModelScope.launch {
            _programList.value = mViewModel.getProgramList()
        }
    }
}