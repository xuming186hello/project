package com.example.newpaserapp.repository

import Program
import TransportStreamManager
import android.content.Context
import com.example.newpaserapp.db.AppDataBase
import com.example.newpaserapp.db.entity.Favorite
import com.example.newpaserapp.db.entity.TransportStreamProgramDescriptor
import com.example.newpaserapp.main.ProgramAndFavorite

/**
 * @author xxm
 * 2022/7/22
 **/
class DataRepository private constructor(context: Context) {
    private lateinit var mTransportStreamManager: TransportStreamManager
    private var mAppDataBase: AppDataBase? = null

    private var mFileName: String = ""

    private var mSearchHistory: ArrayList<String>? = null
    private var mProgramList: ArrayList<ProgramAndFavorite>? = null
    private var mFavoriteList: ArrayList<Int>? = null

    fun getFileName(): String {
        return mFileName
    }

    fun checkParseData(filePath: String?): Boolean {
        mProgramList?.let {
            if (mFileName == filePath) {
                return true
            }
        }
        val dao = mAppDataBase?.programDescriptorDao()
        val programList = filePath?.let { dao?.queryALLProgram(it) }
                as ArrayList<TransportStreamProgramDescriptor>?
        mProgramList = ArrayList()
        programList.let {
            for (program in programList!!) {
                val programAndFavorite = ProgramAndFavorite()
                programAndFavorite.setProgramDescriptor(program)
                mProgramList!!.add(programAndFavorite)
            }
        }

        if (mProgramList != null && mProgramList!!.size != 0) {
            mFileName = filePath!!
            return true
        }

        val parseTsStream = mTransportStreamManager?.parseTsStream(filePath) as ArrayList
        parseTsStream?.let {
            var networkProgram: TransportStreamProgramDescriptor? = null
            for (program: Program in parseTsStream) {
                val programAndFavorite = ProgramAndFavorite()
                val descriptor =
                    TransportStreamProgramDescriptor(
                        programNumber = program.getProgramNumber(),
                        programName = program.getProgramName().toString(),
                        fileName = filePath.toString()
                    )
                if (program.getProgramNumber() == 0) {
                    networkProgram = descriptor
                } else {
                    programAndFavorite.setProgramDescriptor(descriptor)
                    mProgramList!!.add(programAndFavorite)
                }
                dao?.insertDescriptor(descriptor)
            }
            return true
        }
        return false
    }

    fun getProgramList(): ArrayList<ProgramAndFavorite>? {
        mProgramList?.let {
            setFavoriteProgram()
            return mProgramList
        }


        val dao = mAppDataBase?.programDescriptorDao()
        dao?.let {
            val programList = it.queryAll(mFileName) as ArrayList
            programList.let {
                for (program in programList) {
                    val programAndFavorite = ProgramAndFavorite()
                    programAndFavorite.setProgramDescriptor(program)
                    mProgramList!!.add(programAndFavorite)
                }
            }

            setFavoriteProgram()
            return mProgramList
        }
        return null
    }

    private fun setFavoriteProgram() {
        val favoriteList = getFavoriteList()
        favoriteList?.let {
            for (program in mProgramList!!) {
                val programNumber = program.programNumber.toInt()
                if (!favoriteList.contains(programNumber)) {
                    continue
                }
                program.favorite = true
            }
        }
    }

    private fun getFavoriteList(): List<Int>? {
        if (mFavoriteList != null && mFavoriteList!!.size > 0) {
            return mFavoriteList
        }
        val dao = mAppDataBase?.favoriteDao()
        if (dao != null) {
            return dao.queryAll(mFileName)
        }
        return null
    }

    fun addFavorite(favorite: Favorite): Boolean {
        favorite.let {
            val dao = mAppDataBase?.favoriteDao()
            dao?.insert(favorite)
            mFavoriteList?.add(favorite.programNumber)
            return true
        }
        return false
    }

    fun removeFavorite(favorite: Favorite): Boolean {
        favorite.let {
            val dao = mAppDataBase?.favoriteDao()
            dao?.delete(favorite)
            mFavoriteList?.remove(favorite.programNumber)
            return false
        }
        return true
    }

    fun getFavoriteProgram(): ArrayList<ProgramAndFavorite> {
        var favoriteProgramList = ArrayList<ProgramAndFavorite>()
        mProgramList?.let {
            for (favoriteProgram in it) {
                if (!favoriteProgram.favorite) {
                    continue
                }
                favoriteProgramList.add(favoriteProgram)
            }
        }

        return favoriteProgramList
    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        @JvmStatic
        fun getInstance(context: Context?): DataRepository? {
            instance?.let { return instance }
            synchronized(this) {
                if (context != null) {
                    instance = DataRepository(context)
                }
            }
            return instance
        }

        @JvmStatic
        fun getSec(): DataRepository? {
            return instance
        }
    }

    init {
        mTransportStreamManager = TransportStreamManager()
        mAppDataBase = AppDataBase.getDataBase(context)
        mProgramList = ArrayList()
    }

}