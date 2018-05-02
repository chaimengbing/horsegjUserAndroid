package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseEvaluate;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.groupbuying.GroupBuyingEvaluationListModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingEvaluationModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2017/3/7.
 */

public class GroupBuyingAllEvaluationActivity extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.list_view)
    private PullToRefreshListView listView;

    private GroupBuyingEvaluationAdapter adapter;

    private static final int maxResults = 10;
    private int start = 0;
    private long merchantId;

    public static void toGroupBuyingAllEvaluationActivity(Context context, long merchantId) {
        Intent intent = new Intent(context, GroupBuyingAllEvaluationActivity.class);
        intent.putExtra("merchantId", merchantId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_group_buying_all_evaluation);
        Injector.get(this).inject();
        merchantId = getIntent().getLongExtra("merchantId", -1);
        initView();
        getData(false);
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvTitle.setText("全部评价");
        adapter = new GroupBuyingEvaluationAdapter(mActivity);
        listView.setAdapter(adapter);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
                start = adapter.getCount();
                getData(true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.common_back) {
            back();
        }
    }

    public void getData(final boolean isLoadMore) {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", merchantId);
        params.put("start", start);
        params.put("size", maxResults);
        VolleyOperater<GroupBuyingEvaluationListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_EVLUATE_LIST, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    GroupBuyingEvaluationListModel model = (GroupBuyingEvaluationListModel) obj;
                    ArrayList<GroupPurchaseEvaluate> mlist = new ArrayList<>();
                    mlist.addAll(model.getValue());
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            ArrayList<GroupPurchaseEvaluate> mlistOrg = adapter.getData();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setData(mlistOrg);
                            }
                        } else {
                            adapter.setData(mlist);
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
        }, GroupBuyingEvaluationListModel.class);
    }
}
