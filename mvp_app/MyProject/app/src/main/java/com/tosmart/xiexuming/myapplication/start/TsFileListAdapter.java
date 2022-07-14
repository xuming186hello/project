package com.tosmart.xiexuming.myapplication.start;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
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
public class TsFileListAdapter extends BaseAdapter {
    private ArrayList<String> mFileName = new ArrayList<>();

    private final Context mContext;

    public TsFileListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void updateFileList(ArrayList<String> fileList) {
        mFileName = fileList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFileName.size();
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
                    .inflate(R.layout.start_file_itemview,
                            viewGroup, false);
            Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/Roboto-Medium.ttf");
            TextView fileNameView = itemView.findViewById(R.id.textView_file_name);
            fileNameView.setTypeface(typeface);
        }
        TextView fileNameView = itemView.findViewById(R.id.textView_file_name);
        fileNameView.setText(mFileName.get(i));

        return itemView;
    }

}
