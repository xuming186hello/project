package com.example.newpaserapp.main.live.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newpaserapp.BR
import com.example.newpaserapp.R
import com.example.newpaserapp.databinding.ItemviewLiveChannelBinding
import com.example.newpaserapp.main.ProgramAndFavorite

/**
 * @author xxm
 * 2022/7/21
 **/
class ProgramAdapter() : RecyclerView.Adapter<ChannelViewHolder>() {
    private var mDataList: ArrayList<ProgramAndFavorite>? = null
    private var mItemClick: ItemEven? = null

    private lateinit var mViewHolder: ChannelViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        var binding = DataBindingUtil.inflate<ItemviewLiveChannelBinding>(
            LayoutInflater.from(parent.context),
            R.layout.itemview_live_channel,
            parent,
            false
        )
        mViewHolder = ChannelViewHolder(binding)
        return mViewHolder
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        initBindingView(holder, position)
        initBindingListener(holder, position)
    }

    private fun initBindingView(holder: ChannelViewHolder, position: Int) {
        var descript: ProgramAndFavorite? = mDataList?.get(position)
        var programItem = ProgramItem()
        if (descript != null) {
            programItem.programNumber = descript.programNumber
        }
        programItem.programName = descript?.programName
        holder.getBinding().setVariable(BR.liveChannel, programItem)

        holder.getBinding().imageLiveAddFav.isSelected = descript!!.favorite
    }

    private fun initBindingListener(holder: ChannelViewHolder, position: Int) {
        holder.getBinding().imageLiveAddFav.setOnClickListener {
            if (mItemClick != null) {
                mItemClick!!.onClick(
                    position,
                    holder.getBinding().imageLiveAddFav.isSelected,
                    holder.getBinding().textViewChannelNumber.text.toString()
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return mDataList?.size ?: 0
    }

    fun setItemClick(clickEvent: ItemEven) {
        mItemClick = clickEvent
    }

    fun setData(dataList: ArrayList<ProgramAndFavorite>) {
        mDataList = dataList
        notifyDataSetChanged()
    }

    fun setFavoriteStatus(selectStatus: SelectStatus) {
        var data = mDataList?.get(selectStatus.position)
        data?.setSelectStatus(selectStatus)
        notifyItemChanged(selectStatus.position)
    }

    interface ItemEven {
        fun onClick(position: Int, loveStatus: Boolean?, programNumber: String)
    }
}