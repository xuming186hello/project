package com.example.newpaserapp.main.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.newpaserapp.R
import com.example.newpaserapp.databinding.FragmentAboutBinding

/**
 * @author xxm
 * 2022/7/21
 **/
class AboutFragment(context: Context) : BaseFragment(context) {
    private lateinit var mAboutFragmentBinding: FragmentAboutBinding

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        mAboutFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_about, container, false
        )
        var view = mAboutFragmentBinding.root
        return view
    }

    override fun initData() {

    }

    override fun initListener() {

    }
}