package com.example.newpaserapp.main.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author xxm
 * 2022/7/21
 **/
class ViewPageFragmentAdapter : FragmentStateAdapter {
    private var mFragmentList: ArrayList<Fragment>

    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        fragmentList: ArrayList<Fragment>
    ) : super(
        fragmentManager,
        lifecycle
    ) {
        mFragmentList = fragmentList


    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }
}