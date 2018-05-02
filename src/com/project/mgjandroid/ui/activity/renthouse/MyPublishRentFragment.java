package com.project.mgjandroid.ui.activity.renthouse;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.HouseLeaseInformation;
import com.project.mgjandroid.bean.information.InformationFreeStandard;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.bean.information.OrderType;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.information.InformationLeaseRentListModel;
import com.project.mgjandroid.model.information.InformationLeaseRentModel;
import com.project.mgjandroid.model.information.InformationWasteModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.information.InformationDetailActivity;
import com.project.mgjandroid.ui.activity.information.PayActivity;
import com.project.mgjandroid.ui.fragment.BaseFragment;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PayUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.InjectorFragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/11/18.
 */
public class MyPublishRentFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2<ListView>, View.OnClickListener {

    @InjectView(R.id.list_view)
    private PullToRefreshListView listView;
    private MyPublishLeaseRentListAdapter adapter;
    private int start = 0;
    private int maxResults = 10;
    private MLoadingDialog mMLoadingDialog;
    private CallPhoneDialog dialog;
    private int mPos;
    private PopupWindow mPopupWindow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_search, container, false);
        InjectorFragment.get(this).inject(view);
        initView();
        initData();
        return view;
    }

    private void initData() {
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(this);
        adapter = new MyPublishLeaseRentListAdapter(R.layout.item_my_publish_lease, mActivity);
        adapter.setListener(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= listView.getRefreshableView().getHeaderViewsCount()) {
                    HouseLeaseInformation information = adapter.getItem(position - listView.getRefreshableView().getHeaderViewsCount());
                    InformationDetailActivity.toInformationDetail(mActivity, information.getId(), InformationType.Rent.getValue());
                }
            }
        });
        View emptyView = mInflater.inflate(R.layout.empty_view_publish, null);
        TextView tvEmpty = (TextView) emptyView.findViewById(R.id.tv_no_data);
        tvEmpty.setText("您还没有发布过信息");
        listView.setEmptyView(emptyView);
        mMLoadingDialog.show(mActivity.getFragmentManager(), "");
        getData(false);
    }

    private void initView() {
        mMLoadingDialog = new MLoadingDialog();
        dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                HouseLeaseInformation information = adapter.getData().get(mPos);
                dialog.dismiss();
                doDelete(information);
            }

            @Override
            public void onExit() {
                dialog.dismiss();
            }
        }, "", "确定要删除这条发布信息吗？", "确定", "取消");
    }

    private void doDelete(HouseLeaseInformation information) {
        mMLoadingDialog.show(mActivity.getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("informationType", InformationType.Rent.getValue());
        map.put("informationId", information.getId());
        VolleyOperater<InformationLeaseRentListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_DELETE_INFORMATION_BY_ID, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    getData(false);
                }
            }
        }, InformationLeaseRentListModel.class);
    }

    private void refresh() {
        hidePopup();
        mMLoadingDialog.show(mActivity.getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("informationType", InformationType.Rent.getValue());
        map.put("informationId", adapter.getData().get(mPos).getId());
        VolleyOperater<InformationLeaseRentListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_REFRESH_INFORMATION_BY_ID, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    getData(false);
                }
            }
        }, InformationLeaseRentListModel.class);
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

    public void getData(final boolean isLoadMore) {
        if (!isLoadMore) {
            start = 0;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", maxResults);
        map.put("informationType", InformationType.Rent.getValue());
        map.put("viewType", 1);
        VolleyOperater<InformationLeaseRentListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                listView.onRefreshComplete();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    InformationLeaseRentListModel model = (InformationLeaseRentListModel) obj;
                    List<HouseLeaseInformation> mlist = new ArrayList<>();
                    mlist.addAll(model.getValue());
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<HouseLeaseInformation> mlistOrg = adapter.getData();
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
        }, InformationLeaseRentListModel.class);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_publish_republic://重新发布
                mPos = (int) v.getTag();
                getInformationFeeList(adapter.getData().get(mPos).getId(), adapter.getData().get(mPos).getAgentId(),
                        InformationType.Rent.getValue(), adapter.getData().get(mPos).getCategoryId(), OrderType.SendInformation.getValue());
                break;
            case R.id.tv_refresh://刷新
//                getInformationFeeList(adapter.getData().get(mPos).getId(), adapter.getData().get(mPos).getAgentId(),
//                        InformationType.Recruit.getValue(), adapter.getData().get(mPos).getCategoryId(), OrderType.RefreshInformation.getValue());
//                hidePopup();
                refresh();
                break;
            case R.id.tv_sticky://置顶
                hidePopup();
                if (adapter.getData().get(mPos).getIsTop() == 1) {
                    ToastUtils.displayMsg("该信息已经置顶", mActivity);
                    break;
                }
                getInformationFeeList(adapter.getData().get(mPos).getId(), adapter.getData().get(mPos).getAgentId(),
                        InformationType.Rent.getValue(), adapter.getData().get(mPos).getCategoryId(), OrderType.TopInformation.getValue());
                break;
            case R.id.tv_delete://删除
                if (dialog != null && !dialog.isShowing()) {
                    hidePopup();
                    dialog.show();
                }
                break;
            case R.id.iv_more_setting:
                mPos = (int) v.getTag();
                if (adapter.getData().get(mPos).getIsExpire() == 0 && adapter.getData().get(mPos).getStatus() == 2) {
                    initPopup();
                    if (mPos == adapter.getData().size() - 1 && adapter.getData().size() != 1) {
                        mPopupWindow.showAsDropDown(v, -(int) mActivity.getResources().getDimension(R.dimen.x100), -(int) mActivity.getResources().getDimension(R.dimen.x126));
                    } else {
                        mPopupWindow.showAsDropDown(v, -(int) mActivity.getResources().getDimension(R.dimen.x100), -(int) mActivity.getResources().getDimension(R.dimen.x20));
                    }
                } else {
                    if (dialog != null && !dialog.isShowing())
                        dialog.show();
                }
                break;
            case R.id.my_publish_toPay:
                int payPos = (int) v.getTag();
                HouseLeaseInformation information = adapter.getData().get(payPos);
                PayActivity.toPay(mActivity, information.getId(), information.getInformationOrderId(), InformationType.Rent.getValue(), information.getAgentId());
                break;
        }
    }

    @Override
    protected void showList(final ArrayList<InformationFreeStandard> value, final int orderType) {
        if (CheckUtils.isEmptyList(value)) {
            if (orderType == OrderType.RefreshInformation.getValue()) {
                refresh();
            } else if (orderType == OrderType.TopInformation.getValue()) {
                createFreeInformationOrder(adapter.getData().get(mPos), InformationType.Rent.getValue(), orderType);
            } else if (orderType == OrderType.SendInformation.getValue()) {
                republic(null, adapter.getData().get(mPos));
            }
            return;
        }
        PayUtils.showWindow(mActivity, value, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < value.size()) {
                    if (orderType == OrderType.RefreshInformation.getValue()) {
                        refresh();
                    } else if (orderType == OrderType.SendInformation.getValue()) {
                        republic(value.get(position), adapter.getData().get(mPos));
                    } else if (orderType == OrderType.TopInformation.getValue()) {
                        createInformationOrder(adapter.getData().get(mPos), value.get(position));
                    }
                    PayUtils.hideWindow();
                }
            }
        });
    }

    private void republic(final InformationFreeStandard informationFreeStandard, HouseLeaseInformation information) {
        final Map<String, Object> map = new HashMap<>();
        map.put("title", information.getTitle());
        map.put("agentId", information.getAgentId());
        map.put("informationType", InformationType.Rent.getValue());
        map.put("imgs", information.getImgs());
        map.put("mobile", information.getMobile());
        map.put("description", information.getDescription());

        map.put("type", 1);
        map.put("sectorArea", information.getSectorArea());
        map.put("houseType", information.getHouseType());
        map.put("minAmt", information.getMinAmt());
        map.put("maxAmt", information.getMaxAmt());
        map.put("categoryId", information.getCategoryId());
        map.put("fromInformationId", information.getId());
        if (informationFreeStandard != null) {
            map.put("freeStandardId", informationFreeStandard.getId());
            map.put("days", informationFreeStandard.getDays());
            map.put("price", informationFreeStandard.getPrice());
            if (informationFreeStandard.getOriginPrice() != null) {
                map.put("originalPrice", informationFreeStandard.getOriginPrice());
            } else {
                map.put("originalPrice", informationFreeStandard.getPrice());
            }
        } else {
            map.put("freeStandardId", 0);
            map.put("days", 0);
            map.put("price", 0);
            map.put("originalPrice", 0);
        }
        map.put("province", information.getProvince());
        map.put("city", information.getCity());
        map.put("district", information.getDistrict());
        VolleyOperater<InformationLeaseRentModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_INFORMATION, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    InformationLeaseRentModel model = (InformationLeaseRentModel) obj;
                    if (model.getCode() == 0) {
                        if (BigDecimal.ZERO.compareTo(informationFreeStandard.getPrice()) < 0) {
                            PayActivity.toPay(mActivity, model.getValue().getId(), model.getValue().getInformationOrder().getId(),
                                    InformationType.Rent.getValue(), model.getValue().getAgentId());
                        } else {
                            getData(false);
                        }
                    }
                }
            }
        }, InformationLeaseRentModel.class);
    }

    private void initPopup() {
        View view = mInflater.inflate(R.layout.popup_second_hand_setting, null);
        TextView tvRefresh = (TextView) view.findViewById(R.id.tv_refresh);
        TextView tvSticky = (TextView) view.findViewById(R.id.tv_sticky);
        TextView tvDelete = (TextView) view.findViewById(R.id.tv_delete);
        tvRefresh.setTag(mPos);
        tvSticky.setTag(mPos);
        tvDelete.setTag(mPos);
        tvRefresh.setOnClickListener(this);
        tvSticky.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setClippingEnabled(false);
        mPopupWindow.setFocusable(true);
    }

    public void hidePopup() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}
