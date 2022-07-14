package com.tosmart.xiexuming.myapplication.about;

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

/**
 * @author xiexuming
 */
public class AboutInformationAdapter extends BaseAdapter {
    private Context mContext;

    public AboutInformationAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 2;
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
        @SuppressLint("ViewHolder")
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.about_information_itemview, viewGroup, false);
        TextView leftTitleTextView = itemView.findViewById(R.id.textview_about_left_title);
        TextView rightTitleTextView = itemView.findViewById(R.id.textview_about_right_title);
        leftTitleTextView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf"));
        if (i == 0) {
            rightTitleTextView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf"));
            rightTitleTextView.setText(R.string.about_version_number);
            leftTitleTextView.setText(R.string.about_version);
        } else {
            ImageView arrowImageView = itemView.findViewById(R.id.imageview_about_right_arrow);
            leftTitleTextView.setText(R.string.about_check_update);
            arrowImageView.setVisibility(View.VISIBLE);
            rightTitleTextView.setVisibility(View.GONE);
        }
        return itemView;
    }
}
