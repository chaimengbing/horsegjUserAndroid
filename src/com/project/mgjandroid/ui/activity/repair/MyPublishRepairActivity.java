package com.project.mgjandroid.ui.activity.repair;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.EducationInformation;
import com.project.mgjandroid.bean.HomemakingInformation;
import com.project.mgjandroid.bean.RepairInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.FindAgentModel;
import com.project.mgjandroid.model.HomeMakingDetailModel;
import com.project.mgjandroid.model.MyPublishHomemakingModel;
import com.project.mgjandroid.model.MyPublishRepairModel;
import com.project.mgjandroid.model.RepairDetailModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.homemaking.HomeMakingDetailActivity;
import com.project.mgjandroid.ui.adapter.MyHomemakingPublishListAdapter;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPublishRepairActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener, CustomDialog.onBtnClickListener {
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    public MLoadingDialog mMLoadingDialog;

    @InjectView(R.id.my_publish_repair_listview)
    private PullToRefreshListView listView;
    private static final int maxResults = 10;
    private int start = 0;
    private MyRepairPublishListAdapter adapter;
    private CallPhoneDialog stickyDialog;
    private CustomDialog mCustomDialog;
    private int mDeleteTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publish_repair);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        tvTitle.setText("维修服务");
        ivBack.setOnClickListener(this);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(this);
//        adapter = new MyRepairPublishListAdapter(R.layout.item_my_publish_education_list_view, mActivity , this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        View emptyView = mInflater.inflate(R.layout.empty_view_publish, null);
        TextView textView = (TextView) emptyView.findViewById(R.id.tv_no_data);
        textView.setText("您还没有发布");
        listView.setEmptyView(emptyView);

        mMLoadingDialog = new MLoadingDialog();
        start = 0;
        getData(false);
        mCustomDialog = new CustomDialog(mActivity, this, "确定", "取消", "提示", "确定删除吗？");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
//            case R.id.tv_refresh:
//                adapter.hidePopup();
//                int tag = (Integer) v.getTag();
//                refreshSecondInfo((long) adapter.getData().get(tag).getId(), tag);
//                break;
//            case R.id.tv_sticky:
//                adapter.hidePopup();
//                findAgent((Integer) v.getTag());
//                break;
//            case R.id.tv_delete:
//                adapter.hidePopup();
//                mDeleteTag = (Integer) v.getTag();
//                mCustomDialog.show();
//                break;
            case R.id.item_cover:
                int position = (int) v.getTag();
                Intent intent = new Intent(this, RepairDetailActivity.class);
                intent.putExtra("messageId", adapter.getData().get(position).getId().intValue());
                startActivity(intent);
                break;
            case R.id.item_state_delete:
                mDeleteTag = (Integer) v.getTag();
                mCustomDialog.show();
                break;
        }
    }

    private void findAgent(int position) {
        mMLoadingDialog.show(mActivity.getFragmentManager(), "");
        RepairInformation bean = adapter.getData().get(position);
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", bean.getAgentId());
        VolleyOperater<FindAgentModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_AGENT_BY_ID, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    if (((FindAgentModel) obj).getValue() != null) {
                        showStickyDialog(((FindAgentModel) obj).getValue().getPhone());
                    }
                }
            }
        }, FindAgentModel.class);
    }

    private void showStickyDialog(final String phone) {
        if (stickyDialog != null) {
            stickyDialog.cancel();
            stickyDialog = null;
        }
        stickyDialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse(String.format(mActivity.getString(R.string.withdraw_phone), phone)));
                mActivity.startActivity(intent);
                stickyDialog.dismiss();
            }

            @Override
            public void onExit() {
                stickyDialog.dismiss();
            }
        }, "排序请拨打客服电话", phone);
        stickyDialog.show();
    }

    private void refreshSecondInfo(Long id, final int position) {
        mMLoadingDialog.show(mActivity.getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        VolleyOperater<RepairDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_REFRESH_REPAIR_MESSAGE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    if (((RepairDetailModel) obj).getValue() != null) {
                        List<RepairInformation> mDatas = adapter.getData();
                        mDatas.remove(position);
                        mDatas.add(0, ((RepairDetailModel) obj).getValue());
                        adapter.setData(mDatas);
                    }
                    ToastUtils.displayMsg("刷新成功", mActivity);
                }
            }
        }, RepairDetailModel.class);
    }

    private void deleteSecondInfo(Long id, final int position) {
        mMLoadingDialog.show(mActivity.getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        VolleyOperater<RepairDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_DELETE_REPAIR_MESSAGE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    List<RepairInformation> mDatas = adapter.getData();
                    mDatas.remove(position);
                    adapter.setData(mDatas);
                    ToastUtils.displayMsg(R.string.delete_success, mActivity);
                }
            }
        }, RepairDetailModel.class);
    }

    private void getData(final boolean isLoadMore) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("size", maxResults);
        VolleyOperater<MyPublishRepairModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_USER_REPAIR_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    MyPublishRepairModel recruitListModel = (MyPublishRepairModel) obj;
                    List<RepairInformation> mlist = new ArrayList<>();
                    mlist.addAll(recruitListModel.getValue());
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<RepairInformation> mlistOrg = adapter.getData();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setData(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            adapter.setData(mlist);
                            adapter.notifyDataSetChanged();
                            AnimatorUtils.fadeFadeIn(listView, mActivity);
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
        }, MyPublishRepairModel.class);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, RepairDetailActivity.class);
        intent.putExtra("messageId", adapter.getData().get(position - 1).getId().intValue());
        startActivity(intent);
    }

    @Override
    public void onSure() {
        deleteSecondInfo((long) adapter.getData().get(mDeleteTag).getId(), mDeleteTag);
        mCustomDialog.dismiss();
    }

    @Override
    public void onExit() {
        mCustomDialog.dismiss();
    }
}
