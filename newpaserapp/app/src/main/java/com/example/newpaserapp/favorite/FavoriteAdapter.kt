package com.example.newpaserapp.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newpaserapp.BR
import com.example.newpaserapp.R
import com.example.newpaserapp.databinding.ItemviewFavoriteProgramBinding
import com.example.newpaserapp.main.ProgramAndFavorite

/**
 * @author xxm
 * 2022/7/22
 **/
class FavoriteAdapter : RecyclerView.Adapter<FavoriteViewHolder>() {
    private var mDataList: ArrayList<ProgramAndFavorite>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        var binding = DataBindingUtil.inflate<ItemviewFavoriteProgramBinding>(
            LayoutInflater
                .from(parent.context), R.layout.itemview_favorite_program, parent, false
        )
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.getBinding().setVariable(BR.favoriteProgram, mDataList?.get(position))
    }

    override fun getItemCount(): Int {
        return mDataList?.size ?: 0
    }

    fun setData(programAndFavoriteList: ArrayList<ProgramAndFavorite>) {
        mDataList = programAndFavoriteList
        notifyDataSetChanged()
    }
}