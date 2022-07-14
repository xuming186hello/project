package com.tosmart.xiexuming.myapplication.presenter;

import android.content.Context;

import com.tosmart.xiexuming.myapplication.callback.FavoriteCallback;
import com.tosmart.xiexuming.myapplication.favorite.FavoriteActivity;
import com.tosmart.xiexuming.myapplication.model.Program;
import com.tosmart.xiexuming.myapplication.reposity.DataRepository;

import java.util.ArrayList;

/**
 * @author xxm
 * 2022.7.14
 **/
public class FavoritePresenter {
    private Context mContext;

    public FavoritePresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void updateFavoriteList(FavoriteActivity.Updatable callback) {
        DataRepository.getInstance(mContext).getFavoriteProgram(new FavoriteCallback() {
            @Override
            public void displayFavoriteList(ArrayList<Program> favorites) {
                callback.updateFavorites(favorites);
            }
        });
    }
}
