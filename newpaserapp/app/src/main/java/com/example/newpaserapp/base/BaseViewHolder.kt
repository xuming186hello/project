package com.xxm.tsparseapp.base

import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @author xxm
 * 2022/7/20
 **/
class BaseViewHolder(@NonNull viewDataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    private var mBinding: ViewDataBinding

    fun getBinding(): ViewDataBinding {
        return mBinding
    }

    init {
        mBinding = viewDataBinding
    }
}