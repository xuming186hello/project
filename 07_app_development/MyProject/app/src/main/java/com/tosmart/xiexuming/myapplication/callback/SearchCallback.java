package com.tosmart.xiexuming.myapplication.callback;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public interface SearchCallback {

    /**
     * 回调
     * @param data 返回历史搜索记录
     */
     void displayHistory(ArrayList<String> data);
}
