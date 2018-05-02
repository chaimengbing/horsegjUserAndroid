package com.project.mgjandroid.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.information.InformationBaseProperty;
import com.project.mgjandroid.bean.information.InformationFreeStandard;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.information.InformationFreeStandardListModel;
import com.project.mgjandroid.model.information.InformationOrderModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.activity.information.InformationDetailActivity;
import com.project.mgjandroid.ui.activity.information.MyPublishInformationActivity;
import com.project.mgjandroid.ui.activity.information.PayActivity;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PayUtils;
import com.project.mgjandroid.utils.ToastUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;

    protected LayoutInflater mInflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public InformationBaseProperty getInformation() {
        return null;
    }

    // orderType: 1,发布信息；2,置顶信息；3,查看信息；4,刷新信息
    protected void findInformationFreeStandardList(long userId, long informationId, long agentId, int informationType, Long categoryId, int orderType) {
        if (!App.isLogin()) {
            startActivity(new Intent(mActivity, SmsLoginActivity.class));
            return;
        }
        if (App.getUserInfo() != null && App.getUserInfo().getId() == userId) {
            if (mActivity instanceof InformationDetailActivity) {
                ((InformationDetailActivity) mActivity).showDialog(getInformation().getMobile());
            }
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("informationId", informationId);
        params.put("agentId", agentId);
        params.put("informationType", informationType);
        if (categoryId != null) params.put("categoryId", categoryId);
        params.put("type", orderType);
        VolleyOperater<InformationFreeStandardListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_FEE_STANDARD_LIST, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    InformationFreeStandardListModel model = (InformationFreeStandardListModel) obj;
                    if (!model.getValue().isHasPayed()) {
                        final List<InformationFreeStandard> value = model.getValue().getFreeStandardList();
                        if (CheckUtils.isNoEmptyList(value)) {
//                            if (value.size() == 1 && BigDecimal.ZERO.compareTo(value.get(0).getPrice()) == 0) {
//                                ToastUtils.displayMsg("本次查看联系方式免费", mActivity);
//                                ((InformationDetailActivity) mActivity).showDialog(getInformation().getMobile());
//                                return;
//                            }
                            PayUtils.showWindow(mActivity, value, new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    PayUtils.hideWindow();
                                    if (position < value.size()) {
                                        createInformationOrder(getInformation(), value.get(position));
                                    }
                                }
                            });
                        } else {
                            if (mActivity instanceof InformationDetailActivity) {
                                ((InformationDetailActivity) mActivity).showDialog(getInformation().getMobile());
                            }
                        }
                    } else {
                        if (mActivity instanceof InformationDetailActivity) {
                            ((InformationDetailActivity) mActivity).showDialog(getInformation().getMobile());
                        }
                    }
                }
            }
        }, InformationFreeStandardListModel.class);
    }

    protected void createInformationOrder(InformationBaseProperty information, InformationFreeStandard informationFee) {
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", information.getAgentId());
        params.put("informationId", information.getId());
        params.put("informationType", informationFee.getInformationType());
        params.put("orderType", informationFee.getType());
        params.put("freeStandardId", informationFee.getId());
        params.put("days", informationFee.getDays());
        final BigDecimal price = informationFee.getPrice();
        params.put("price", informationFee.getPrice());
        Log.d("originalPrice", informationFee.getOriginPrice() + "------");
        if (informationFee.getOriginPrice() == null) {
            params.put("originalPrice", informationFee.getPrice());
        } else {
            params.put("originalPrice", informationFee.getOriginPrice());
        }
        params.put("totalPrice", informationFee.getPrice());
        if (informationFee.getOriginPrice() == null) {
            params.put("originalTotalPrice", informationFee.getPrice());
        } else {
            params.put("originalTotalPrice", informationFee.getOriginPrice());
        }

        params.put("serviceCategoryId", information.getServiceCategoryId());
        params.put("categoryId", information.getCategoryId());
        params.put("type", information.getType());

        VolleyOperater<InformationOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_INFORMATION_ORDER, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    InformationOrderModel model = (InformationOrderModel) obj;
                    if (model.getCode() == 0) {
                        if (BigDecimal.ZERO.compareTo(price) == 0) {
                            if (mActivity instanceof InformationDetailActivity) {
                                ((InformationDetailActivity) mActivity).showDialog(getInformation().getMobile());
                            } else {
                                MyPublishInformationActivity.getInstance().refresh();
                            }
                        } else {
                            PayActivity.toPayForResult(mActivity, PayActivity.FROM_INFORMATION_DETAIL_ACTIVITY, model.getValue().getId(), InformationDetailActivity.REQUEST_CODE_PAY);
                        }
                    }
                }
            }
        }, InformationOrderModel.class);
    }

    protected void createFreeInformationOrder(InformationBaseProperty information, int informationType, int orderType) {
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", information.getAgentId());
        params.put("informationId", information.getId());
        params.put("informationType", informationType);
        params.put("orderType", orderType);
        params.put("freeStandardId", 0);
        params.put("days", 0);
        params.put("price", 0);
        params.put("originalPrice", 0);
        params.put("totalPrice", 0);
        params.put("originalTotalPrice", 0);
        params.put("serviceCategoryId", information.getServiceCategoryId());
        params.put("categoryId", information.getCategoryId());
        params.put("type", information.getType());

        VolleyOperater<InformationOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_INFORMATION_ORDER, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    InformationOrderModel model = (InformationOrderModel) obj;
                    if (model.getCode() == 0) {
//                        PayActivity.toPayForResult(mActivity, PayActivity.FROM_INFORMATION_DETAIL_ACTIVITY, model.getValue().getId(), InformationDetailActivity.REQUEST_CODE_PAY);
                        MyPublishInformationActivity.getInstance().refresh();
                    }
                }
            }
        }, InformationOrderModel.class);
    }

    public void getInformationFeeList(Long informationId, long agentId, int informationType, Long categoryId, final int orderType) {
        Map<String, Object> params = new HashMap<>();
        if (informationId != null) params.put("informationId", informationId);
        params.put("agentId", agentId);
        params.put("informationType", informationType);
        if (categoryId != null) params.put("categoryId", categoryId);
        params.put("type", orderType);
        VolleyOperater<InformationFreeStandardListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_FEE_STANDARD_LIST, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    InformationFreeStandardListModel model = (InformationFreeStandardListModel) obj;
//                    if (!model.getValue().isHasPayed())
                    showList(model.getValue().getFreeStandardList(), orderType);
                }
            }
        }, InformationFreeStandardListModel.class);
    }

    protected void showList(final ArrayList<InformationFreeStandard> value, int orderType) {

    }
}
