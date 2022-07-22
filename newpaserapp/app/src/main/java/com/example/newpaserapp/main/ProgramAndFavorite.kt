package com.example.newpaserapp.main

import com.example.newpaserapp.db.entity.TransportStreamProgramDescriptor
import com.example.newpaserapp.main.live.recycler.SelectStatus

/**
 * @author xxm
 * 2022/7/22
 **/
class ProgramAndFavorite {
    lateinit var programNumber: String
    lateinit var programName: String
    lateinit var fileName: String
    var favorite: Boolean = false

    fun setProgramDescriptor(
        descriptor:
        TransportStreamProgramDescriptor
    ) {
        programName = descriptor.programName
        programNumber = descriptor.programNumber.toString()
        fileName = descriptor.fileName
    }

    fun setSelectStatus(selectStatus: SelectStatus) {
        programNumber = selectStatus.favoriteProgram
        favorite = selectStatus.status
    }
}