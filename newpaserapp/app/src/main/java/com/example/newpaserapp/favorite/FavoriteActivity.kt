package com.example.newpaserapp.favorite

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newpaserapp.R
import com.example.newpaserapp.databinding.ActivityFavoriteBinding
import com.example.newpaserapp.main.MainActivity
import com.example.newpaserapp.main.ProgramAndFavorite

/**
 * @author xxm
 * 2022/7/22
 **/
class FavoriteActivity : com.xxm.tsparseapp.base.BaseActivity() {
    private lateinit var mActivityFavoriteBinding: ActivityFavoriteBinding
    private lateinit var mFavoriteViewModel: FavoriteViewModel
    private lateinit var mFavoriteAdapter: FavoriteAdapter

    override fun initView() {
        mActivityFavoriteBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_favorite)

        mFavoriteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FavoriteViewModel::class.java)
    }

    override fun initData() {
        mFavoriteViewModel.getFavoriteProgram()

        mFavoriteAdapter = FavoriteAdapter()
        mActivityFavoriteBinding.rvFavourite.adapter = mFavoriteAdapter
        mActivityFavoriteBinding.rvFavourite.layoutManager = GridLayoutManager(
            this, SPAN_COUNT
        )
    }

    override fun initListener() {
        mFavoriteViewModel.favoriteProgram.observe(this, object :
            Observer<ArrayList<ProgramAndFavorite>> {
            override fun onChanged(t: ArrayList<ProgramAndFavorite>?) {
                mFavoriteAdapter.setData(t!!)
            }
        })

        mActivityFavoriteBinding.topBarFavorite.setBackIconEvent(object : IconBackEvent {
            override fun back() {
                var intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
            }

        })
    }

    interface IconBackEvent {
        fun back()
    }

    companion object {
        const val SPAN_COUNT = 3
    }
}