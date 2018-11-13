package com.project.mgjandroid.ui.activity.legwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.DeleteOrderModel;
import com.project.mgjandroid.model.NewOrderFragmentModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.activity.OrderRefundInfoActivity;
import com.project.mgjandroid.ui.adapter.NewOrderListAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SunXueLiang on 2018-03-30.
 */

public class LegworkOrderActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.orderlist_listView)
    private PullToRefreshListView listView;
    private View mNoDataView;
    private NewOrderListAdapter adapter;
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    protected boolean refreshFlag = true;
    private static final int maxResults = 20;
    private int page = 0;
    private SimpleDateFormat sdf;
    private CustomDialog dialog;
    private MLoadingDialog mLoadingDialog;
    private int deleteTag = -1;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_legwork_order);
        Injector.get(this).inject();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        initDialog();
        initViews();
    }

    private void initDialog() {
        dialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                deleteOrder();
                dialog.dismiss();
            }

            @Override
            public void onExit() {
                dialog.dismiss();
            }
        }, "确定", "取消", "提示", "确定删除吗？");
        mLoadingDialog = new MLoadingDialog();
    }

    private void initViews() {
        ivBack.setOnClickListener(this);
        tvTitle.setText("全部订单");
        adapter = new NewOrderListAdapter(R.layout.item_new_order_list, mActivity);
        adapter.setListener(this);
        listView.setAdapter(adapter);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        mNoDataView = LayoutInflater.from(mActivity).inflate(R.layout.layout_order_list_no_data, null);
        listView.autoRefresh();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentDetail = new Intent(LegworkOrderActivity.this, LegworkOrderdetailsActivity.class);
                List<NewOrderFragmentModel.ValueEntity> mlistOrg = adapter.getData();
                intentDetail.putExtra("orderId", mlistOrg.get(i - 1).getLegWorkOrder().getId());
                startActivityForResult(intentDetail, 2000);
                LegworkOrderActivity.this.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
            }
        });
        listView.getRefreshableView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                deleteTag = position - 1;
                dialog.show();
                return true;
            }
        });
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshFlag) {
                    page = 0;
                    getDate(false);
                }
            }

            @Override
            public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshFlag) {
                    page += maxResults;
                    getDate(true);
                }
            }

        });
    }

    private void getDate(final boolean isLoadMore) {
        refreshFlag = false;
        Map<String, Object> map = new HashMap<>();
        map.put("start", page);
        map.put("size", maxResults);
        map.put("type", 9);
        VolleyOperater<NewOrderFragmentModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_GET_NEW_ORDER_LIST, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                refreshFlag = true;
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        if (App.isLogin()) {
                            if ("订单信息不存在".equals(obj)) obj = "没有订单记录";
                            ToastUtils.displayMsg((String) obj, mActivity);
                        }
                        adapter.setData(new ArrayList<NewOrderFragmentModel.ValueEntity>());
                        return;
                    }
                    ArrayList<NewOrderFragmentModel.ValueEntity> mlist = null;
                    if (obj instanceof JSONArray) {
                        String data = JSONArray.toJSONString(obj);
                        mlist = (ArrayList<NewOrderFragmentModel.ValueEntity>) JSONArray.parseArray(data, NewOrderFragmentModel.ValueEntity.class);
                        initServerTime(mlist, sdf.format(new Date()));
                    } else {
                        NewOrderFragmentModel orderFragmentModel = (NewOrderFragmentModel) obj;
                        mlist = new ArrayList<>();
                        mlist.addAll(orderFragmentModel.getValue());
                        initServerTime(mlist, orderFragmentModel.getServertime());
                    }
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        listView.removeView(mNoDataView);
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<NewOrderFragmentModel.ValueEntity> mlistOrg = adapter.getData();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setData(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            adapter.setData(mlist);
                            adapter.notifyDataSetChanged();
//                            AnimatorUtils.fadeFadeIn(listView, mActivity);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            adapter.setData(mlist);
                            listView.setEmptyView(mNoDataView);
                        }
                    }
                } else {

                }
            }
        }, NewOrderFragmentModel.class);
    }

    /**
     * 删除订单
     *
     * @param
     */
    private void deleteOrder() {
        NewOrderFragmentModel.ValueEntity valueEntity = adapter.getData().get(deleteTag);
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", valueEntity.getId());
        VolleyOperater<DeleteOrderModel> operater = new VolleyOperater<DeleteOrderModel>(mActivity);
        operater.doRequest(Constants.URL_DELETE_ORDER, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    DeleteOrderModel deleteOrderModel = (DeleteOrderModel) obj;
                    if (deleteOrderModel.isSuccess()) {
                        adapter.getData().remove(deleteTag);
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.displayMsg(deleteOrderModel.getValue(), mActivity);
                    }
                }
            }
        }, DeleteOrderModel.class);
    }

    public void refreshList() {
        if (listView != null) {
            listView.resetCurrentMode();
            listView.setRefreshing(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000) {
            refreshList();
        }
    }

    private void initServerTime(ArrayList<NewOrderFragmentModel.ValueEntity> mlist, String servertime) {
        for (NewOrderFragmentModel.ValueEntity value : mlist) {
            value.setServerTime(servertime);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_state_go_pay:
                int mTag = (int) v.getTag();
                NewOrderFragmentModel.ValueEntity mValueEntity = adapter.getData().get(mTag);
                if (mValueEntity.getType() == 9) {
                    Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                    intent.putExtra("orderId", mValueEntity.getId());
                    intent.putExtra("agentId", mValueEntity.getAgentId());
                    intent.putExtra("isLegwork", true);
                    startActivityForResult(intent, 2000);
                }
                break;
            case R.id.order_state_evaluate:
                int mTag2 = (int) v.getTag();
                NewOrderFragmentModel.ValueEntity mValueEntity2 = adapter.getData().get(mTag2);
                Intent mIntent = new Intent(mActivity, LegworkEvaluateActivity.class);
                mIntent.putExtra("orderId", mValueEntity2.getId());
                mIntent.putExtra("agentId", "" + mValueEntity2.getAgentId());
                mIntent.putExtra("value", mValueEntity2);
                startActivityForResult(mIntent, 2000);
                break;
            case R.id.order_state_refund_to_balance:
                int mTag3 = (int) v.getTag();
                NewOrderFragmentModel.ValueEntity mValueEntity3 = adapter.getData().get(mTag3);
                Intent intent2 = new Intent(mActivity, OrderRefundInfoActivity.class);
                intent2.putExtra("orderId", mValueEntity3.getId());
                startActivity(intent2);
                break;
            case R.id.common_back:
                finish();
                break;

        }
    }

}
