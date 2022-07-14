package com.tosmart.xiexuming.myapplication.mainmenu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.tosmart.xiexuming.myapplication.R;
import com.tosmart.xiexuming.myapplication.about.AboutFragment;
import com.tosmart.xiexuming.myapplication.live.LiveFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiexuming
 */
public class MainMenuActivity extends AppCompatActivity {
    private ImageView mLiveImageView;
    private ImageView mAboutImageView;
    private ImageView mCurrentImageView;

    View mAboutRl;
    View mLiveRl;

    private ViewPager2 mViewPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);
        initUi();
        initData();
        initEven();
    }

    private void initUi() {
        mAboutImageView = findViewById(R.id.imageview_about);
        mLiveImageView = findViewById(R.id.imageview_live);
        mAboutRl = findViewById(R.id.rl_about);
        mLiveRl = findViewById(R.id.rl_live);
        mLiveImageView.setSelected(true);
        mAboutImageView.setSelected(false);
    }

    private void initData() {
        mCurrentImageView = mLiveImageView;
        initPage();
    }

    private void initPage() {
        LiveFragment mLiveFragment = new LiveFragment();
        AboutFragment mAboutFragment = new AboutFragment();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(mLiveFragment);
        fragmentList.add(mAboutFragment);
        FragmentViewPageAdapter fragmentViewPageAdapter =
                new FragmentViewPageAdapter(getSupportFragmentManager(), getLifecycle(), fragmentList);
        mViewPage = findViewById(R.id.viewpage_main_fragment_contain);
        mViewPage.setAdapter(fragmentViewPageAdapter);
        mViewPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changePicture(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void initEven() {
        mAboutImageView.setOnClickListener(view -> {
            mViewPage.setCurrentItem(1);
        });
        mLiveImageView.setOnClickListener(view -> {
            mViewPage.setCurrentItem(0);
        });
        mLiveRl.setOnClickListener(view -> mViewPage.setCurrentItem(0));
        mAboutRl.setOnClickListener(view -> mViewPage.setCurrentItem(1));
    }

    public void changePicture(Integer position) {
        mCurrentImageView.setSelected(false);
        switch (position) {
            case 0:
                mCurrentImageView = mLiveImageView;
                mCurrentImageView.setSelected(true);
                break;
            case 1:
                mCurrentImageView = mAboutImageView;
                mCurrentImageView.setSelected(true);
            default:
                break;
        }
    }
}