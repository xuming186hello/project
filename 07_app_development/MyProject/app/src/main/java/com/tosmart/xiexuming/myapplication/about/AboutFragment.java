package com.tosmart.xiexuming.myapplication.about;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tosmart.xiexuming.myapplication.R;

/**
 * @author xiexuming
 */
public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getLayoutInflater();
        @SuppressLint("InflateParams")
        View fragmentView = layoutInflater.inflate(R.layout.about_fragment, null);
        initView(fragmentView);
        return fragmentView;
    }

    private void initView(View fragmentView) {
        View topBar = fragmentView.findViewById(R.id.top_bar_main);
        TextView title = topBar.findViewById(R.id.textview_top_bar_title);
        title.setText(R.string.about_title);
        ImageView backImageView = topBar.findViewById(R.id.image_top_bar_back);
        backImageView.setVisibility(View.INVISIBLE);

        AboutInformationAdapter aboutInformationAdapter = new AboutInformationAdapter(requireContext());
        ListView informationListView = fragmentView.findViewById(R.id.listview_about_information);
        informationListView.setAdapter(aboutInformationAdapter);
    }
}
