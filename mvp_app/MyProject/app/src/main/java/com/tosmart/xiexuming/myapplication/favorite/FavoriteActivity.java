package com.tosmart.xiexuming.myapplication.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tosmart.xiexuming.myapplication.R;
import com.tosmart.xiexuming.myapplication.mainmenu.MainMenuActivity;
import com.tosmart.xiexuming.myapplication.model.Program;
import com.tosmart.xiexuming.myapplication.presenter.FavoritePresenter;

import java.util.ArrayList;

/**
 * @author Administrator
 */
public class FavoriteActivity extends AppCompatActivity {
    private ImageView mBackImage;
    private FavoriteProgramAdapter mFavoriteProgramAdapter;
    GridView mGridView;
    private FavoritePresenter mFavoritePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_activity);
        initView();
        initData();
        initEven();
    }

    private void initView() {
        mGridView = findViewById(R.id.gridView_favourite);
        View topBar = findViewById(R.id.top_bar_favorite);
        TextView title = topBar.findViewById(R.id.textview_top_bar_title);
        title.setText(R.string.favorite_title);
        mBackImage = topBar.findViewById(R.id.image_top_bar_back);
        mBackImage.setVisibility(View.VISIBLE);
        mFavoriteProgramAdapter = new FavoriteProgramAdapter(this);
    }


    private void initData() {
        mFavoritePresenter = new FavoritePresenter(this);
        mFavoritePresenter.updateFavoriteList(new Updatable() {
            @Override
            public void updateFavorites(ArrayList<Program> favorites) {
                mFavoriteProgramAdapter.updateFavoriteProgram(favorites);
                mGridView.setAdapter(mFavoriteProgramAdapter);
            }
        });
    }

    private void initEven() {
        mBackImage.setOnClickListener(view -> {
            Intent intent = new Intent(FavoriteActivity.this, MainMenuActivity.class);
            startActivity(intent);
        });
    }

    public interface Updatable {
        /**
         * 回调接口
         *
         * @param favorites 更新喜欢列表
         */
        void updateFavorites(ArrayList<Program> favorites);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFavoritePresenter = null;
    }
}