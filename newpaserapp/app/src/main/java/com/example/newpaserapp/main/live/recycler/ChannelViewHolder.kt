package com.example.newpaserapp.main.live.recycler

import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.newpaserapp.databinding.ItemviewLiveChannelBinding

/**
 * @author xxm
 * 2022/7/22
 **/
class ChannelViewHolder(@NonNull binding: ItemviewLiveChannelBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private var mBinding: ItemviewLiveChannelBinding

    fun getBinding(): ItemviewLiveChannelBinding {
        return mBinding
    }

    init {
        mBinding = binding
    }
}