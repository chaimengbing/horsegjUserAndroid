package com.project.mgjandroid.ui.activity.employment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.InformationOrder;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.CustomerAndComplainPhoneDTOModel;
import com.project.mgjandroid.model.information.InformationOrderListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/11/16.
 */
public class MyPublishOrderActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.com_share)
    private ImageView ivPhone;
    @InjectView(R.id.info_order_list)
    private PullToRefreshListView listView;

    private MyPublishOrderListAdapter adapter;
    private int start = 0;
    private int maxResults = 10;
    private MLoadingDialog mMLoadingDialog;
    private String agentTitle = "区域热线", agentPhone, mgjPhone, mgjTitle = "总部热线";
    private Dialog avatarDialog;
    private Button dialog_bt_take_photo;
    private Button dialog_bt_pick_photo;
    private CallPhoneDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publish_order);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        tvTitle.setText("订单");
        ivBack.setOnClickListener(this);
        ivPhone.setOnClickListener(this);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(this);
        adapter = new MyPublishOrderListAdapter(R.layout.item_my_publish_order, mActivity);
        listView.setAdapter(adapter);

        View emptyView = mInflater.inflate(R.layout.empty_view_publish, null);
        TextView tvEmpty = (TextView) emptyView.findViewById(R.id.tv_no_data);
        tvEmpty.setText("您还没有订单信息");
        listView.setEmptyView(emptyView);
        mMLoadingDialog = new MLoadingDialog();
        mMLoadingDialog.show(getFragmentManager(), "");
        getTelNumXY();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.com_share:
                if (avatarDialog != null && !avatarDialog.isShowing())
                    avatarDialog.show();
                break;
            case R.id.btn_take_photo:
                if (CheckUtils.isTelNum(mgjPhone)) {
                    mgjPhone = getResources().getString(R.string.sale_phone);
                }
                dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                    @Override
                    public void onSure() {
                        //拨打电话
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.DIAL");
                        intent.setData(Uri.parse("tel:" + mgjPhone));
                        mActivity.startActivity(intent);
                        dialog.dismiss();
                    }

                    @Override
                    public void onExit() {
                        dialog.dismiss();
                    }
                }, "", mgjPhone);
                dialog.show();
                avatarDialog.dismiss();
                break;
            case R.id.btn_pick_photo:
                dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                    @Override
                    public void onSure() {
                        //拨打电话
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.DIAL");
                        intent.setData(Uri.parse("tel:" + agentPhone));
                        mActivity.startActivity(intent);
                        dialog.dismiss();
                    }

                    @Override
                    public void onExit() {
                        dialog.dismiss();
                    }
                }, "", agentPhone);
                dialog.show();
                avatarDialog.dismiss();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        start = 0;
        getData(false);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        start = 0;
        getData(false);
    }

    @Override
    public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        start = adapter.getDataCount();
        getData(true);
    }

    private void getData(final boolean isLoadMore) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", maxResults);
        VolleyOperater<InformationOrderListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_ORDER_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                listView.onRefreshComplete();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    InformationOrderListModel model = (InformationOrderListModel) obj;
                    List<InformationOrder> mlist = new ArrayList<>();
                    String serviceTime = model.getServertime();
                    mlist.addAll(model.getValue());
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<InformationOrder> mlistOrg = adapter.getData();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                for (InformationOrder order : mlistOrg) {
                                    order.setServiceTime(serviceTime);
                                }
                                adapter.setData(mlistOrg);
                            }
                        } else {
                            for (InformationOrder order : mlist) {
                                order.setServiceTime(serviceTime);
                            }
                            adapter.setData(mlist);
                            AnimatorUtils.fadeFadeIn(listView, mActivity);
                        }
                    } else {
                        if (isLoadMore) {
                            for (InformationOrder order : adapter.getData()) {
                                order.setServiceTime(serviceTime);
                            }
                            adapter.notifyDataSetChanged();
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            for (InformationOrder order : mlist) {
                                order.setServiceTime(serviceTime);
                            }
                            adapter.setData(mlist);
                        }
                    }
                }
            }
        }, InformationOrderListModel.class);
    }

    private void getTelNumXY() {
        final Map<String, Object> map = new HashMap<>();
        if (PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", Double.parseDouble(PreferenceUtils.getLocation(mActivity)[0]));
            map.put("longitude", Double.parseDouble(PreferenceUtils.getLocation(mActivity)[1]));
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        VolleyOperater<CustomerAndComplainPhoneDTOModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_TELNUM_XY, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (obj instanceof String) {
                    ToastUtils.displayMsg(obj.toString(), mActivity);
                    return;
                }
                CustomerAndComplainPhoneDTOModel model = (CustomerAndComplainPhoneDTOModel) obj;
                for (int i = 0; i < model.getValue().size(); i++) {
                    if (model.getValue() != null && 2 == model.getValue().get(i).getType()) {
                        agentPhone = model.getValue().get(i).getPhone();
                        if (CheckUtils.isNoEmptyStr(model.getValue().get(i).getTitle()))
                            agentTitle = model.getValue().get(i).getTitle();
                    } else if (model.getValue() != null && 3 == model.getValue().get(i).getType()) {
                        mgjPhone = model.getValue().get(i).getPhone();
                        if (CheckUtils.isNoEmptyStr(model.getValue().get(i).getTitle()))
                            mgjTitle = model.getValue().get(i).getTitle();
                    }
                }
                initAvatarDialog();
            }
        }, CustomerAndComplainPhoneDTOModel.class);
    }

    /**
     * 投诉电话联系
     */
    private void initAvatarDialog() {
        RelativeLayout contentView = (RelativeLayout) View.inflate(mActivity, R.layout.pick_or_take_photo_dialog, null);
        avatarDialog = new Dialog(mActivity, R.style.fullDialog);
        avatarDialog.setContentView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        dialog_bt_take_photo = (Button) contentView.findViewById(R.id.btn_take_photo);
        Button dialog_bt_cancel = (Button) contentView.findViewById(R.id.btn_cancel);
        dialog_bt_pick_photo = (Button) contentView.findViewById(R.id.btn_pick_photo);
        View line = contentView.findViewById(R.id.line);
        dialog_bt_take_photo.setText(mgjTitle);
        dialog_bt_pick_photo.setText(agentTitle);
        dialog_bt_pick_photo.setOnClickListener(this);
        dialog_bt_take_photo.setOnClickListener(this);
        dialog_bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarDialog.dismiss();
            }
        });
        if (CheckUtils.isTelNum(agentPhone)) {
            dialog_bt_pick_photo.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            dialog_bt_pick_photo.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        }
    }
}
