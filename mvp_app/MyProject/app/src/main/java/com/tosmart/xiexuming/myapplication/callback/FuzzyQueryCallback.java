package com.tosmart.xiexuming.myapplication.callback;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public interface FuzzyQueryCallback {

    /**
     * 返回模糊查询的所有数据
     * @param fuzzyQueryList  节目号 +节目名
     */
    void getFuzzyQueryData(ArrayList<String> fuzzyQueryList);
}
