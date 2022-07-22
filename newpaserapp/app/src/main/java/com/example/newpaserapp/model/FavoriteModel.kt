package com.example.newpaserapp.model

import com.example.newpaserapp.db.entity.Favorite
import com.example.newpaserapp.main.ProgramAndFavorite
import com.example.newpaserapp.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * @author xxm
 * 2022/7/21
 **/
class FavoriteModel {
    private var mDataRepository: DataRepository

    suspend fun addFavorite(number: String): Boolean {
        val job = withContext(Dispatchers.IO) {
            async {
                mDataRepository.addFavorite(convertFavorite(number))
            }
        }
        return job.await()
    }

    suspend fun removeFavorite(number: String): Boolean {
        val job = withContext(Dispatchers.IO) {
            async {
                mDataRepository.removeFavorite(convertFavorite(number))
            }
        }
        return job.await()
    }

    private fun convertFavorite(number: String): Favorite {
        return Favorite(number.toInt(), mDataRepository.getFileName())
    }

    suspend fun getFavoriteProgram(): ArrayList<ProgramAndFavorite> {
        val job = withContext(Dispatchers.IO) {
            async {
                mDataRepository.getFavoriteProgram()
            }
        }
        return job.await()
    }

    init {
        mDataRepository = DataRepository.getSec()!!
    }
}