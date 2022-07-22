package com.example.newpaserapp.start

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newpaserapp.R
import com.example.newpaserapp.databinding.ActivityStartBinding
import com.example.newpaserapp.main.MainActivity
import com.example.newpaserapp.start.adpter.FileAdapter
import com.xxm.tsparseapp.base.BaseActivity
import com.xxm.tsparseapp.start.StartViewModel


class StartActivity : BaseActivity() {
    private lateinit var mPopupWindow: PopupWindow

    lateinit var mStartViewModel: StartViewModel
    lateinit var mParseStatsViewModel: StartParseViewModel

    lateinit var mBinding: ActivityStartBinding

    private var mTypeface: Typeface? = null

    override fun initView() {
        mBinding =
            DataBindingUtil.setContentView<ActivityStartBinding>(this, R.layout.activity_start)
        mTypeface = Typeface.createFromAsset(assets, "fonts/Roboto-Medium.ttf")
        mBinding.topBar.setTextFont(mTypeface!!)
        mStartViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(StartViewModel::class.java)
        mParseStatsViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(StartParseViewModel::class.java)

        mBinding.listViewSelectFile.layoutManager = LinearLayoutManager(
            baseContext, LinearLayoutManager.VERTICAL, false
        )
    }

    override fun initData() {
        mStartViewModel.loadTsFile(externalCacheDir)
    }

    override fun initListener() {
        var fileAdapter = FileAdapter()
        mBinding.listViewSelectFile.adapter = fileAdapter
        mStartViewModel.dataList.observe(
            this, object : Observer<ArrayList<String>> {
                override fun onChanged(t: ArrayList<String>?) {
                    if (t != null) {
                        fileAdapter.setData(t)
                    }
                }

            }
        )

        fileAdapter?.setItemClick(object : FileAdapter.ItemEven {
            override fun onItemClick(fileNmae: String, position: Int) {
                showLoading()
                mParseStatsViewModel.parseTsFile(fileNmae)
            }

        })

        mParseStatsViewModel.parseStatus.observe(this, object : Observer<Boolean> {
            override fun onChanged(t: Boolean?) {
                if (t == true) {
                    Log.i(TAG, "onChanged: 完成")
                    mPopupWindow.dismiss()
                    mParseStatsViewModel.setValue(false)

                    Intent()
                    startActivity(Intent(baseContext, MainActivity::class.java))

                }
            }
        })
    }

    private fun showLoading() {
        mPopupWindow = PopupWindow()
        mPopupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT
        mPopupWindow.width = ViewGroup.LayoutParams.WRAP_CONTENT
        mPopupWindow.isFocusable = true
        @SuppressLint("InflateParams") val view = LayoutInflater
            .from(this).inflate(R.layout.start_load_popuwindow, null)
        val tip = view.findViewById<TextView>(R.id.textview_start_popu_window_tip)
        tip.typeface = mTypeface
        mPopupWindow.contentView = view
        mPopupWindow.showAtLocation(window.decorView, Gravity.CENTER, 0, 0)
    }

    companion object {
        const val TAG = "StartActivity"
    }
}


