package com.tosmart.xiexuming.myapplication.search;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public class FuzzyQueryUtil {
    private static FuzzyQueryUtil instance;

    private ArrayList<String> mResultData;
    private ArrayList<String> mFuzzyQueryList;

    public static FuzzyQueryUtil getInstance() {
        if (instance == null) {
            synchronized (FuzzyQueryUtil.class) {
                instance = new FuzzyQueryUtil();
            }
        }
        return instance;
    }

    private FuzzyQueryUtil() {
    }

    public void updateFuzzyQueryList(ArrayList<String> fuzzyQuery) {
        mFuzzyQueryList = fuzzyQuery;
    }

    public ArrayList<String> getFuzzyQueryAdapterData(String inputData) {
        if (mFuzzyQueryList == null || mFuzzyQueryList.size() <= 0) {
            return null;
        }
        mResultData = new ArrayList<>();
        for (String fuzzyData : mFuzzyQueryList) {
            if (fuzzyData.contains(inputData)) {
                mResultData.add(fuzzyData);
            }
        }
        return mResultData;
    }
}
