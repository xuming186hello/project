package com.example.newpaserapp.favorite

import androidx.recyclerview.widget.RecyclerView
import com.example.newpaserapp.databinding.ItemviewFavoriteProgramBinding
import org.jetbrains.annotations.NotNull

/**
 * @author xxm
 * 2022/7/22
 **/
class FavoriteViewHolder(@NotNull itemFavoriteProgramBinding: ItemviewFavoriteProgramBinding) :
    RecyclerView.ViewHolder(itemFavoriteProgramBinding.root) {
    private val mBinding = itemFavoriteProgramBinding

    fun getBinding(): ItemviewFavoriteProgramBinding {
        return mBinding
    }
}