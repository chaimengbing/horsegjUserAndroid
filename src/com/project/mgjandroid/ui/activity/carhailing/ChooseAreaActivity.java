package com.project.mgjandroid.ui.activity.carhailing;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.carhailing.CityListBean;
import com.project.mgjandroid.bean.carhailing.DistrictListBean;
import com.project.mgjandroid.bean.carhailing.ProvinceBean;
import com.project.mgjandroid.inter_face.AreaClick;
import com.project.mgjandroid.model.carhailing.TripListModel;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.fragment.BaseFragment;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User_Cjh on 2016/12/9.
 */
public class ChooseAreaActivity extends BaseActivity implements AreaClick {

    @InjectView(R.id.photo_title)
    private TextView tvTitle;
    @InjectView(R.id.photo_back)
    private ImageView ivBack;

    public static final int TAB_1 = 0;
    public static final int TAB_2 = 1;

    private ViewPager mViewPager;
    private ArrayList<BaseFragment> mFragments;
    private NewProvinceFragment mProvinceFragment;
    private NewCityFragment mCityFragment;
    private NewAreaFragment mAreaFragment;
    private RadioGroup mLlContainer;
    private MyPagerAdapter mPagerAdapter;

    public static List<ProvinceBean> mProvinces;
    public static List<CityListBean> mCitys = new ArrayList<>();
    public static List<DistrictListBean> mAreas = new ArrayList<>();
    private ProvinceBean mSelectedProvince;
    private CityListBean mSelectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_car_area);
        Injector.get(this).inject();
        initView();
        initData();
        setLinstener();
    }

    private void setLinstener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initData() {
        TripListModel listModel = (TripListModel) getIntent().getSerializableExtra("tripList");
        int type = getIntent().getIntExtra("type", 0);
        if (listModel == null || CheckUtils.isEmptyList(listModel.getValue())) {
//            ToastUtils.displayMsg("数据错误",mActivity);
            finish();
            return;
        }
        if (type == 2) {
            tvTitle.setText("请选择下车地点");
        }
        mProvinces = listModel.getValue();
        mPagerAdapter.notifyDataSetChanged();
        MLog.d("mProvinces" + mProvinces.size());
    }

    private void initView() {
        mFragments = new ArrayList<>();
        mViewPager = (ViewPager) findViewById(R.id.choose_city_view_pager);
        mViewPager.setOffscreenPageLimit(3);
        mLlContainer = (RadioGroup) findViewById(R.id.area_container);
        addNewTab("请选择", 3, R.color.title_bar_bg);
        mLlContainer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    RadioButton childAt = (RadioButton) group.getChildAt(i);
                    if (childAt.isChecked()) {
                        mViewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });
        mProvinceFragment = NewProvinceFragment.newInstance();
        mProvinceFragment.setListener(this);
        mFragments.add(mProvinceFragment);
        mCityFragment = NewCityFragment.newInstance();
        mCityFragment.setListener(this);
        mAreaFragment = NewAreaFragment.newInstance();
        mAreaFragment.setListener(this);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                RadioButton childAt = (RadioButton) mLlContainer.getChildAt(i);
                if (childAt != null) {
                    childAt.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        RadioButton childAt = (RadioButton) mLlContainer.getChildAt(0);
        childAt.setChecked(true);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    public void setCurrentItem(int i, int pos) {

        switch (i) {
            case 1:
                mFragments.clear();
                mFragments.add(mProvinceFragment);
                mFragments.add(mCityFragment);
                mPagerAdapter.notifyDataSetChanged();
                mProvinceFragment.notifyList();
                mCityFragment.notifyList();
                mAreaFragment.notifyList();

                mLlContainer.removeAllViews();
                addNewTab(mProvinces.get(pos).getProvinceName(), TAB_1, R.color.color_3);
                addNewTab("请选择", 3, R.color.title_bar_bg);
                break;

            case 2:
                mFragments.clear();
                mFragments.add(mProvinceFragment);
                mFragments.add(mCityFragment);
                mFragments.add(mAreaFragment);
                mPagerAdapter.notifyDataSetChanged();
                mAreaFragment.notifyList();
                mCityFragment.notifyList();

                int childCount = mLlContainer.getChildCount();
                if (childCount == 3) {
                    mLlContainer.removeViewAt(1);
                }
                addNewTab(mCitys.get(pos).getCityName(), TAB_2, R.color.color_3);
                break;
        }
        mViewPager.setCurrentItem(i);
    }

    private void addNewTab(String s, int pos, int colorId) {
        RadioButton tvName = new RadioButton(mActivity);
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DipToPx.dip2px(mActivity, 40));
        layoutParams.leftMargin = DipToPx.dip2px(mActivity, 15);
        tvName.setLayoutParams(layoutParams);
        tvName.setGravity(Gravity.CENTER);
        tvName.setTextSize(14);
        tvName.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        tvName.setTextColor(getResources().getColor(colorId));
        tvName.setBackgroundResource(R.drawable.bg_choose_area_tab);
        tvName.setText(s);
        if (pos == TAB_2) {
            mLlContainer.addView(tvName, TAB_2);
        } else {
            mLlContainer.addView(tvName);
        }
    }

    @Override
    public void onAreaClick(int currentItem, int pos) {
        switch (currentItem) {
            case 0:
                mSelectedProvince = mProvinces.get(pos);
                mCitys = mSelectedProvince.getCityList();
                setCurrentItem(currentItem + 1, pos);
                break;
            case 1:
                mSelectedCity = mCitys.get(pos);
                mAreas = mSelectedCity.getDistrictList();
                setCurrentItem(currentItem + 1, pos);
                break;
            case 2:
                clickArea(pos, false);
                break;
        }
    }

    private void clickArea(int pos, boolean clickArea) {
        DistrictListBean selectDistrict = mSelectedCity.getDistrictList().get(pos);
        String name = selectDistrict.getDistrictName();
        Intent intent = new Intent();
        intent.putExtra("selectDistrict", selectDistrict);
        setResult(RESULT_OK, intent);
        finish();
    }
}
