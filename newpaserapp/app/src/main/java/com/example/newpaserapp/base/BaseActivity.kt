package com.xxm.tsparseapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author xxm
 * 2022/7/20
 **/
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
        initListener()
    }

    /**
     * 加载view
     */
    protected abstract fun initView()

    /**
     * 加载数据
     */
    protected abstract fun initData()

    /**
     * 加载事件
     */
    protected abstract fun initListener()
}