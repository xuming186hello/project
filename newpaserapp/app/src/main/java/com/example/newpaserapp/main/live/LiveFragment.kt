package com.example.newpaserapp.main.live

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newpaserapp.R
import com.example.newpaserapp.databinding.FragmentLiveBinding
import com.example.newpaserapp.main.FavoriteStatusViewModel
import com.example.newpaserapp.main.MainViewModel
import com.example.newpaserapp.main.ProgramAndFavorite
import com.example.newpaserapp.favorite.FavoriteActivity
import com.example.newpaserapp.main.fragment.BaseFragment
import com.example.newpaserapp.main.live.recycler.ProgramAdapter
import com.example.newpaserapp.main.live.recycler.SelectStatus

/**
 * @author xxm
 * 2022/7/21
 **/
class LiveFragment(context: Context) : BaseFragment(context) {
    lateinit var mLiveFragmentBinding: FragmentLiveBinding
    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mFavoriteStatusViewModel: FavoriteStatusViewModel

    private var mContext: Context
    private lateinit var mRecyclerAdapter: ProgramAdapter

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        mLiveFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_live, container, false
        )
        mMainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)
        mFavoriteStatusViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FavoriteStatusViewModel::class.java)

        mLiveFragmentBinding.rvChannel.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )

        var view = mLiveFragmentBinding.root
        return view
    }

    override fun initData() {
        mMainViewModel.getProgramList()
        mRecyclerAdapter = ProgramAdapter()
        mLiveFragmentBinding.rvChannel.adapter = mRecyclerAdapter
    }

    override fun initListener() {
        mMainViewModel.programList.observe(this,
            object : Observer<ArrayList<ProgramAndFavorite>> {
                override fun onChanged(t: ArrayList<ProgramAndFavorite>?) {
                    t?.let { mRecyclerAdapter.setData(it) }
                }

            })

        mRecyclerAdapter.setItemClick(object : ProgramAdapter.ItemEven {
            override fun onClick(position: Int, loveStatus: Boolean?, programNumber: String) {
                if (!loveStatus!!) {
                    mFavoriteStatusViewModel.addFavorite(programNumber, position)
                } else {
                    mFavoriteStatusViewModel.deleteFavorite(programNumber, position)
                }
            }
        })

        mFavoriteStatusViewModel.favoriteNumber.observe(this, object :
            Observer<SelectStatus> {
            override fun onChanged(t: SelectStatus?) {
                mRecyclerAdapter.setFavoriteStatus(t!!)
            }
        })

        mLiveFragmentBinding.imageFavorite.setOnClickListener {
            val intent = Intent(mContext, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }

    init {
        mContext = context
    }

    companion object {
        const val TAG = "LIVE FRAGMENT: "
    }
}