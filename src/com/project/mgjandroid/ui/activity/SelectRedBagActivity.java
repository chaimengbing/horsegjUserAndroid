package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
    public final static String ADDRESS = "address";
    public final static String ITEMS_PRICE = "itemsPrice";
    public final static String RED_MONEY = "red_money";
    public static final int RED_BAG_MONEY = 10007;

    @InjectView(R.id.my_redbag_act_back)
    private ImageView ivBack;
    @InjectView(R.id.title_textview)
    private TextView titleTextView;
    @InjectView(R.id.red_bag_list)
    private RecyclerView recyclerView;


    private SelectRedBagRecyclerAdapter selectRedBagRecyclerAdapter;
    private MLoadingDialog loadingDialog;
    private long agentId = 0l;
    private double itemsPrice = 0;
    private UserAddress userAddress;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_redbag);
        Injector.get(this).inject();
        businessType = getIntent().getIntExtra(BUSINESS_TYPE, businessType);
        userAddress = (UserAddress) getIntent().getSerializableExtra(ADDRESS);
        itemsPrice = getIntent().getDoubleExtra(ITEMS_PRICE, 0);
        initView();
        initData();
    }

    /**
     * 请求红包
     */
    private void initData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("agentId", agentId);
        map.put("businessType", businessType);
        if (userAddress != null) {
            map.put("userAddressId", userAddress.getId());
        }
        map.put("itemsPrice", itemsPrice);
        VolleyOperater<RedBagsModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_QUERY_PLATFORM_REDBAGLIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                List<RedBag> mlist = new ArrayList<>();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    RedBagListModel redBagListModel = ((RedBagsModel) obj).getValue();
                    mlist.add(new RedBag());
                    mlist.addAll(redBagListModel.getPlatformRedBagList());
                    mlist.add(new RedBag());
                    mlist.addAll(redBagListModel.getPlatformRedBagAvailableList());
                    selectRedBagRecyclerAdapter.setPlatformNum(redBagListModel.getPlatformRedBagCount());
                    selectRedBagRecyclerAdapter.setPlatformNumDis(redBagListModel.getPlatformRedBagAvailableList().size());
                    selectRedBagRecyclerAdapter.setList(mlist);
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
    }


}
