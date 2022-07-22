package com.example.newpaserapp.main

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.newpaserapp.R
import com.example.newpaserapp.databinding.ActivityMainBinding
import com.xxm.tsparseapp.base.BaseActivity
import com.example.newpaserapp.main.fragment.AboutFragment
import com.example.newpaserapp.main.fragment.ViewPageFragmentAdapter
import com.example.newpaserapp.main.live.LiveFragment

/**
 * @author xxm
 * 2022/7/21
 **/
class MainActivity : BaseActivity() {
    private lateinit var mMainActivityBinding: ActivityMainBinding
    private lateinit var mMainViewModel: MainViewModel

    override fun initView() {
        mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mMainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)

        mMainActivityBinding.bottomIconLeft.isSelected = true
        mMainActivityBinding.bottomIconRight.isSelected = false
    }

    override fun initData() {
        initPage()
    }

    private fun initPage() {
        val liveFragment = LiveFragment(this)
        val aboutFragment = AboutFragment(this)
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(liveFragment)
        fragmentList.add(aboutFragment)
        val fragmentAdapter =
            ViewPageFragmentAdapter(supportFragmentManager, lifecycle, fragmentList)
        mMainActivityBinding.viewpageMain.adapter = fragmentAdapter
        mMainActivityBinding.viewpageMain.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> {
                            mMainActivityBinding.bottomIconLeft.isSelected = true
                            mMainActivityBinding.bottomIconRight.isSelected = false
                        }
                        1 -> {
                            mMainActivityBinding.bottomIconRight.isSelected = true
                            mMainActivityBinding.bottomIconLeft.isSelected = false
                        }
                    }
                }
            }

        )
    }

    override fun initListener() {
        mMainActivityBinding.bottomIconLeft.setOnClickListener {
            mMainActivityBinding.viewpageMain.currentItem = 0
        }
        mMainActivityBinding.bottomIconRight.setOnClickListener {
            mMainActivityBinding.viewpageMain.currentItem = 1
        }
    }
}