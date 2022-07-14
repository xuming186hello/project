package com.tosmart.xiexuming.myapplication.live;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tosmart.xiexuming.myapplication.R;
import com.tosmart.xiexuming.myapplication.favorite.FavoriteActivity;
import com.tosmart.xiexuming.myapplication.model.Program;
import com.tosmart.xiexuming.myapplication.reposity.DataRepository;
import com.tosmart.xiexuming.myapplication.search.SearchActivity;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public class LiveFragment extends Fragment {
    public static final String SEARCH_TAG = "searchCondition";

    private ImageView mFavoriteImage;
    private EditText mSearchEditText;

    ProgramListAdapter mProgramListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getLayoutInflater();
        @SuppressLint("InflateParams")
        View fragmentView = layoutInflater.inflate(R.layout.live_fragment, null);
        initView(fragmentView);
        initData();
        initEven();
        return fragmentView;
    }

    private void initView(View fragmentView) {
        View searchTopBar = fragmentView.findViewById(R.id.top_bar_search_live);
        Button btnCancel = searchTopBar.findViewById(R.id.btn_search_cancel);
        mFavoriteImage = searchTopBar.findViewById(R.id.image_favorite);
        mSearchEditText = fragmentView.findViewById(R.id.editText_search);
        mSearchEditText.setFocusable(false);
        mProgramListAdapter = new ProgramListAdapter(getContext(), i -> {
            ArrayList<Program> programs =
                    DataRepository.getInstance(getContext()).modifyFavorite(i);
            mProgramListAdapter.updateProgramList(programs);
        });
        ListView mListView = fragmentView.findViewById(R.id.listView_channel);
        mListView.setAdapter(mProgramListAdapter);
        mFavoriteImage.setVisibility(View.VISIBLE);
        btnCancel.setVisibility(View.GONE);
        mSearchEditText.setTypeface(Typeface.createFromAsset
                (requireContext().getAssets(), "fonts/Roboto-Regular.ttf"));
    }

    private void initEven() {
        mFavoriteImage.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), FavoriteActivity.class);
            startActivity(intent);
        });

        mSearchEditText.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            String searchCondition = mSearchEditText.getText().toString().trim();
            intent.putExtra(SEARCH_TAG, searchCondition);
            startActivity(intent);
        });
    }

    private void initData() {
        String searchCondition = requireActivity().getIntent().getStringExtra(SEARCH_TAG);
        if (searchCondition != null && !searchCondition.isEmpty()) {
            mSearchEditText.setText(searchCondition);
            DataRepository.getInstance(getContext())
                    .getFuzzyQueryProgram(searchCondition,
                            programDescriptors
                                    -> mProgramListAdapter.updateProgramList(programDescriptors));

        } else {
            ArrayList<Program> programList =
                    DataRepository.getInstance(getContext()).getProgramList();
            if (programList != null) {
                mProgramListAdapter.updateProgramList(programList);
            }
        }
    }
}
