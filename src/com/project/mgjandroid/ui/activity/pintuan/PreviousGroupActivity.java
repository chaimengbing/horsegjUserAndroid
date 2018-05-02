package com.project.mgjandroid.ui.activity.pintuan;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.GroupInfo;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.FindGroupAddressModel;
import com.project.mgjandroid.model.PreviousGroupModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/8/12.
 */
@Router("previousGroup")
public class PreviousGroupActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.previous_evaluate_list)
    private ListView lvEvaluate;

    private PintuanListAdapter adapter;
    private int userId;
    private MLoadingDialog mMLoadingDialog;
    private int start = 0;
    private int maxResults = 10;
    private int sort = 1;

    private RoundImageView headerImage;
    private TextView headerUsername;
    private TextView headerBenefitCount;
    private TextView headerUserIntro;
    private TextView headerTotalCount;
    private TextView headerSort;
    private ImageView headerSortArrow;
    private PopupWindow mPopupWindow;
    private boolean isBottom = false;
    private boolean hasMoreData = true;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_evaluate);
        Injector.get(this).inject();

        initView();
        adapter = new PintuanListAdapter(R.layout.item_pintuan_list, mActivity);

        View header = getLayoutInflater().inflate(R.layout.header_previous_evaluate, null);
        headerImage = (RoundImageView) header.findViewById(R.id.previous_group_header_image);
        headerUsername = (TextView) header.findViewById(R.id.previous_group_header_username);
        headerBenefitCount = (TextView) header.findViewById(R.id.previous_group_header_benefit_count);
        headerUserIntro = (TextView) header.findViewById(R.id.previous_group_header_user_intro);
        headerTotalCount = (TextView) header.findViewById(R.id.previous_group_header_total_count);
        headerSort = (TextView) header.findViewById(R.id.previous_group_header_sort);
        headerSortArrow = (ImageView) header.findViewById(R.id.previous_group_header_sort_arrow);

        lvEvaluate.addHeaderView(header);
        lvEvaluate.setAdapter(adapter);
        setListener();
    }

    private void initView() {
        initPopupWindow();
        if (getIntent().hasExtra("userId")) {
            userId = Integer.parseInt(getIntent().getStringExtra("userId"));
            mMLoadingDialog = new MLoadingDialog();
            getData(false);
        } else {
            finish();
            return;
        }
    }

    private void getData(final boolean isLoadingMore) {
        isLoading = true;
        mMLoadingDialog.show(getFragmentManager(), "");
        VolleyOperater<PreviousGroupModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", maxResults);
        map.put("userId", userId);
        map.put("sort", sort);
        operater.doRequest(Constants.URL_USER_GROUP_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    PreviousGroupModel model = (PreviousGroupModel) obj;
                    List<GroupInfo> data = model.getValue().getUserGroupBuyList();
                    hasMoreData = data.size() >= maxResults;
                    if (isLoadingMore) {
                        List<GroupInfo> list = adapter.getData();
                        list.addAll(data);
                        adapter.setData(list);
                    } else {
                        adapter.setData(data);
                        setHeaderData(model.getValue());
                    }
                }
                isLoading = false;
            }
        }, PreviousGroupModel.class);
    }

    private void setHeaderData(PreviousGroupModel.ValueEntity value) {
        PreviousGroupModel.ValueEntity.GroupBuyUserEntity user = value.getGroupBuyUser();
        if (CheckUtils.isNoEmptyStr(user.getHeaderImg())) {
            ImageUtils.loadBitmap(mActivity, user.getHeaderImg(), headerImage, R.drawable.default_group_user_avatar, Constants.getEndThumbnail(45, 45));
        }
        headerUsername.setText(user.getName());
        tvTitle.setText(user.getName());
        headerBenefitCount.setText("受益人数 " + user.getBenefitUserCount());
        headerUserIntro.setText("个人介绍：" + user.getIntro());
        headerTotalCount.setText("全部商品(" + value.getUsergroupBuyCount() + ")");
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        lvEvaluate.setOnItemClickListener(this);
        headerSort.setOnClickListener(this);
        headerSortArrow.setOnClickListener(this);
        adapter.setListener(this);
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.previous_group_header_sort:
            case R.id.previous_group_header_sort_arrow:
                mPopupWindow.showAsDropDown(headerSort, -(int) mActivity.getResources().getDimension(R.dimen.x40), -(int) mActivity.getResources().getDimension(R.dimen.x1));
                break;
            case R.id.tv_default:
                sort = 1;
                start = 0;
                headerSort.setText("默认排序");
                hidePopup();
                getData(false);
                break;
            case R.id.tv_sale_count:
                sort = 2;
                start = 0;
                headerSort.setText("销量排序");
                hidePopup();
                getData(false);
                break;
            case R.id.tv_join_count:
                sort = 3;
                start = 0;
                headerSort.setText("参与人数");
                hidePopup();
                getData(false);
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

    private void initPopupWindow() {
        View view = mInflater.inflate(R.layout.popup_group_sort, null);
        TextView tvDefault = (TextView) view.findViewById(R.id.tv_default);
        TextView tvSaleCount = (TextView) view.findViewById(R.id.tv_sale_count);
        TextView tvJoinCount = (TextView) view.findViewById(R.id.tv_join_count);
        tvDefault.setOnClickListener(this);
        tvSaleCount.setOnClickListener(this);
        tvJoinCount.setOnClickListener(this);
        mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setClippingEnabled(false);
        mPopupWindow.setFocusable(true);
    }

    public void hidePopup() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            return;
        }
        Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseDetail" + "?id=" + adapter.getData().get(position - 1).getId());
    }
}
