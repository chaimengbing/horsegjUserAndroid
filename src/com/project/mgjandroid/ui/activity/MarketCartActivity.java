package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.constants.ActRequestCode;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ConfirmOrderModel;
import com.project.mgjandroid.model.MarketCartModel;
import com.project.mgjandroid.model.MarketShippingFeeModel;
import com.project.mgjandroid.model.SuperMarketCartModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.MarketCartListAdapter;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.swipemenulistview.SwipeMenu;
import com.project.mgjandroid.ui.view.swipemenulistview.SwipeMenuCreator;
import com.project.mgjandroid.ui.view.swipemenulistview.SwipeMenuItem;
import com.project.mgjandroid.ui.view.swipemenulistview.SwipeMenuListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/10/10.
 */
public class MarketCartActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @InjectView(R.id.common_back)
    private ImageView back;
    @InjectView(R.id.market_cart_edit)
    private TextView edit;
    @InjectView(R.id.market_cart_select_all_image)
    private ImageView selectAllImage;
    @InjectView(R.id.market_cart_select_all_text)
    private TextView selectAllText;
    @InjectView(R.id.market_cart_total_price)
    private TextView totalPrice;
    @InjectView(R.id.market_cart_ship_fee)
    private TextView shipFee;
    @InjectView(R.id.market_cart_to_pay)
    private TextView toPay;
    @InjectView(R.id.market_cart_listView)
    private SwipeMenuListView cartList;
    @InjectView(R.id.market_cart_bottom)
    private LinearLayout bottom;
    @InjectView(R.id.market_cart_empty_view)
    private LinearLayout empty;

    private boolean isEdit = false;
    private MarketCartListAdapter adapter;
    private boolean isSelectAll = false;
    private MLoadingDialog dialog;
    private CallPhoneDialog deleteDialog;
    private SuperMarketCartModel superMarketCartModel;
    private SuperMarketCartModel.SuperMarketCartBean superMarketBean;
    private ArrayList<SuperMarketCartModel.CartGoods> goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_cart);
        Injector.get(this).inject();
        instance = this;
        initView();
        setListener();
    }

    private void setListener() {
        back.setOnClickListener(this);
        edit.setOnClickListener(this);
        selectAllImage.setOnClickListener(this);
        selectAllText.setOnClickListener(this);
        toPay.setOnClickListener(this);
    }

    private void initView() {
        adapter = new MarketCartListAdapter(R.layout.item_market_cart, mActivity);
        cartList.setAdapter(adapter);
        cartList.setOnItemClickListener(this);
        adapter.setListener(this);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(getResources().getColor(R.color.red_1)));
                deleteItem.setWidth(DipToPx.dip2px(MarketCartActivity.this, 70));
                deleteItem.setTitle(R.string.delete);
                deleteItem.setTitleColor(getResources().getColor(R.color.white));
                deleteItem.setTitleSize(18);
                menu.addMenuItem(deleteItem);
            }
        };
        cartList.setMenuCreator(creator);
        cartList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if (index == 0) {
                    doDelete(position);
                }
                return false;
            }
        });
        dialog = new MLoadingDialog();
        getCartGoods();
        getShippingFee();
    }

    private void getShippingFee() {
        Map<String, Object> map = new HashMap<>();
        double latitude = Double.parseDouble(PreferenceUtils.getLocation(this)[0]);
        double longitude = Double.parseDouble(PreferenceUtils.getLocation(this)[1]);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("merchantId", superMarketBean.getMerchantId());
        VolleyOperater<MarketShippingFeeModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_MERCHANT_SHIPPING_FEE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (obj instanceof String) {
                    ToastUtils.displayMsg(obj.toString(), mActivity);
                    return;
                }
                MarketShippingFeeModel model = (MarketShippingFeeModel) obj;
                BigDecimal shippingFee = model.getValue();
                if (shippingFee.compareTo(BigDecimal.ZERO) == 0) {
                    shipFee.setVisibility(View.GONE);
                } else {
                    shipFee.setVisibility(View.VISIBLE);
                    shipFee.setText("(另需配送费¥" + shippingFee + ")");
                }
            }
        }, MarketShippingFeeModel.class);
    }

    private void getCartGoods() {
        superMarketCartModel = SuperMarketCartModel.getInstance();
        superMarketCartModel.initData();
        superMarketBean = superMarketCartModel.getSuperMarketCartBean();
        goods = superMarketBean.getCartGoods();
        StringBuilder sb = new StringBuilder("");
        for (int i = goods.size() - 1; i >= 0; i--) {
            SuperMarketCartModel.CartGoods good = goods.get(i);
            if (i == 0)
                sb.append(good.getGoodsId());
            else
                sb.append(good.getGoodsId()).append(",");
        }
        if (CheckUtils.isEmptyStr(sb.toString())) {
            bottom.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
            return;
        } else {
            bottom.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }

        dialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("goodsIds", sb.toString());
        VolleyOperater<MarketCartModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_MERCHANT_SHOP_GOODS_CART_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                dialog.dismiss();
                if (obj instanceof String) {
                    ToastUtils.displayMsg(obj.toString(), mActivity);
                    return;
                }
                MarketCartModel model = (MarketCartModel) obj;
                if (model.getCode() == 0) {
                    List<Goods> data = model.getValue();
                    dealGoods(data);
                }
            }
        }, MarketCartModel.class);
    }

    private void dealGoods(List<Goods> data) {
        for (Goods bean : data) {
            bean.setCanEdit(true);
            if (bean.getStatus() == 0 || (bean.getGoodsSpecList().get(0).getStockType() == 1 && bean.getGoodsSpecList().get(0).getStock() == 0)) {//售罄状态
                bean.setCanEdit(false);
            } else if (bean.getStatus() == 2) {
                bean.setCanEdit(false);
            }
        }
        if (data.size() == 0) {
            bottom.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        } else {
            bottom.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            for (int i = 0; i < data.size(); i++) {
                data.get(i).setCount(goods.get(data.size() - i - 1).getButCount());
            }
            adapter.setData(data);
            isSelectAll = true;
            selectAllImage.setImageResource(R.drawable.market_cart_checked);
            setAll(isSelectAll);
            setTotalPrice();
        }
    }

    private void doDelete(int position) {
        List<Goods> data = adapter.getData();
        if (!superMarketCartModel.isHasChange())
            superMarketCartModel.setHasChange(true);
        superMarketBean.deleteGoods(data.get(position).getId());
        data.remove(position);
        adapter.setData(data);
        setTotalPrice();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.market_cart_to_pay:
                if (isEdit) {
                    boolean hasSelect = false;
                    for (Goods good : adapter.getData()) {
                        if (good.isChecked()) {
                            hasSelect = true;
                            break;
                        }
                    }
                    if (!hasSelect) {
                        ToastUtils.displayMsg("您还没有选择商品哦！", mActivity);
                        return;
                    }
                    if (deleteDialog == null) {
                        deleteDialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                            @Override
                            public void onSure() {
                                deleteSelectGoods();
                                deleteDialog.dismiss();
                            }

                            @Override
                            public void onExit() {
                                deleteDialog.dismiss();
                            }
                        }, "", "是否删除选中的商品", "删除", "取消");
                    }
                    deleteDialog.show();
                } else {
                    if (CommonUtils.checkLogin(mActivity)) {
                        getOrderPreview();
                    }
                }
                break;
            case R.id.market_cart_edit_status:
                int pos = (int) v.getTag();
                List<Goods> data = adapter.getData();
                if (data.get(pos).isChecked()) {
                    data.get(pos).setIsChecked(false);
                } else {
                    data.get(pos).setIsChecked(true);
                }
                isSelectAll = true;
                for (Goods goods : data) {
                    if (goods.isCanEdit() && !goods.isChecked()) {
                        isSelectAll = false;
                    }
                }
                if (isSelectAll) {
                    selectAllImage.setImageResource(R.drawable.market_cart_checked);
                } else {
                    selectAllImage.setImageResource(R.drawable.market_cart_unselect);
                }
                adapter.setData(data);
                setTotalPrice();
                break;
            case R.id.market_cart_goods_add:
                int addPos = (int) v.getTag();
                List<Goods> addData = adapter.getData();
                Goods bean = addData.get(addPos);
                GoodsSpec goodsSpec = bean.getGoodsSpecList().get(0);
                int count = bean.getCount();
                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && count >= goodsSpec.getStock()) {
                    ToastUtils.displayMsg("该商品库存不足", mActivity);
                    break;
                }
                if (goodsSpec.getOrderLimit() != 0 && count >= goodsSpec.getOrderLimit()) {
                    ToastUtils.displayMsg("该商品限购" + goodsSpec.getOrderLimit() + "份", mActivity);
                    break;
                }
                if (bean.getCount() == 99 || (bean.getGoodsSpecList().get(0).getOrderLimit() != 0 && bean.getCount() >= bean.getGoodsSpecList().get(0).getOrderLimit())) {
                    ToastUtils.displayMsg("该商品已达到最大购买数量", mActivity);
                    break;
                }
                bean.setCount(bean.getCount() + 1);
                if (!superMarketCartModel.isHasChange())
                    superMarketCartModel.setHasChange(true);
                superMarketBean.addGoods(bean.getId(), 1);
                adapter.setData(addData);
                setTotalPrice();
                break;
            case R.id.market_cart_goods_minus:
                int minusPos = (int) v.getTag();
                List<Goods> minusData = adapter.getData();
                Goods bean1 = minusData.get(minusPos);
                if (bean1.getCount() <= 1) {
                    break;
                }
                bean1.setCount(bean1.getCount() - 1);
                if (!superMarketCartModel.isHasChange())
                    superMarketCartModel.setHasChange(true);
                superMarketBean.minusGoods(bean1.getId(), 1);
                adapter.setData(minusData);
                setTotalPrice();
                break;
            case R.id.market_cart_edit:
                List<Goods> data1 = adapter.getData();
                if (isEdit) {
                    isEdit = false;
                    edit.setText("编辑");
                    toPay.setText("去结算");
                    toPay.setBackgroundResource(R.color.org_yellow);
                } else {
                    isEdit = true;
                    edit.setText("完成");
                    toPay.setText("删除");
                    toPay.setBackgroundResource(R.color.pintuan_red);
                }
                for (Goods goods : data1) {
                    goods.setIsEditing(isEdit);
                    goods.setCanEdit(true);
                    GoodsSpec entity = goods.getGoodsSpecList().get(0);
                    if (goods.getStatus() == 0 || (entity.getStockType() == 1 && entity.getStock() == 0)) {//售罄状态
                        if (!goods.isEditing()) {
                            goods.setCanEdit(false);
                            goods.setIsChecked(false);
                        }
                        goods.setSaleStatus(false);
                    } else if (goods.getStatus() == 2) {
                        if (!goods.isEditing()) {
                            goods.setCanEdit(false);
                            goods.setIsChecked(false);
                        }
                        goods.setSaleStatus(false);
                    } else {
                        goods.setSaleStatus(true);
                    }
                }
                adapter.setData(data1);
                isSelectAll = true;
                for (Goods goods : adapter.getData()) {
                    if (goods.isCanEdit() && !goods.isChecked()) {
                        isSelectAll = false;
                    }
                }
                if (isSelectAll) {
                    selectAllImage.setImageResource(R.drawable.market_cart_checked);
                } else {
                    selectAllImage.setImageResource(R.drawable.market_cart_unselect);
                }
                break;
            case R.id.market_cart_select_all_image:
            case R.id.market_cart_select_all_text:
                if (isSelectAll) {
                    isSelectAll = false;
                    selectAllImage.setImageResource(R.drawable.market_cart_unselect);
                } else {
                    isSelectAll = true;
                    selectAllImage.setImageResource(R.drawable.market_cart_checked);
                }
                setAll(isSelectAll);
                setTotalPrice();
                break;
        }
    }

    private void deleteSelectGoods() {
        List<Goods> data = adapter.getData();
        List<Goods> list = adapter.getData();
        for (int i = data.size() - 1; i >= 0; i--) {
            Goods bean = data.get(i);
            if (bean.isChecked()) {
                if (!superMarketCartModel.isHasChange())
                    superMarketCartModel.setHasChange(true);
                superMarketBean.deleteGoods(bean.getId());
                list.remove(i);
            }
        }
        adapter.setData(list);
        isEdit = false;
        edit.setText("编辑");
        toPay.setText("去结算");
        toPay.setBackgroundResource(R.color.org_yellow);
        List<Goods> data1 = adapter.getData();
        for (Goods goods : data1) {
            goods.setIsEditing(isEdit);
        }
        adapter.setData(data1);
        setTotalPrice();
    }

    /**
     * 订单预览
     */
    private void getOrderPreview() {
        dialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", superMarketBean.getMerchantId());
        if (App.isLogin()) {
            map.put("loginToken", App.getUserInfo().getToken());
            map.put("userId", App.getUserInfo().getId());
        }
        ArrayList<Map<String, Object>> orderItems = new ArrayList<>();
        for (Goods goods : adapter.getData()) {
            if (goods.isChecked()) {
                HashMap<String, Object> m = new HashMap<>();
                m.put("id", goods.getId());
                m.put("quantity", goods.getCount());
                m.put("specId", goods.getGoodsSpecList().get(0).getId());
                orderItems.add(m);
            }
        }
        map.put("orderItems", orderItems);
        final JSONObject previewJsonData = new JSONObject(map);
        Map<String, Object> params = new HashMap<>();
        params.put("data", previewJsonData.toString());
        VolleyOperater<ConfirmOrderModel> operater = new VolleyOperater<>(MarketCartActivity.this);
        operater.doRequest(Constants.URL_MERCHANT_SHOP_ORDER_PREVIEW, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        String errorMsg = (String) obj;
                        ToastUtils.displayMsg(errorMsg, mActivity);
                    } else {
                        ConfirmOrderModel confirmOrderModel = (ConfirmOrderModel) obj;
                        if (confirmOrderModel.isSuccess()) {
                            Intent intent = new Intent(mActivity, ConfirmOrderActivity.class);
                            intent.putExtra("confirmOrderModel", confirmOrderModel);
                            intent.putExtra("onceMoreOrder", previewJsonData);
                            intent.putExtra("isFromMarket", true);
                            startActivityForResult(intent, ActRequestCode.MARKET);
                        } else {
                            ToastUtils.displayMsg("商家无法下单，请联系商家", mActivity);
                        }
                    }
                }
                dialog.dismiss();
            }
        }, ConfirmOrderModel.class);
    }

    private void setTotalPrice() {
        List<Goods> data = adapter.getData();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Goods bean : data) {
            if (bean.isChecked()) {
                totalPrice = totalPrice.add(bean.getGoodsSpecList().get(0).getPrice().multiply(new BigDecimal(bean.getCount())));
            }
        }
        this.totalPrice.setText("总计：¥" + totalPrice);
    }

    private void setAll(boolean isChecked) {
        List<Goods> data = adapter.getData();
        for (Goods bean : data) {
            if (bean.isCanEdit()) {
                bean.setIsChecked(isChecked);
            }
        }
        adapter.setData(data);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Goods goods = adapter.getData().get(position);
//        if(goods.isEditing() || goods.getStatus() == 2){
//            return;
//        }
//        Intent intent = new Intent(mActivity,CommodityDetailActivity.class);
//        intent.putExtra("goods",goods);
//        startActivityForResult(intent, 102);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 102:
                refreshData();
                break;
        }
    }

    private void refreshData() {
        goods = superMarketBean.getCartGoods();
        if (goods.size() == 0) {
            bottom.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            cartList.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        } else {
            List<Goods> data = adapter.getData();
            for (int i = data.size() - 1; i >= 0; i--) {
                Goods good = data.get(i);
                boolean hasThisGood = false;
                for (SuperMarketCartModel.CartGoods cartGood : goods) {
                    if (good.getId() == cartGood.getGoodsId()) {
                        good.setCount(cartGood.getButCount());
                        hasThisGood = true;
                        break;
                    }
                }
                if (!hasThisGood) {
                    data.remove(i);
                }
            }
            adapter.setData(data);
            setTotalPrice();
        }
    }

    public static MarketCartActivity instance;

    public void paySuccess() {
        instance.finish();
    }
}
