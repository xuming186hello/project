package com.xxm.tsparseapp.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author xxm
 * 2022/7/20
 **/
abstract class BaseRecycleAdapter<T>(dataList: ArrayList<T>, layoutId: Int) :
    RecyclerView.Adapter<com.xxm.tsparseapp.base.BaseViewHolder>() {
    private var mDataList: ArrayList<T>? = null
    private var mLayoutId: Int = 0

    private var mItemclick: com.xxm.tsparseapp.base.BaseRecycleAdapter.ItemEven? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.xxm.tsparseapp.base.BaseViewHolder {
        return initView(parent)
    }

    abstract fun initView(parent: ViewGroup): com.xxm.tsparseapp.base.BaseViewHolder

    override fun onBindViewHolder(holder: com.xxm.tsparseapp.base.BaseViewHolder, position: Int) {
        convert(holder, position, mDataList!![position])
    }

    /**
     * 子类初始化数据绑定等操作
     */
    abstract fun convert(holder: com.xxm.tsparseapp.base.BaseViewHolder, position: Int, any: T?)

    override fun getItemCount(): Int {
        return mDataList?.size ?: 0
    }

    fun setData(dataList: ArrayList<T>) {
        this.mDataList = dataList
        notifyDataSetChanged()
    }

    private fun setListener(viewHolder: com.xxm.tsparseapp.base.BaseViewHolder) {
        viewHolder.itemView.setOnClickListener { view ->
            mItemclick?.onItemClick(view, viewHolder, viewHolder.adapterPosition)
        }
    }

    open fun setItemClick(itemEven: com.xxm.tsparseapp.base.BaseRecycleAdapter.ItemEven) {
        mItemclick = itemEven
    }

    interface ItemEven {
        fun onItemClick(view: View?, holder: com.xxm.tsparseapp.base.BaseViewHolder?, position: Int)
    }

    init {
        mLayoutId = layoutId
        mDataList = dataList
    }
}