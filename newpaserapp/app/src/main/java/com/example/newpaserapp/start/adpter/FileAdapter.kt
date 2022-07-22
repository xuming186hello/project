package com.example.newpaserapp.start.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.newpaserapp.BR
import com.example.newpaserapp.R


/**
 * @author xxm
 * 2022/7/21
 **/
class FileAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mDataList: ArrayList<String>? = null
    private var mItemClick: ItemEven? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_file_name,
            parent,
            false
        )
        return com.xxm.tsparseapp.base.BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var fileName = TsFileName()
        fileName.setName(mDataList!![position])
        var fileHolder = holder as com.xxm.tsparseapp.base.BaseViewHolder
        fileHolder.getBinding().setVariable(BR.fileItem, fileName)
        fileHolder.getBinding().executePendingBindings()
        fileHolder.itemView.setOnClickListener {
            mItemClick?.onItemClick(mDataList!![position], position)
        }
    }

    override fun getItemCount(): Int {
        return mDataList?.size ?: 0
    }

    fun setData(fileList: ArrayList<String>) {
        mDataList = fileList
        notifyDataSetChanged()
    }

    fun setItemClick(itemEven: ItemEven) {
        mItemClick = itemEven
    }

    interface ItemEven {
        fun onItemClick(fileNmae: String, position: Int)
    }
}