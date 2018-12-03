package com.project.mgjandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.model.LegworkEntityModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.legwork.LegworkFeedbackActivity;
import com.project.mgjandroid.ui.activity.legwork.LegworkWriteOrderActivity;
import com.project.mgjandroid.ui.adapter.LegworkTabAdapter;
import com.project.mgjandroid.ui.view.KeyboardStatusDetector;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.OnBannerItemClickListener;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.InjectorFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 项目名称：mgjandroid
 * 类描述：代购
 * 创建人：Mr_Lei
 * 创建时间：2018/3/12 17:29
 */
public class LegworkBuyFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.my_banner)
    private MyBanner myBanner;
    @InjectView(R.id.et_legwork_wares)
    private EditText etLegworkWares;
    @InjectView(R.id.btn_write_order)
    private Button btnWriteOrder;
    @InjectView(R.id.rv_legwork_tab)
    private RecyclerView rvLegworkTab;
    @InjectView(R.id.tv_legwork_server)
    private TextView tvLegworkServer;
    @InjectView(R.id.tv_legwork_feedback)
    private TextView tvLegworkFeedback;
    @InjectView(R.id.tv_billboard)
    private TextView tvBillboard;
    @InjectView(R.id.ll_edit)
    private LinearLayout llEdit;
    @InjectView(R.id.iv_banner_default)
    private ImageView ivBannerDefault;
    private long agentId;
    private String serviceIntroduceUrl;
    private List<LegworkEntityModel.ValueBean.LegWorkBannersBean> legWorkBanners;
    private boolean business = false;
    private MLoadingDialog mMLoadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_legwork_buy, container, false);
        InjectorFragment.get(this).inject(view);
        mMLoadingDialog = new MLoadingDialog();
        agentId = PreferenceUtils.getLongPreference("issueAgentId", -1, mActivity);
        initData();
        inListener();
        return view;
    }

    private void inListener() {
        btnWriteOrder.setOnClickListener(this);
        tvLegworkServer.setOnClickListener(this);
        myBanner.setOnBannerItemClickListener(new OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (legWorkBanners != null && legWorkBanners.size() > position) {
                    String url = legWorkBanners.get(position).getUrl();
                    if (!TextUtils.isEmpty(url)) {
                        Intent intent2 = new Intent(mActivity, YLBWebViewActivity.class);
                        intent2.putExtra(YLBSdkConstants.EXTRA_H5_URL, url);
                        startActivity(intent2);
                    }
                }
            }
        });
        tvLegworkFeedback.setOnClickListener(this);
        new KeyboardStatusDetector()
                .registerActivity(getActivity())
                .setVisibilityListener(new KeyboardStatusDetector.KeyboardVisibilityListener() {
                    @Override
                    public void onVisibilityChanged(boolean keyboardVisible) {
                        if (keyboardVisible) {
                            // Do stuff for keyboard visible
                            btnWriteOrder.setVisibility(View.VISIBLE);
                        } else {
                            // Do stuff for keyboard hidden
                            if (!business) {
                                return;
                            }
                            if (etLegworkWares.getText().toString().length() > 0) {
                                btnWriteOrder.setVisibility(View.VISIBLE);
                            } else {
                                btnWriteOrder.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                });
    }


    private void initData() {
        mMLoadingDialog.show(mActivity.getFragmentManager(), "");
        VolleyOperater<LegworkEntityModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("agentId", agentId);
        map.put("page", 1);
        operater.doRequest(Constants.URL_GET_LEGWORK_DATA, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), getActivity());
                        return;
                    }
                    LegworkEntityModel model = (LegworkEntityModel) obj;
                    LegworkEntityModel.ValueBean value = model.getValue();
                    llEdit.setVisibility(View.VISIBLE);
                    business = value.isBusiness();
                    btnWriteOrder.setEnabled(business);
                    if (!business) {
                        btnWriteOrder.setVisibility(View.VISIBLE);
                    }
                    serviceIntroduceUrl = value.getServiceIntroduceUrl();
                    List<LegworkEntityModel.ValueBean.LegWorkGoodsCategoryListBean> legWorkGoodsCategoryList = value.getLegWorkGoodsCategoryList();
                    if (legWorkGoodsCategoryList != null && legWorkGoodsCategoryList.size() > 0) {
                        rvLegworkTab.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                        rvLegworkTab.setAdapter(new LegworkTabAdapter(legWorkGoodsCategoryList, getActivity(), etLegworkWares, value.isBusiness()));
                    }
                    legWorkBanners = value.getLegWorkBanners();
                    if (legWorkBanners != null && legWorkBanners.size() > 0) {
                        ivBannerDefault.setVisibility(View.GONE);
                        ArrayList<String> strings = new ArrayList<>();
                        for (LegworkEntityModel.ValueBean.LegWorkBannersBean legWorkBanner : legWorkBanners) {
                            strings.add(legWorkBanner.getPicUrl());
                        }
                        myBanner.setUrls(strings, false, false);
                    }
                    List<LegworkEntityModel.ValueBean.LegWorkBroadcastsBean> legWorkBroadcasts = value.getLegWorkBroadcasts();
                    if (legWorkBroadcasts != null && legWorkBroadcasts.size() > 0) {
                        String content = legWorkBroadcasts.get(0).getContent();
                        tvBillboard.setText(content);
                        tvBillboard.setSelected(true);
                    }
                }
            }
        }, LegworkEntityModel.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_write_order:
                //填写订单
                Intent intent = new Intent(mActivity, LegworkWriteOrderActivity.class);
                intent.putExtra("desc", etLegworkWares.getText().toString().trim());
                startActivity(intent);
                break;
            case R.id.tv_legwork_server:
                if (TextUtils.isEmpty(serviceIntroduceUrl)) {
                    return;
                }
                Intent intent2 = new Intent(mActivity, YLBWebViewActivity.class);
                intent2.putExtra(YLBSdkConstants.EXTRA_H5_URL, serviceIntroduceUrl);
                startActivity(intent2);
                break;
            case R.id.tv_legwork_feedback:
                if (CommonUtils.checkLogin(mActivity)) {
                    Intent intent1 = new Intent(mActivity, LegworkFeedbackActivity.class);
                    startActivity(intent1);
                }
                break;
        }

    }
}
