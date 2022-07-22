package com.example.newpaserapp

import android.app.Application
import com.example.newpaserapp.repository.DataRepository

/**
 * @author xxm
 * 2022/7/21
 **/
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataRepository.getInstance(this)
    }
}