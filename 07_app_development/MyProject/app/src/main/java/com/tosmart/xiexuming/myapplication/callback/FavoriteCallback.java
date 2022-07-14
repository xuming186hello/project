package com.tosmart.xiexuming.myapplication.callback;


import com.tosmart.xiexuming.myapplication.model.Program;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public interface FavoriteCallback {


    /**
     * 成功获取数据
     * @param favorites 回调数据
     */
    void displayFavoriteList(ArrayList<Program> favorites);
}
