package com.tosmart.xiexuming.myapplication.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tosmart.xiexuming.myapplication.R;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public class FuzzyQueryAdapter extends BaseAdapter {
    private ArrayList<String> mProgramItem = new ArrayList<>();
    private final Context mContext;

    public FuzzyQueryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void updateFuzzyData(ArrayList<String> resultData) {
        mProgramItem = resultData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mProgramItem.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        if (itemView == null) {
            itemView = LayoutInflater.from(mContext)
                    .inflate(R.layout.search_fuzzy_query_itemview, viewGroup, false);
        }
        TextView fuzzyResult = itemView.findViewById(R.id.textview_search_fuzzy_query_result);
        String result = mProgramItem.get(i);
        fuzzyResult.setText(result);
        return itemView;
    }
}
