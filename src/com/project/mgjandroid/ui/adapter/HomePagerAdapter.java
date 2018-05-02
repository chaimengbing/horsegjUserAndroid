package com.project.mgjandroid.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.project.mgjandroid.ui.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * 主页ViewPager适配器
 *
 * @author jian
 */
public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private FragmentManager fm;
    private ArrayList<BaseFragment> fragments;
    private int mChildCount;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public ArrayList<BaseFragment> getFragments() {
        return fragments;
    }

    public void notify(ArrayList<BaseFragment> fragments) {
        if (this.fragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragments) {
                ft.remove(f);
            }
            ft.commitAllowingStateLoss();
            fm.executePendingTransactions();
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        // 重写getItemPosition,保证每次获取时都强制重绘UI
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //得到缓存的fragment
        Fragment fragment = (Fragment) super.instantiateItem(container,
                position);
        if (position == 0 && fragment != fragments.get(0)) {
            //得到tag，这点很重要
            String fragmentTag = fragment.getTag();
            //如果这个fragment需要更新
            FragmentTransaction ft = fm.beginTransaction();
            //移除旧的fragment
            ft.remove(fragment);
            //换成新的fragment
            fragment = fragments.get(0);
            //添加新fragment时必须用前面获得的tag，这点很重要
            ft.add(container.getId(), fragment, fragmentTag);
            ft.attach(fragment);
            ft.commit();
        }
        return fragment;
    }

}
