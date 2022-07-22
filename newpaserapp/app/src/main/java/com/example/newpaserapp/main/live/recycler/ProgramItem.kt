package com.example.newpaserapp.main.live.recycler

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.newpaserapp.BR

/**
 * @author xxm
 * 2022/7/21
 **/
class ProgramItem : BaseObservable() {
    var programName: String? = ""
    var programNumber: String? = ""

    @Bindable
    fun getNumber(): String {
        return programNumber ?: ""
    }

    @Bindable
    fun getName(): String {
        return programName ?: ""
    }

    fun setName(programName: String) {
        this.programName = programName
        notifyPropertyChanged(BR.number)
    }

    fun setNumber(programNumber: Int) {
        this.programNumber = programNumber.toString()
        notifyPropertyChanged(BR.name)
    }
}