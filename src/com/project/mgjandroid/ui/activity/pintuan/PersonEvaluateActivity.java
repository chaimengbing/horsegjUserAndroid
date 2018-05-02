package com.project.mgjandroid.ui.activity.pintuan;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.GroupUserComments;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.UserGroupEvaluateModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/8/12.
 */
@Router("personEvaluate")
public class PersonEvaluateActivity extends BaseActivity {
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.person_evaluate_list)
    private ListView lvEvaluate;

    private PersonEvaluateAdapter adapter;
    private String userId;
    private int start = 0;
    private int maxSize = 10;
    private TextView tvTotal, tvGoods, tvService, tvAll, tvImage;
    private boolean hasImage = false;
    private boolean isBottom = false;
    private boolean hasMoreData = true;

    private MLoadingDialog loadingDialog;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_evaluate);
        Injector.get(this).inject();

        initView();
        adapter = new PersonEvaluateAdapter(R.layout.item_pintuan_evaluate, mActivity);
        View header = getLayoutInflater().inflate(R.layout.header_person_evaluate, null);
        tvTotal = (TextView) header.findViewById(R.id.user_evaluate_total);
        tvGoods = (TextView) header.findViewById(R.id.user_evaluate_goods);
        tvService = (TextView) header.findViewById(R.id.user_evaluate_service);
        tvAll = (TextView) header.findViewById(R.id.user_evaluate_all);
        tvImage = (TextView) header.findViewById(R.id.user_evaluate_has_image);

        lvEvaluate.addHeaderView(header);
        lvEvaluate.setAdapter(adapter);
        getData(false);
        setListener();
    }

    private void getData(final boolean isLoadMore) {
        if (!isLoadMore) {
            loadingDialog.show(getFragmentManager(), "");
        }
        isLoading = true;
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("start", start);
        map.put("size", maxSize);
        if (hasImage) {
            map.put("haveImgs", 1);
        }
        VolleyOperater<UserGroupEvaluateModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_GROUP_COMMENT, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast("获取数据失败");
                        return;
                    }
                    UserGroupEvaluateModel model = (UserGroupEvaluateModel) obj;
                    setScore(model);
                    List<GroupUserComments> list = model.getValue().getCommontsAllList();
                    hasMoreData = list.size() >= maxSize;
                    if (isLoadMore) {
                        List<GroupUserComments> data = adapter.getData();
                        data.addAll(list);
                        adapter.setData(data);
                    } else {
                        adapter.setData(list);
                    }
                }
                isLoading = false;
            }
        }, UserGroupEvaluateModel.class);
    }

    private void setScore(UserGroupEvaluateModel model) {
        UserGroupEvaluateModel.ValueEntity entity = model.getValue();
        tvTotal.setText(entity.getCompositeAvgScore() + "");
        tvGoods.setText(entity.getGoodsAvgScore() + "");
        tvService.setText(entity.getServiceAvgScore() + "");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.user_evaluate_all:
                if (!hasImage) {
                    break;
                }
                hasImage = false;
                tvAll.setBackgroundResource(R.drawable.shap_org_range_bg);
                tvAll.setTextColor(Color.parseColor("#ff9900"));
                tvImage.setBackgroundResource(R.drawable.shap_gray_white_bg);
                tvImage.setTextColor(getResources().getColor(R.color.gray_txt));
                start = 0;
                getData(false);
                break;
            case R.id.user_evaluate_has_image:
                if (hasImage) {
                    break;
                }
                hasImage = true;
                tvImage.setBackgroundResource(R.drawable.shap_org_range_bg);
                tvImage.setTextColor(Color.parseColor("#ff9900"));
                tvAll.setBackgroundResource(R.drawable.shap_gray_white_bg);
                tvAll.setTextColor(getResources().getColor(R.color.gray_txt));
                start = 0;
                getData(false);
                break;
        }
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvAll.setOnClickListener(this);
        tvImage.setOnClickListener(this);
        lvEvaluate.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (isBottom) {
                        if (!isLoading) {
                            if (hasMoreData) {
                                start = adapter.getData().size();
                                getData(true);
                            }
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = firstVisibleItem + visibleItemCount == totalItemCount;
            }
        });
    }

    private void initView() {
        tvTitle.setText("全部评价");
        if (getIntent().hasExtra("userId")) {
            userId = getIntent().getStringExtra("userId");
        } else {
            finish();
            return;
        }
        loadingDialog = new MLoadingDialog();
    }
}
