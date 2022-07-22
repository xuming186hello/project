package com.example.newpaserapp.model

import com.example.newpaserapp.main.ProgramAndFavorite
import com.example.newpaserapp.repository.DataRepository
import com.xxm.tsparseapp.start.FileUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * @author xxm
 * 2022/7/21
 **/
class ProgramModel() {
    private var mDataRepository: DataRepository

    suspend fun parseTsFile(fileName: String): Boolean {
        var job = withContext(Dispatchers.IO) {
            async {
                val filePath = FileUtil.getInstance()?.getFilePath(fileName)
                mDataRepository.checkParseData(filePath)
            }
        }
        return job.await()
    }

    suspend fun getProgramList(): ArrayList<ProgramAndFavorite>? {
        var job = withContext(Dispatchers.IO) {
            async {
                mDataRepository.getProgramList()
            }
        }
        return job.await()
    }

    init {
        mDataRepository = DataRepository.getSec()!!
    }
}