package com.project.mgjandroid.ui.activity.pintuan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.GroupCheckModel;
import com.project.mgjandroid.model.MyGroupModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.HomeActivity;
import com.project.mgjandroid.ui.activity.OrderDetailActivity;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ShareUtil;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/8/15.
 */
public class MyGroupPurchaseActivity extends BaseActivity implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2 {

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.tv_publish)
    private TextView tvPintuan;
    @InjectView(R.id.my_pituan_list)
    private PullToRefreshListView plvPintuan;
    @InjectView(R.id.tv_type_1)
    private TextView type1;
    @InjectView(R.id.tv_type_2)
    private TextView type2;
    @InjectView(R.id.tv_type_3)
    private TextView type3;
    @InjectView(R.id.tv_type_4)
    private TextView type4;
    @InjectView(R.id.line_1)
    private View line1;
    @InjectView(R.id.line_2)
    private View line2;
    @InjectView(R.id.line_3)
    private View line3;
    @InjectView(R.id.line_4)
    private View line4;

    private int currentType = 1;
    private MyGroupPurchaseAdapter adapter;
    private MLoadingDialog mMLoadingDialog;
    private CallPhoneDialog dialog;
    private int maxSize = 10;
    private int start = 0;
    private int status = -1;
    private static final int REFRESH = 500;
    private ShareUtil shareUtil;
    private MyGroupModel.ValueEntity shareGroup;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group_purchase);
        Injector.get(this).inject();
        initView();
        setLinstener();
    }

    private void setLinstener() {
        ivBack.setOnClickListener(this);
        tvPintuan.setOnClickListener(this);
        type1.setOnClickListener(this);
        type2.setOnClickListener(this);
        type3.setOnClickListener(this);
        type4.setOnClickListener(this);
        plvPintuan.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_publish:
                canCreateGroup();
                break;
            case R.id.tv_type_1:
                if (currentType == 1) {
                    break;
                }
                setOriginType(currentType);
                currentType = 1;
                setCurrrentType(currentType);
                break;
            case R.id.tv_type_2:
                if (currentType == 2) {
                    break;
                }
                setOriginType(currentType);
                currentType = 2;
                setCurrrentType(currentType);
                break;
            case R.id.tv_type_3:
                if (currentType == 3) {
                    break;
                }
                setOriginType(currentType);
                currentType = 3;
                setCurrrentType(currentType);
                break;
            case R.id.tv_type_4:
                if (currentType == 4) {
                    break;
                }
                setOriginType(currentType);
                currentType = 4;
                setCurrrentType(currentType);
                break;
            case R.id.my_group_to_group:
                int groupPos = (int) v.getTag();
                MyGroupModel.ValueEntity group = adapter.getData().get(groupPos);
                Intent groupIntent = new Intent(mActivity, GroupPurchaseDetailActivity.class);
                groupIntent.putExtra("id", group.getGroupBuyId());
                startActivityForResult(groupIntent, REFRESH);
                break;
            case R.id.my_group_to_user:
                //买手详情
                int userPos = (int) v.getTag();
                Intent userIntent = new Intent(mActivity, PreviousGroupActivity.class);
                String userId = adapter.getData().get(userPos).getGroupBuy().getGroupBuyUser().getId() + "";
                userIntent.putExtra("userId", userId);
                startActivity(userIntent);
                break;
            case R.id.to_invite_friend:
                int sharePos = (int) v.getTag();
                shareGroup = adapter.getData().get(sharePos);
                if (shareUtil == null) {
                    shareUtil = new ShareUtil(mActivity, shareGroup.getGroupBuy().getGoodsName(), shareGroup.getGroupBuy().getDescription(), shareGroup.getGroupBuy().getShareUrl(),
                            shareGroup.getGroupBuy().getImgs());
                }
                shareUtil.showPopupWindow();
                break;
            case R.id.to_evaluate_group:
                int evaluatePos = (int) v.getTag();
                MyGroupModel.ValueEntity evaluate = adapter.getData().get(evaluatePos);
                String groupBuyOrderId = evaluate.getId();
                String groupBuyId = evaluate.getGroupBuyId();
                Intent evaluateIntent = new Intent(mActivity, EvaluateGroupActivity.class);
                evaluateIntent.putExtra("groupBuyOrderId", groupBuyOrderId);
                evaluateIntent.putExtra("groupBuyId", groupBuyId);
                startActivityForResult(evaluateIntent, REFRESH);
                break;
        }
    }

    private void setCurrrentType(int currentType) {
        switch (currentType) {
            case 1:
                type1.setTextColor(mResource.getColor(R.color.title_bar_bg));
                line1.setVisibility(View.VISIBLE);
                status = -1;
                start = 0;
                getData(false, true);
                break;
            case 2:
                type2.setTextColor(mResource.getColor(R.color.title_bar_bg));
                line2.setVisibility(View.VISIBLE);
                status = 3;
                start = 0;
                getData(false, true);
                break;
            case 3:
                type3.setTextColor(mResource.getColor(R.color.title_bar_bg));
                line3.setVisibility(View.VISIBLE);
                status = 4;
                start = 0;
                getData(false, true);
                break;
            case 4:
                type4.setTextColor(mResource.getColor(R.color.title_bar_bg));
                line4.setVisibility(View.VISIBLE);
                status = 5;
                start = 0;
                getData(false, true);
                break;
        }
    }

    private void setOriginType(int currentType) {
        switch (currentType) {
            case 1:
                type1.setTextColor(mResource.getColor(R.color.color_6));
                line1.setVisibility(View.INVISIBLE);
                break;
            case 2:
                type2.setTextColor(mResource.getColor(R.color.color_6));
                line2.setVisibility(View.INVISIBLE);
                break;
            case 3:
                type3.setTextColor(mResource.getColor(R.color.color_6));
                line3.setVisibility(View.INVISIBLE);
                break;
            case 4:
                type4.setTextColor(mResource.getColor(R.color.color_6));
                line4.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void initView() {
        plvPintuan.setMode(PullToRefreshBase.Mode.BOTH);
        plvPintuan.setOnRefreshListener(this);
        adapter = new MyGroupPurchaseAdapter(R.layout.item_my_pintuan, mActivity);
        plvPintuan.setAdapter(adapter);
        adapter.setListener(this);
        mMLoadingDialog = new MLoadingDialog();
        emptyView = mInflater.inflate(R.layout.empty_view_group, null);
        plvPintuan.setEmptyView(emptyView);
        getData(false, true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mActivity, MyGroupPurchaseDetailActivity.class);
        intent.putExtra(OrderDetailActivity.ORDER_ID, adapter.getData().get(position - 1).getId());
        startActivity(intent);
    }

    private void getData(final boolean isLoadMore, boolean isShowDialog) {
        if (isShowDialog) {
            mMLoadingDialog.show(getFragmentManager(), "");
        }
        VolleyOperater<MyGroupModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", maxSize);
        if (status != -1) {
            map.put("status", status);
        }
        operater.doRequest(Constants.URL_FIND_GROUP_ORDER, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                plvPintuan.onRefreshComplete();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast("获取数据失败");
                        return;
                    }
                    MyGroupModel model = (MyGroupModel) obj;
                    List<MyGroupModel.ValueEntity> list = model.getValue();
                    if (isLoadMore) {
                        List<MyGroupModel.ValueEntity> data = adapter.getData();
                        data.addAll(list);
                        adapter.setData(data);
                    } else {
                        adapter.setData(list);
                    }
                }
            }
        }, MyGroupModel.class);
    }

    private void canCreateGroup() {
        mMLoadingDialog.show(getFragmentManager(), "");
        VolleyOperater<GroupCheckModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_CHECK_CAN_CREATE_GROUP, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast("获取数据失败");
                        return;
                    }
                    GroupCheckModel model = (GroupCheckModel) obj;
                    if (model.getValue().getAgent() == null) {
                        dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                            @Override
                            public void onSure() {
                                dialog.dismiss();
                                setResult(HomeActivity.GROUP);
                                finish();
                            }

                            @Override
                            public void onExit() {
                                dialog.dismiss();
                            }
                        }, "", "当前定位地址暂不支持拼团业务", "返回首页修改", "取消");
                        dialog.show();
                    } else if (model.getValue().getGroupBuyList().getValue() != null && model.getValue().getGroupBuyList().getValue().size() > 0) {
                        GroupCheckModel.ValueEntity.GroupBuyListEntity entity = model.getValue().getGroupBuyList();
                        Intent intent = new Intent(mActivity, GroupCheckActivity.class);
                        intent.putExtra("check", entity);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(mActivity, PublishGroupPurchaseActivity.class);
                        String address;
                        if (model.getValue().getAgent().getProvinceName().equals(model.getValue().getAgent().getCityName())) {
                            address = model.getValue().getAgent().getProvinceName() + " " + model.getValue().getAgent().getDistrictName();
                        } else {
                            address = model.getValue().getAgent().getProvinceName() + " " + model.getValue().getAgent().getCityName() + " "
                                    + model.getValue().getAgent().getDistrictName();
                        }
                        intent.putExtra("address", address);
                        startActivity(intent);
                    }
                }
            }
        }, GroupCheckModel.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REFRESH:
                start = 0;
                getData(false, true);
                break;
        }
        if (shareUtil != null) {
            shareUtil.onActivityResultData(requestCode, resultCode, data);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        start = 0;
        getData(false, false);
    }

    @Override
    public void onPullDownValue(PullToRefreshBase refreshView, int value) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        start = adapter.getDataCount();
        getData(true, false);
    }
}
