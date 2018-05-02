package com.project.mgjandroid.ui.activity.yellowpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.yellowpage.YellowPage;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.yellowpage.YellowPageListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.LoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
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
 * Created by User_Cjh on 2017/6/12.
 */

public class YellowPageListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.list_view)
    private PullToRefreshListView listView;
    @InjectView(R.id.yellow_page_search_bar)
    private RelativeLayout rlSearch;
    @InjectView(R.id.yellow_page_empty)
    private TextView tvEmpty;

    private YellowPageListAdapter adapter;
    private LoadingDialog loadingDialog;
    private int start = 0;
    private int maxResults = 10;
    private int type;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_yellow_page);
        Injector.get(this).inject();
        initView();
        loadingDialog = new LoadingDialog(mActivity);
        loadingDialog.show();
        getData(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                finish();
                break;
        }
    }

    private void initView() {
        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", 0);
            tvTitle.setText(getIntent().getStringExtra("name"));
        }
        rlSearch.setVisibility(View.GONE);
        ivBack.setOnClickListener(this);
        adapter = new YellowPageListAdapter(R.layout.item_yellow_page_module, mActivity);
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
        listView.setOnItemClickListener(this);
    }

    private void getData(final boolean isLoadMore) {//type
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        if (type != 0) {
            map.put("type", type);
        }
        map.put("size", maxResults);
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        VolleyOperater<YellowPageListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_YELLOW_PAGE_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                listView.onRefreshComplete();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        return;
                    }
                    YellowPageListModel model = (YellowPageListModel) obj;
                    List<YellowPage> mlist = new ArrayList<>();
                    mlist.addAll(model.getValue());
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        tvEmpty.setVisibility(View.GONE);
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<YellowPage> mlistOrg = adapter.getData();
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
                            tvEmpty.setVisibility(View.VISIBLE);
                            adapter.setData(mlist);
                        }
                    }
                }
            }
        }, YellowPageListModel.class);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long clickId = adapter.getData().get(position - 1).getId();
        Intent intent = new Intent(mActivity, YellowPageDetailActivity.class);
        intent.putExtra("id", clickId);
        startActivity(intent);
    }
}
