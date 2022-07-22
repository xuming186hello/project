package com.xxm.tsparseapp.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.File

/**
 * @author xxm
 * 2022/7/20
 **/
class StartViewModel : ViewModel() {
    private var _fileList = MutableLiveData<ArrayList<String>>()
    val dataList: LiveData<ArrayList<String>> = _fileList

    fun loadTsFile(file: File?) {
        viewModelScope.launch {
            _fileList.value = FileUtil.getInstance()?.requestFileList(file)
        }
    }

    fun getList(): ArrayList<String>? {
        return _fileList.value
    }
}