package com.tosmart.xiexuming.myapplication.presenter;

import android.content.Context;

import com.tosmart.xiexuming.myapplication.reposity.DataRepository;
import com.tosmart.xiexuming.myapplication.search.SearchActivity;

/**
 * @author xxm
 * 2022。7.14
 **/
public class SearchPresenter {
    private Context mContext;
    private SearchActivity.Updatable mCallback;

    public SearchPresenter(Context mContext, SearchActivity.Updatable mCallback) {
        this.mContext = mContext;
        this.mCallback = mCallback;
    }

    public void updateSearchHistory() {
        DataRepository.getInstance(mContext).
                getSearchHistory(data -> mCallback.updateSearch(data));
        DataRepository.getInstance(mContext).
                getFuzzyQueryData(fuzzyQueryList ->
                        mCallback.updateFuzzyQueryList(fuzzyQueryList));
    }

    public void updateHistory() {
        DataRepository.getInstance(mContext)
                .deleteHistory(data -> mCallback.updateSearch(data));
    }

    public void deleteHistory(String history) {
        DataRepository.getInstance(mContext)
                .deleteHistory(history,
                        data -> mCallback.updateSearch(data));
    }
}
