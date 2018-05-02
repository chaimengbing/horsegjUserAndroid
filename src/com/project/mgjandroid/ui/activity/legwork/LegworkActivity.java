package com.project.mgjandroid.ui.activity.legwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.fragment.BaseFragment;
import com.project.mgjandroid.ui.fragment.LegworkBuyFragment;
import com.project.mgjandroid.ui.fragment.LegworkDeliverFragment;
import com.project.mgjandroid.ui.view.TabsView;
import com.project.mgjandroid.ui.view.ViewPagerSlide;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：mgjandroid
 * 类描述：跑腿代购
 * 创建人：Mr_Lei
 * 创建时间：2018/3/12 11:44
 */
@Router("legwork")
public class LegworkActivity extends BaseActivity {

    @InjectView(R.id.tabslayout)
    private TabsView mTabLayout;
    @InjectView(R.id.vp_legwork_home)
    private ViewPagerSlide vpLegwork;
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.tv_order)
    private TextView tvOrder;


    private List<BaseFragment> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legwork_home);
        Injector.get(this).inject();
        initView();
        setLinstener();
    }

    private void initView() {
        list.clear();
        LegworkBuyFragment legworkBuyFragment = new LegworkBuyFragment();
        LegworkDeliverFragment legworkDeliverFragment = new LegworkDeliverFragment();
        list.add(legworkBuyFragment);
        list.add(legworkDeliverFragment);

        //设置预加载个数
        vpLegwork.setOffscreenPageLimit(2);
        //初始化选项卡
        mTabLayout.setTabs("代购", "取送件");

        //设置适配器
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        vpLegwork.setAdapter(adapter);
    }

    private void setLinstener() {

        ivBack.setOnClickListener(this);
        tvOrder.setOnClickListener(this);

        mTabLayout.setOnTabsItemClickListener(new TabsView.OnTabsItemClickListener() {

            @Override
            public void onClick(View view, int position) {
                vpLegwork.setCurrentItem(position, true);
            }
        });

        vpLegwork.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position, true);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.tv_order:
                if (CommonUtils.checkLogin(this)) {
                    Intent intent = new Intent(mActivity, LegworkOrderActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    class MyAdapter extends FragmentPagerAdapter {

        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

}
