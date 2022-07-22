package com.xxm.tsparseapp.start

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File

/**
 * @author xxm
 * 2022/7/20
 **/
class FileUtil {
    private var mFileMap: HashMap<String, String>? = null
    private var mFileList: ArrayList<String>? = null


    fun getFilePath(fileName: String): String? {
        return if (mFileMap!!.containsKey(fileName)) {
            mFileMap!![fileName]
        } else null
    }

    suspend fun requestFileList(file: File?): ArrayList<String>? {
        var job = withContext(Dispatchers.IO) {
            async {
                mFileMap = HashMap(0)
                mFileList = ArrayList()
                file?.let { updateFileList(file) }
                mFileList
            }
        }
        return job.await()
    }

    private fun updateFileList(rootDir: File) {
        if (!rootDir.isDirectory) {
            val path = rootDir.path
            val substring = path.substring(path.lastIndexOf(".") + 1)
            if (substring == TS_FILE_SUFFIX) {
                val fileName = path.substring(path.lastIndexOf("/") + 1)
                mFileList!!.add(fileName)
                mFileMap!![fileName] = path
            }
            return
        }
        val files = rootDir.listFiles() ?: return
        for (file in files) {
            if (file.isDirectory) {
                updateFileList(file)
            } else {
                val path = file.path
                val substring = path.substring(path.lastIndexOf(".") + 1)
                if (substring == TS_FILE_SUFFIX) {
                    val fileName = path.substring(path.lastIndexOf("/") + 1)
                    mFileMap!![fileName] = path
                    mFileList!!.add(fileName)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var instance: FileUtil? = null

        private const val TS_FILE_SUFFIX = "ts"

        @JvmStatic
        fun getInstance(): FileUtil? {
            if (instance == null) {
                synchronized(FileUtil::class.java) { instance = FileUtil() }
            }
            return instance
        }
    }
}