package com.project.mgjandroid.ui.pictureviewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.utils.CheckUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yuandi on 2016/11/14.
 */

public class PictureViewActivity extends BaseActivity {

    private ImageViewPagerAdapter adapter;
    private HackyViewPager pager;
    private TextView picsCount;
    private List<String> list;
    private int position;

    public static void toViewPicture(Context context, String data, int position) {
        Intent intent = new Intent(context, PictureViewActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("currentItem", position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_view);
        if (savedInstanceState != null) {
            String[] data = savedInstanceState.getStringArray("data");
            position = savedInstanceState.getInt("currentItem");
            if (data != null) list = Arrays.asList(data);
        } else {
            String data = getIntent().getStringExtra("data");
            position = getIntent().getIntExtra("currentItem", 0);
            if (data != null) list = JSONArray.parseArray(data, String.class);
        }
        if (CheckUtils.isEmptyList(list)) {
            finish();
            return;
        }
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        picsCount = (TextView) findViewById(R.id.pic_count);
        pager = (HackyViewPager) findViewById(R.id.pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                picsCount.setText((position + 1) + "/" + list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        adapter = new ImageViewPagerAdapter(getSupportFragmentManager(), list);
        picsCount.setText("1/" + list.size());
        pager.setAdapter(adapter);
        pager.setCurrentItem(position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentItem", position);
        outState.putStringArray("data", (String[]) list.toArray());
        super.onSaveInstanceState(outState);
    }
}
