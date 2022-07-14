package com.tosmart.xiexuming.myapplication.mainmenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * @author xiexuming
 */
public class FragmentViewPageAdapter extends FragmentStateAdapter {
    List<Fragment> mFragmentList;

    public FragmentViewPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle
            , List<Fragment> fragmentList) {
        super(fragmentManager, lifecycle);
        mFragmentList = fragmentList;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }
}
