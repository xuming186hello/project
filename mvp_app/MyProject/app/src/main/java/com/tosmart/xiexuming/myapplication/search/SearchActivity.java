package com.tosmart.xiexuming.myapplication.search;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tosmart.xiexuming.myapplication.R;
import com.tosmart.xiexuming.myapplication.mainmenu.MainMenuActivity;
import com.tosmart.xiexuming.myapplication.presenter.SearchPresenter;
import com.tosmart.xiexuming.myapplication.reposity.DataRepository;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public class SearchActivity extends AppCompatActivity {
    private Button mCancelButton;
    private EditText mSearchEditText;
    private ImageView mSearchImageview;
    private ImageView mImgHistoryDelButton;

    private GridView mSearchHistoryGridView;
    HistoryAdapter mHistoryAdapter;
    private View mSearchHistoryRecordView;

    FuzzyQueryUtil mFuzzyQueryUtil;
    private ListView mFuzzyQueryListview;
    FuzzyQueryAdapter mFuzzyQueryAdapter;

    private SearchPresenter mSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        initView();
        initData();
        initEven();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String searchCondition = intent.getStringExtra("searchCondition");
        if (searchCondition != null && !searchCondition.isEmpty()) {
            mSearchEditText.setText(searchCondition);
            mSearchEditText.setSelection(searchCondition.length());
        }
    }

    private void initView() {
        View searchTopBar = findViewById(R.id.top_bar_search_history);
        mSearchEditText = searchTopBar.findViewById(R.id.editText_search);
        mCancelButton = searchTopBar.findViewById(R.id.btn_search_cancel);
        ImageView imageFavorite = searchTopBar.findViewById(R.id.image_favorite);
        mSearchImageview = searchTopBar.findViewById(R.id.imageview_search);
        mSearchHistoryGridView = findViewById(R.id.gridView_search_history);
        mSearchHistoryRecordView = findViewById(R.id.ll_search_history_record);
        mFuzzyQueryListview = findViewById(R.id.listview_search_fuzzy_query);
        mImgHistoryDelButton = findViewById(R.id.imageview_search_history_del_all);
        TextView llTitle = findViewById(R.id.textview_search_ll_title);
        llTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf"));

        imageFavorite.setVisibility(View.GONE);
        mCancelButton.setVisibility(View.VISIBLE);
        mSearchHistoryRecordView.setVisibility(View.VISIBLE);
        mFuzzyQueryListview.setVisibility(View.GONE);
    }

    private void initEven() {
        mSearchImageview.setOnClickListener(view -> {
            String searchCondition = mSearchEditText.getText().toString().trim();
            DataRepository.getInstance(getApplicationContext())
                    .setSearchHistory(searchCondition);

            Intent searchIntent =
                    new Intent(SearchActivity.this, MainMenuActivity.class);
            searchIntent.putExtra("searchCondition", searchCondition);
            startActivity(searchIntent);
        });
        mCancelButton.setOnClickListener(view -> {
            Intent cancelIntent = new
                    Intent(SearchActivity.this, MainMenuActivity.class);
            startActivity(cancelIntent);
        });
        mImgHistoryDelButton.setOnClickListener(view -> showDialog());

        mSearchHistoryGridView.setOnItemClickListener((adapterView, view, i, l) -> {
            TextView historyText = view.findViewById(R.id.textview_search_history);
            historyText.setOnClickListener(view1 -> {
                String history = historyText.getText().toString().trim();
                mSearchEditText.setText(history);
                mSearchEditText.setSelection(history.length());
            });
        });
        mSearchHistoryGridView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            ImageView imgDel = view.findViewById(R.id.imageview_search_history_del);
            imgDel.setVisibility(View.VISIBLE);
            TextView historyText = view.findViewById(R.id.textview_search_history);
            String history = historyText.getText().toString();
            imgDel.setOnClickListener(view12 ->
                    mSearchPresenter.deleteHistory(history)
            );
            return true;
        });

        mFuzzyQueryListview.setOnItemClickListener((adapterView, view, i, l) -> {
            TextView resultText = view
                    .findViewById(R.id.textview_search_fuzzy_query_result);
            String text = String.valueOf(resultText.getText());
            //后期拓展
        });

        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String inputData = String.valueOf(charSequence);
                if (inputData.isEmpty()) {
                    mSearchHistoryRecordView.setVisibility(View.VISIBLE);
                    mFuzzyQueryListview.setVisibility(View.GONE);
                } else {
                    mSearchHistoryRecordView.setVisibility(View.GONE);
                    mFuzzyQueryListview.setVisibility(View.VISIBLE);
                    ArrayList<String> fuzzyQueryAdapterData
                            = mFuzzyQueryUtil.getFuzzyQueryAdapterData(inputData);
                    if (fuzzyQueryAdapterData != null) {
                        mFuzzyQueryAdapter.updateFuzzyData(fuzzyQueryAdapterData);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void initData() {
        mHistoryAdapter = new HistoryAdapter(this);
        mSearchHistoryGridView.setAdapter(mHistoryAdapter);

        mFuzzyQueryUtil = FuzzyQueryUtil.getInstance();

        mFuzzyQueryAdapter = new FuzzyQueryAdapter(this);
        mFuzzyQueryListview.setAdapter(mFuzzyQueryAdapter);

        mSearchPresenter = new SearchPresenter(this, new Updatable() {
            @Override
            public void updateSearch(ArrayList<String> data) {
                mHistoryAdapter.updateSearchHistory(data);
            }

            @Override
            public void updateFuzzyQueryList(ArrayList<String> fuzzyQueryList) {
                mFuzzyQueryUtil.updateFuzzyQueryList(fuzzyQueryList);
            }
        });
        mSearchPresenter.updateSearchHistory();
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除历史记录");
        builder.setNegativeButton("否", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setMessage("是否删除所有历史记录");
        builder.setPositiveButton("是", (dialogInterface, i) -> {
            mSearchPresenter.updateHistory();
            dialogInterface.dismiss();
        });
        builder.show();
    }

    public interface Updatable {
        /**
         * 更新历史记录的条目
         *
         * @param data 历史搜索记录
         */
        void updateSearch(ArrayList<String> data);

        /**
         * 更新模糊搜索的条目
         *
         * @param fuzzyQueryList 模糊搜索条目
         */
        void updateFuzzyQueryList(ArrayList<String> fuzzyQueryList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchPresenter = null;
    }
}