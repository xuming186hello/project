package com.example.newpaserapp.start.adpter

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.newpaserapp.BR

/**
 * @author xxm
 * 2022/7/21
 **/
class TsFileName : BaseObservable() {
    var fileName: String? = ""

    @Bindable
    fun getName(): String {
        return fileName ?: ""
    }

    fun setName(name: String) {
        fileName = name
        notifyPropertyChanged(BR.name)
    }
}