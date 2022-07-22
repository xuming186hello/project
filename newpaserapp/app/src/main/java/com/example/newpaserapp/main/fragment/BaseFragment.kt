package com.example.newpaserapp.main.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @author xxm
 * 2022/7/21
 **/
abstract class BaseFragment(context: Context) : Fragment() {
    private var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initView(
            inflater,
            container
        )
    }

    abstract fun initView(inflater: LayoutInflater, container: ViewGroup?): View

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initListener()
    }

    abstract fun initData()

    abstract fun initListener()

    init {
        mContext = context
    }
}