package com.project.mgjandroid.ui.activity.information;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.ProvinceBean;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.RecyclerViewDivider;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2016/11/22.
 */

public class ChooseProvinceActivity extends BaseActivity {

    public static final String PROVINCE = "province";

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.recycler_view)
    private RecyclerView recyclerView;

    private MLoadingDialog loadingDialog;
    private ChooseProvinceAdapter adapter;
    private String mCitys1;
    private List<ProvinceBean> mProvinces = new ArrayList<>();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<ProvinceBean> mProvinces = (List<ProvinceBean>) msg.obj;
            loadingDialog.dismiss();
            adapter.setList(mProvinces);

        }
    };

    public static void toChooseProvince(Activity context, int requestCode) {
        Intent intent = new Intent(context, ChooseProvinceActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_choose_publish_area);
        Injector.get(this).inject();
        initView();
        getData();
    }

    private void initView() {
        tvTitle.setText("选择您所在的省份");
        ivBack.setOnClickListener(this);
        loadingDialog = new MLoadingDialog();
        adapter = new ChooseProvinceAdapter(mActivity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerViewDivider(
                mActivity, LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.common_gray_line)));
        recyclerView.setAdapter(adapter);
        adapter.setListener(new ChooseProvinceAdapter.OnClickListener() {
            @Override
            public void onClick(int positon) {
                Intent intent = new Intent();
                intent.putExtra(PROVINCE, mProvinces.get(positon));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void parseCityJson(String citys) {
        Gson gson = new Gson();
        mProvinces = gson.fromJson(citys, new TypeToken<List<ProvinceBean>>() {
        }.getType());
    }

    private void getData() {
        loadingDialog.show(getFragmentManager(), "");
        new Thread() {
            @Override
            public void run() {
                mCitys1 = CommonUtils.readFileFromAssets(mActivity, "city.json");
                parseCityJson(mCitys1);
                Message msg = Message.obtain();
                msg.obj = mProvinces;
                handler.sendMessage(msg);
            }
        }.start();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
