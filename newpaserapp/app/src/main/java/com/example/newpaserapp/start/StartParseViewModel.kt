package com.example.newpaserapp.start

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
class StartParseViewModel : ViewModel() {
    private var _parseStatus = MutableLiveData<Boolean>()
    var parseStatus: LiveData<Boolean> = _parseStatus

    private var mProgramModel: ProgramModel

    fun parseTsFile(fileName: String) {
        viewModelScope.launch {
            _parseStatus.value = mProgramModel.parseTsFile(fileName)
        }
    }

    fun setValue(boolean: Boolean) {
        _parseStatus.value = boolean
    }

    init {
        mProgramModel = ProgramModel()
    }
}