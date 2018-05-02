package com.project.mgjandroid.ui.pictureviewer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.project.mgjandroid.R;

import java.util.List;

/**
 * Created by yuandi on 2016/11/14.
 */

public class ImageViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> mDatas;

    public ImageViewPagerAdapter(FragmentManager fm, List data) {
        super(fm);
        mDatas = data;
    }

    @Override
    public Fragment getItem(int position) {
        String url = mDatas.get(position);
        Fragment fragment = ImageFragment.newInstance(url, R.drawable.horsegj_default);
        return fragment;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }
}
