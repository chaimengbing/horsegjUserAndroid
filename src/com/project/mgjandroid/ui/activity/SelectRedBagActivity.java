package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.RedBagListModel;
import com.project.mgjandroid.model.RedBagsModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.SelectRedBagRecyclerAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2016/5/24.
 */
public class SelectRedBagActivity extends BaseActivity {

    public final static String BUSINESS_TYPE = "businessType";
    public final static String QUANTITY = "quantity";
    public final static String ADDRESS = "address";
    public final static String ITEMS_PRICE = "itemsPrice";
    public final static String PROMOINFO_JSON = "promoInfoJson";
    public final static String RED_MONEY_BAG = "red_money_bag";
    public final static String PLATFORM_REDBAGS = "platform_redbags";
    public final static String PLATFORM_REDBAG_ID = "platform_redbag_id";
    public final static String MERCHANT_ID = "merchantId";
    public final static String DISCOUNT_GOODS_DISCOUN_TAMT = "discountGoodsDiscountAmt";
    public static final int RED_BAG_MONEY = 10007;

    @InjectView(R.id.my_redbag_act_back)
    private ImageView ivBack;
    @InjectView(R.id.title_textview)
    private TextView titleTextView;
    @InjectView(R.id.red_bag_list)
    private RecyclerView recyclerView;
    @InjectView(R.id.use_red_bag_layout)
    private RelativeLayout notUseLayout;
    @InjectView(R.id.no_data)
    private RelativeLayout noDataLayout;
    @InjectView(R.id.is_use_red_bag)
    private CheckBox notUse;


    private SelectRedBagRecyclerAdapter selectRedBagRecyclerAdapter;
    private MLoadingDialog loadingDialog;
    private long agentId = 0l;
    private double itemsPrice = 0;
    private UserAddress userAddress;
    private double discountGoodsDiscountAmt;
    /**
     * Takeaway(1, "外卖"),
     * Groupbuy(2, "拼团"),
     * Shop(3, "商超"),
     * Car(4, "约车")
     * Information(5, "信息发布"),
     * GroupPurchase(6, "团购"),
     * Hitchhiking(7,"顺风车"),
     * VisualAgriculture(8,"可视认养"),
     * LegWork(9,"跑腿"),
     * Express(10,"快递"),
     * Laundry(11,"洗衣");
     */
    private int businessType = 1;
    private String promoInfoJson;
    private long platformRedbagId;
    private int quantity = -1;
    private long merchantId;
    private long isAgentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_redbag);
        Injector.get(this).inject();
        businessType = getIntent().getIntExtra(BUSINESS_TYPE, businessType);
        quantity = getIntent().getIntExtra(QUANTITY, quantity);
        userAddress = (UserAddress) getIntent().getSerializableExtra(ADDRESS);
        itemsPrice = getIntent().getDoubleExtra(ITEMS_PRICE, 0.0);
        promoInfoJson = getIntent().getStringExtra(PROMOINFO_JSON);
        platformRedbagId = getIntent().getLongExtra(PLATFORM_REDBAG_ID, -1l);
        merchantId = getIntent().getLongExtra(MERCHANT_ID, -1l);
        discountGoodsDiscountAmt = getIntent().getDoubleExtra(DISCOUNT_GOODS_DISCOUN_TAMT, 0.0);
        isAgentId = getIntent().getLongExtra("isAgentId", -1l);

        if (platformRedbagId == -1) {
            notUse.setChecked(true);
        } else {
            notUse.setChecked(false);
        }
        loadingDialog = new MLoadingDialog();
        initView();
        initData();
    }

    /**
     * 请求红包
     */
    private void initData() {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<String, Object>();
        if(isAgentId==-1){
            map.put("agentId", agentId);
        }else {
            map.put("agentId", isAgentId);
        }
        map.put("businessType", businessType);
        if (merchantId != -1) {
            map.put("merchantId", merchantId);
        }
        if (userAddress != null) {
            map.put("userAddressId", userAddress.getId());
        }
        if (discountGoodsDiscountAmt != 0.0) {
            map.put("discountGoodsDiscountAmt", discountGoodsDiscountAmt);

        }
        map.put("itemsPrice", itemsPrice);
        if (quantity != -1) {
            map.put("quantity", quantity);
        }
        if (CheckUtils.isNoEmptyStr(promoInfoJson)) {
            map.put("promoInfoJson", promoInfoJson);
        }
        VolleyOperater<RedBagsModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_QUERY_PLATFORM_REDBAGLIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                List<RedBag> mlist = new ArrayList<>();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    RedBagListModel redBagListModel = ((RedBagsModel) obj).getValue();
                    if (!CheckUtils.isEmptyList(redBagListModel.getPlatformRedBagList()) && redBagListModel.getPlatformRedBagList().size() > 0) {
                        mlist.add(new RedBag());
                    }
                    mlist.addAll(redBagListModel.getPlatformRedBagList());
                    if (!CheckUtils.isEmptyList(redBagListModel.getPlatformRedBagAvailableList()) && redBagListModel.getPlatformRedBagAvailableList().size() > 0) {
                        mlist.add(new RedBag());
                    }
                    mlist.addAll(redBagListModel.getPlatformRedBagAvailableList());
                    if (platformRedbagId != -1 && redBagListModel.getPlatformRedBagList() != null) {
                        for (RedBag redBag : redBagListModel.getPlatformRedBagList()) {
                            if (redBag.getId() == platformRedbagId) {
                                redBag.setSelected(true);
                            }
                        }
                    }
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        selectRedBagRecyclerAdapter.setPlatformNum(redBagListModel.getPlatformRedBagCount());
                        selectRedBagRecyclerAdapter.setPlatformNumDis(redBagListModel.getPlatformRedBagAvailableList().size());
                        selectRedBagRecyclerAdapter.setList(mlist);
                        noDataLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        noDataLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                }
            }
        }, RedBagsModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        agentId = PreferenceUtils.getLongPreference("issueAgentId", 0, mActivity);
        loadingDialog = new MLoadingDialog();
        selectRedBagRecyclerAdapter = new SelectRedBagRecyclerAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(selectRedBagRecyclerAdapter);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        notUseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notUse.setChecked(true);
                mActivity.setResult(SelectRedBagActivity.RED_BAG_MONEY, new Intent());
                mActivity.finish();
            }
        });
        notUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notUse.setChecked(true);
                mActivity.setResult(SelectRedBagActivity.RED_BAG_MONEY, new Intent());
                mActivity.finish();
            }
        });
    }


}
