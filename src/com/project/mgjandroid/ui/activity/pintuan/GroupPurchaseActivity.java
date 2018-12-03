package com.project.mgjandroid.ui.activity.pintuan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.GroupInfo;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.FindGroupAddressModel;
import com.project.mgjandroid.model.FindGroupModel;
import com.project.mgjandroid.model.GroupCheckModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.MorePrimaryCategoryActivity;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/8/11.
 */
@Router("groupBuy")
public class GroupPurchaseActivity extends BaseActivity implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2 {

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_publish)
    private TextView tvPintuan;
    @InjectView(R.id.pituan_list)
    private PullToRefreshListView plvPintuan;

    private PintuanListAdapter adapter;
    private MLoadingDialog mMLoadingDialog;
    private CallPhoneDialog dialog;
    private int start = 0;
    private static final int maxResults = 10;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_purchase);
        Injector.get(this).inject();

        initView();
        plvPintuan.setMode(PullToRefreshBase.Mode.BOTH);
        plvPintuan.setOnRefreshListener(this);
        adapter = new PintuanListAdapter(R.layout.item_pintuan_list, mActivity);
        adapter.isRunningGroup(true);
        plvPintuan.setAdapter(adapter);
        adapter.setListener(this);
        getData(false);
        setClick();
    }

    private void getData(final boolean isLoadMore) {
        mMLoadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("size", maxResults);
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        VolleyOperater<FindGroupModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_GROUP_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                plvPintuan.onRefreshComplete();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    FindGroupModel model = (FindGroupModel) obj;
                    List<GroupInfo> mlist = new ArrayList<>();
                    mlist.addAll(model.getValue());
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<GroupInfo> mlistOrg = adapter.getData();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setData(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            adapter.setData(mlist);
                            adapter.notifyDataSetChanged();
                            AnimatorUtils.fadeFadeIn(plvPintuan, mActivity);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            adapter.setData(mlist);
                        }
                    }
                }
            }
        }, FindGroupModel.class);
    }

    private void initView() {
        tvTitle.setText("拼团");
        tvPintuan.setText("发起拼团");
        tvPintuan.setVisibility(View.VISIBLE);
        mMLoadingDialog = new MLoadingDialog();
        emptyView = mInflater.inflate(R.layout.empty_view_group, null);
        plvPintuan.setEmptyView(emptyView);
    }

    private void setClick() {
        ivBack.setOnClickListener(this);
        tvPintuan.setOnClickListener(this);
        plvPintuan.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_publish:
                if (CommonUtils.checkLogin(mActivity)) {
                    canCreateGroup();
                }
                break;
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.join_group:
                int pos = (int) v.getTag();
                if (CommonUtils.checkLogin(mActivity)) {
                    GroupInfo entity = adapter.getData().get(pos);
                    if (entity.getStatus() == 3) {
                        getUserAddress(entity);
                    }
                }
                break;
        }
    }

    private void getUserAddress(final GroupInfo entity) {
        mMLoadingDialog.show(getFragmentManager(), "");
        VolleyOperater<FindGroupAddressModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", entity.getAgentId());
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_FIND_USER_ADDRESS_PREVIEW, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    FindGroupAddressModel model = (FindGroupAddressModel) obj;
                    UserAddress address = model.getValue();
                    Intent intent = new Intent(mActivity, JoinGroupActivity.class);
                    intent.putExtra("group", entity);
                    intent.putExtra("address", address);
                    startActivity(intent);

                }
            }
        }, FindGroupAddressModel.class);
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
                                if (MorePrimaryCategoryActivity.instance != null)
                                    MorePrimaryCategoryActivity.instance.toHome();
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Routers.open(mActivity, ActivitySchemeManager.SCHEME+"groupPurchaseDetail" +"?id=" + adapter.getData().get(position-1).getId());
        Intent intent = new Intent(mActivity, GroupPurchaseDetailActivity.class);
        intent.putExtra("id", adapter.getData().get(position - 1).getId());
        intent.putExtra("isFromLocal", true);
        startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        start = 0;
        getData(false);
    }

    @Override
    public void onPullDownValue(PullToRefreshBase refreshView, int value) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        start = adapter.getDataCount();
        getData(true);
    }
}
