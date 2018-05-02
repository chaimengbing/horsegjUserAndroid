package com.project.mgjandroid.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.view.ZoomImageView;
import com.project.mgjandroid.utils.BitmapUtil;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Router("photoActivity")
public class PhotoActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @InjectView(R.id.photo_back)
    private ImageView ivBack;
    @InjectView(R.id.photo_delete)
    private ImageView ivDelete;
    @InjectView(R.id.photo_title)
    private TextView tvTitle;
    @InjectView(R.id.photo_view_pager)
    private ViewPager viewPager;
    @InjectView(R.id.photo_tool_bar)
    private RelativeLayout toolbar;

    private int currentPos = -1;
    private ArrayList<String> mStrings;
    private ArrayList<View> mViews;
    private MyPagerAdapter mPagerAdapter;
    private boolean showBar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Injector.get(this).inject();
        initView();
    }

    protected void initView() {
        currentPos = getIntent().getIntExtra("position", 0);
        if (getIntent().hasExtra("imgs")) {
            ivDelete.setVisibility(View.GONE);
            String imgs = getIntent().getStringExtra("imgs");
            List<String> list = Arrays.asList(imgs.split(";"));
            mViews = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                View view = mInflater.inflate(R.layout.layout_photo, null);
                final ZoomImageView zoomImageView = (ZoomImageView) view.findViewById(R.id.zoom_image_view);
//                ImageUtils.loadBitmap(mActivity, list.get(i), zoomImageView, R.drawable.group_zhanwei, Constants.getEndThumbnail(640, 640));
                Glide.with(mActivity).load(list.get(i)).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        zoomImageView.setImageBitmap(resource);
                    }
                });
                mViews.add(view);
            }
            mPagerAdapter = new MyPagerAdapter(mViews);
            viewPager.setAdapter(mPagerAdapter);
            setPhoto();
            viewPager.addOnPageChangeListener(this);
        } else {
            mStrings = ChoosePhotoModel.getInstance().getmCurrentPhotoList();
            mViews = new ArrayList<>();
            for (int i = 0; i < mStrings.size(); i++) {
                View view = mInflater.inflate(R.layout.layout_photo, null);
                ZoomImageView zoomImageView = (ZoomImageView) view.findViewById(R.id.zoom_image_view);
                Bitmap bitmap = BitmapUtil.compressBitmap(mStrings.get(i), 1280);
                zoomImageView.setImageBitmap(bitmap);
                mViews.add(view);
            }
            mPagerAdapter = new MyPagerAdapter(mViews);
            viewPager.setAdapter(mPagerAdapter);
            setPhoto();
            viewPager.addOnPageChangeListener(this);

            ivDelete.setOnClickListener(this);
        }
        ivBack.setOnClickListener(this);
    }

    private void setPhoto() {
        if (currentPos != -1) {
            viewPager.setCurrentItem(currentPos);
            tvTitle.setText((viewPager.getCurrentItem() + 1) + "/" + mViews.size());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_delete:
                int delPos = viewPager.getCurrentItem();
                mStrings.remove(delPos);
                mViews.remove(delPos);
                viewPager.setAdapter(mPagerAdapter);
                if (mViews.size() == 0) {
                    finish();
                }
                if (delPos > 0) {
                    viewPager.setCurrentItem(delPos - 1);
                }
                tvTitle.setText((viewPager.getCurrentItem() + 1) + "/" + mStrings.size());
                break;
            case R.id.photo_parent:
                break;
            case R.id.photo_back:
                finish();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tvTitle.setText((viewPager.getCurrentItem() + 1) + "/" + mViews.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class MyPagerAdapter extends PagerAdapter {
        private List<View> views;

        public MyPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views.get(position);
            ViewParent parent = view.getParent();
            if (parent != null) {
                container.removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }
}
