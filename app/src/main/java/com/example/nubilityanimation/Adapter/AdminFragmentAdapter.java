package com.example.nubilityanimation.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdminFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments= new ArrayList<>();
    private List<String> mList = new ArrayList<>();
    public AdminFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
    public void addFragment(Fragment fragment,String title)
    {
        mFragments.add(fragment);
        mList.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position);
    }

}
