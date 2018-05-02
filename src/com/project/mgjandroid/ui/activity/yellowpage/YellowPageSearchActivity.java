package com.project.mgjandroid.ui.activity.yellowpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.yellowpage.YellowPage;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.yellowpage.YellowPageListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
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

public class YellowPageSearchActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @InjectView(R.id.market_search_back)
    private ImageView ivBack;
    @InjectView(R.id.search_text)
    private EditText etSearch;
    @InjectView(R.id.market_search_list_no_data)
    private TextView tvEmpty;
    @InjectView(R.id.market_search_list)
    private ListView listView;
    @InjectView(R.id.iv_delete)
    private ImageView ivClear;

    private int start = 0;
    private int maxResults = 10;
    private String searchText;
    private YellowPageListAdapter adapter;
    private boolean isBottom = false;
    private boolean hasMoreData = true;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_yellow_page_search);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        adapter = new YellowPageListAdapter(R.layout.item_yellow_page, mActivity);
        adapter.isSearch(true);
        listView.setAdapter(adapter);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchText = s.toString().trim();
                if (searchText.length() > 0) {
                    ivClear.setVisibility(View.VISIBLE);
                    getData(false);
                } else {
                    adapter.setData(new ArrayList<YellowPage>());
                    tvEmpty.setVisibility(View.GONE);
                    ivClear.setVisibility(View.GONE);
                }
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (isBottom) {
                        if (hasMoreData) {
                            start = adapter.getCount();
                            getData(true);
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = firstVisibleItem + visibleItemCount == totalItemCount;
            }
        });
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.market_search_back:
                finish();
                break;
            case R.id.iv_delete:
                etSearch.setText("");
                break;
        }
    }

    private void getData(final boolean isLoadMore) {//type
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", maxResults);
        map.put("paramStr", searchText);
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
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    YellowPageListModel model = (YellowPageListModel) obj;
                    List<YellowPage> mlist = new ArrayList<>();
                    mlist.addAll(model.getValue());
                    hasMoreData = model.getValue().size() >= maxResults;
                    adapter.setSearchText(searchText);
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        tvEmpty.setVisibility(View.GONE);
                        if (isLoadMore) {
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
//                            ToastUtils.displayMsg("到底了", mActivity);
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
        long clickId = adapter.getData().get(position).getId();
        Intent intent = new Intent(mActivity, YellowPageDetailActivity.class);
        intent.putExtra("id", clickId);
        startActivity(intent);
    }
}
