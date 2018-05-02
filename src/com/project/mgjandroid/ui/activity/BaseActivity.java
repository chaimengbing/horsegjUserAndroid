package com.project.mgjandroid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.pingplusplus.android.PaymentActivity;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.information.InformationBaseProperty;
import com.project.mgjandroid.bean.information.InformationFreeStandard;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.information.InformationFreeStandardListModel;
import com.project.mgjandroid.model.information.InformationOrderModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Activity基类
 *
 * @author jian
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {
    private WeakReference<Activity> reference;
    /**
     * 当前activity是否还存在
     */
    protected boolean isActAlive;

    protected View mDecorView;
    protected Activity mActivity;
    protected LayoutInflater mInflater;
    protected Resources mResource;
    protected TextView mTitle;

    protected boolean isCanBack = true;

    public boolean isCanBack() {
        return isCanBack;
    }

//	public boolean isAlive() {
//		return isActAlive;
//	}
//
//	public void setCanBack(boolean isCanBack) {
//		this.isCanBack = isCanBack;
//	}


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        reference = ((App) getApplication()).addActivity(this);
        isActAlive = true;
        mDecorView = getWindow().getDecorView();
        mActivity = this;
        mInflater = LayoutInflater.from(mActivity);
        mResource = mActivity.getResources();
//		mBack = (ImageView) findViewById(R.id.common_back);
//		mBack.setOnClickListener(this);
//		mTitle = (TextView) findViewById(R.id.common_title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActAlive = false;
        ((App) getApplication()).removeActivity(reference);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        isActAlive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActAlive = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isCanBack()) {
            back();
        }
    }

    protected void back() {
        this.finish();
        overridePendingTransition(R.anim.unhold, R.anim.unfade);
    }

    public void toast(int resId) {
        ToastUtils.displayMsg(resId, this);
    }

    public void toast(String msg) {
        ToastUtils.displayMsg(msg, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
        }
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CommonUtils.hideKeyBoard(mActivity);
        return super.onTouchEvent(event);
    }

    public void getInformationFeeList(Long informationId, long agentId, int informationType, Long categoryId, int orderType) {
        Map<String, Object> params = new HashMap<>();
        if (informationId != null) params.put("informationId", informationId);
        params.put("agentId", agentId);
        params.put("informationType", informationType);
        if (categoryId != null) params.put("categoryId", categoryId);
        params.put("type", orderType);
        VolleyOperater<InformationFreeStandardListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_FEE_STANDARD_LIST, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    InformationFreeStandardListModel model = (InformationFreeStandardListModel) obj;
                    if (!model.getValue().isHasPayed())
                        showList(model.getValue().getFreeStandardList());
                }
            }
        }, InformationFreeStandardListModel.class);
    }

    public void createInformationOrder(final InformationBaseProperty information, int orderType, InformationFreeStandard informationFree) {
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", information.getAgentId());
        params.put("informationId", information.getId());
        params.put("orderType", orderType);
        params.put("freeStandardId", informationFree.getId());
        params.put("days", informationFree.getDays());
        params.put("price", informationFree.getPrice());
        Log.d("originalPrice", informationFree.getOriginPrice() + "------");
        if (informationFree.getOriginPrice() == null) {
            params.put("originalPrice", informationFree.getPrice());
        } else {
            params.put("originalPrice", informationFree.getOriginPrice());
        }
        params.put("totalPrice", informationFree.getPrice());
        if (informationFree.getOriginPrice() == null) {
            params.put("originalTotalPrice", informationFree.getPrice());
        } else {
            params.put("originalTotalPrice", informationFree.getOriginPrice());
        }
        params.put("serviceCategoryId", information.getServiceCategoryId());
        params.put("categoryId", information.getCategoryId());
        VolleyOperater<InformationOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_INFORMATION_ORDER, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    InformationOrderModel model = (InformationOrderModel) obj;
                    Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                    intent.putExtra("orderId", model.getValue().getId());
                    intent.putExtra("isInformationOrder", true);
                    startActivity(intent);
                }
            }
        }, InformationOrderModel.class);
    }

    protected void showList(final ArrayList<InformationFreeStandard> value) {

    }
}
