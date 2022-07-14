package com.tosmart.xiexuming.myapplication.live;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tosmart.xiexuming.myapplication.R;
import com.tosmart.xiexuming.myapplication.callback.ProgramListAdapterCallback;
import com.tosmart.xiexuming.myapplication.model.Program;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiexuming
 */
public class ProgramListAdapter extends BaseAdapter {
    private List<Program> mPrograms = new ArrayList<>();
    private final Context mContext;
    Typeface mFontType;

    private final ProgramListAdapterCallback mProgramListAdapterCallback;

    public ProgramListAdapter(Context context,
                              ProgramListAdapterCallback callback) {
        mContext = context;
        mProgramListAdapterCallback = callback;
        mFontType = Typeface.createFromAsset
                (mContext.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    public void updateProgramList(List<Program> list) {
        mPrograms = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mPrograms.size();
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
                    .inflate(R.layout.live_channel_itemview,
                            viewGroup, false);
            TextView channelTitle = itemView
                    .findViewById(R.id.textView_channel_title);
            channelTitle.setTypeface(mFontType);
            TextView channelNumber = itemView
                    .findViewById(R.id.textView_channel_number);
            channelNumber.setTypeface(mFontType);
            TextView channelStartTime = itemView
                    .findViewById(R.id.textview_channel_start);
            channelStartTime.setTypeface(mFontType);
            TextView channelPge = itemView
                    .findViewById(R.id.textview_channel_peg);
            channelPge.setTypeface(mFontType);
        }

        Integer programNumber = mPrograms.get(i).getProgramNumber();

        TextView channelNumber = itemView
                .findViewById(R.id.textView_channel_number);
        channelNumber.setText(String.valueOf(programNumber));
        TextView channelTitle = itemView
                .findViewById(R.id.textView_channel_title);
        channelTitle.setText(mPrograms.get(i)
                .getProgramName());
        TextView channelStartTime = itemView
                .findViewById(R.id.textview_channel_start);
        channelStartTime.setText(R.string.live_channel_time);
        TextView channelPge = itemView
                .findViewById(R.id.textview_channel_peg);
        channelPge.setText(R.string.live_channel_descriptor);

        ImageView favButton = itemView.findViewById(R.id.image_live_add_fav);
        if (mPrograms.get(i).isFavorite()) {
            favButton.setBackgroundResource(R.mipmap.icon_fav);
        } else {
            favButton.setBackgroundResource(R.mipmap.icon_add_fav);
        }
        favButton.setOnClickListener((view1)
                -> mProgramListAdapterCallback.updateProgramList(i));
        return itemView;
    }

}
