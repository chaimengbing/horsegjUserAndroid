package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.PrimaryCategory;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.SecondCategoryModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.employment.MyPublishOrderActivity;
import com.project.mgjandroid.ui.activity.information.MyPublishInformationActivity;
import com.project.mgjandroid.ui.adapter.PrimaryCategoryListAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

public class MyPublishCategoryActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_publish)
    private TextView tvUserOrder;
    @InjectView(R.id.my_publish_category_grid_view)
    GridView mGridView;
    private MLoadingDialog mMLoadingDialog;
    private PrimaryCategoryListAdapter mCategoryListAdapter;
    private int totalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publish_category);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        tvUserOrder.setVisibility(View.VISIBLE);
        tvUserOrder.setText("订单");
        ivBack.setOnClickListener(this);
        tvUserOrder.setOnClickListener(this);
        tvTitle.setText("我的发布");
        mMLoadingDialog = new MLoadingDialog();
        getSecondTag();
        mCategoryListAdapter = new PrimaryCategoryListAdapter(R.layout.primary_category_grid_view_item, mActivity);
        mGridView.setAdapter(mCategoryListAdapter);
        mGridView.setOnItemClickListener(this);
    }

    /**
     * 获取二手所有的tag
     */
    public void getSecondTag() {
        mMLoadingDialog.show(getFragmentManager(), "");
        VolleyOperater<SecondCategoryModel> operater = new VolleyOperater<>(MyPublishCategoryActivity.this);
        operater.doRequest(Constants.URL_USER_PUBLISH, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    SecondCategoryModel secondCategoryModel = (SecondCategoryModel) obj;
                    mCategoryListAdapter.setData(secondCategoryModel.getValue());
                    totalCount = secondCategoryModel.getValue().size();
                }
            }
        }, SecondCategoryModel.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_publish:
                Intent intent2 = new Intent(this, MyPublishOrderActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position > totalCount - 1) {
            return;
        }
        PrimaryCategory primaryCategory = mCategoryListAdapter.getData().get(position);
        Long id1 = primaryCategory.getId();
        switch (id1.intValue()) {
            case 1://招聘
                Intent intent = new Intent(this, MyPublishInformationActivity.class);
                intent.putExtra("informationType", 1);
                startActivity(intent);
                break;
            case 10://求职
                Intent intent10 = new Intent(this, MyPublishInformationActivity.class);
                intent10.putExtra("informationType", 10);
                startActivity(intent10);
                break;
            case 11://废品回收
                Intent intent11 = new Intent(this, MyPublishInformationActivity.class);
                intent11.putExtra("informationType", 11);
                startActivity(intent11);
                break;
            case 9://健康
                Intent intent9 = new Intent(this, MyPublishInformationActivity.class);
                intent9.putExtra("informationType", 9);
                startActivity(intent9);
                break;
            case 8://法律
                Intent intent8 = new Intent(this, MyPublishInformationActivity.class);
                intent8.putExtra("informationType", 8);
                startActivity(intent8);
                break;
            case 7://风水
                Intent intent7 = new Intent(this, MyPublishInformationActivity.class);
                intent7.putExtra("informationType", 7);
                startActivity(intent7);
                break;
            case 2://租赁
                Intent intent2 = new Intent(this, MyPublishInformationActivity.class);
                intent2.putExtra("informationType", 2);
                startActivity(intent2);
                break;
            case 14://求租
                Intent intent14 = new Intent(this, MyPublishInformationActivity.class);
                intent14.putExtra("informationType", 14);
                startActivity(intent14);
                break;

            case 6:
                Intent intent6 = new Intent(this, MyPublishInformationActivity.class);
                intent6.putExtra("informationType", 6);
                startActivity(intent6);
                break;
            case 4:
                Intent intent4 = new Intent(this, MyPublishInformationActivity.class);
                intent4.putExtra("informationType", 4);
                startActivity(intent4);
                break;
            case 5:
                Intent intent5 = new Intent(this, MyPublishInformationActivity.class);
                intent5.putExtra("informationType", 5);
                startActivity(intent5);
                break;
            case 3:
                Intent intent3 = new Intent(this, MyPublishInformationActivity.class);
                intent3.putExtra("informationType", 3);
                startActivity(intent3);
                break;
            case 13:
                Intent intent13 = new Intent(this, MyPublishInformationActivity.class);
                intent13.putExtra("informationType", 13);
                startActivity(intent13);
                break;
            case 12:
                Intent intent12 = new Intent(this, MyPublishInformationActivity.class);
                intent12.putExtra("informationType", 12);
                startActivity(intent12);
                break;
            case 16:
                Intent intent16 = new Intent(this, MyPublishInformationActivity.class);
                intent16.putExtra("informationType", 16);
                startActivity(intent16);
                break;
            case 15:
                Intent intent15 = new Intent(this, MyPublishInformationActivity.class);
                intent15.putExtra("informationType", 15);
                startActivity(intent15);
                break;
        }
    }
}
