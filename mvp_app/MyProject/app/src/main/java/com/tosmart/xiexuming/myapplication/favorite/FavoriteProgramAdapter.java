package com.tosmart.xiexuming.myapplication.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tosmart.xiexuming.myapplication.R;
import com.tosmart.xiexuming.myapplication.model.Program;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public class FavoriteProgramAdapter extends BaseAdapter {
    private ArrayList<Program> favorites;

    private final Context mContext;

    public FavoriteProgramAdapter(Context context) {
        this.mContext = context;
    }

    public void updateFavoriteProgram(ArrayList<Program> list) {
        favorites = list;
    }

    @Override
    public int getCount() {
        return favorites.size();
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
            itemView = LayoutInflater.from(mContext).
                    inflate(R.layout.favorite_program_itemview, viewGroup, false);
        }
        TextView channelNumber = itemView.findViewById(R.id.textview_favorite_channel_number);
        channelNumber.setText(favorites.get(i).getProgramNumber().toString());
        TextView channelName = itemView.findViewById(R.id.textview_favorite_channel_name);
        channelName.setText(favorites.get(i).getProgramName());
        return itemView;
    }
}
