package com.tosmart.xiexuming.myapplication.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tosmart.xiexuming.myapplication.R;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public class HistoryAdapter extends BaseAdapter {
    private final Context mContext;

    private ArrayList<String> history = new ArrayList<>();

    public HistoryAdapter(Context context) {
        mContext = context;
    }

    public void updateSearchHistory(ArrayList<String> historyList) {
        history = historyList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return history.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        if (itemView == null) {
            itemView = LayoutInflater.from(mContext)
                    .inflate(R.layout.search_history_itemview
                            , viewGroup, false);
            TextView historyText = itemView.findViewById(R.id.textview_search_history);
            historyText
                    .setTypeface(Typeface.createFromAsset(mContext.getAssets(),
                            "fonts/Roboto-Medium.ttf"));
        }
        TextView historyText = itemView.findViewById(R.id.textview_search_history);
        historyText.setText(history.get(i));
        ImageView imgDel = itemView.findViewById(R.id.imageview_search_history_del);
        imgDel.setVisibility(View.INVISIBLE);
        return itemView;
    }

}
