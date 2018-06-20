package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.CouDanModel;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.bean.PickGoods;
import com.project.mgjandroid.bean.yellowpage.GoodsAttribute;
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.ui.listener.BottomCartListener;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SunXueLiang on 2018/6/8.
 */
public class CouDanListAdapter extends BaseAdapter {
    private Context context;
    private List<PickGoods> products;
    private List<Goods> couDanModelValue;
    private BottomCartListener listener;
    private PickGoods pickGoods;
    List<GoodsSpec> goodsSpecList = new ArrayList<>();

    private PickGoods product;
    private Goods goods;
    private GoodsAttribute goodsAttribute;

    public CouDanListAdapter(Context context, List<Goods> couDanModelValue, List<PickGoods> mCartProducts, BottomCartListener listener) {
        this.context = context;
        this.couDanModelValue = couDanModelValue;
        this.products = mCartProducts;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return couDanModelValue.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_coudan, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        goods = couDanModelValue.get(i);
        Log.d("------", goods.getName());
        if (goods.getTGoodsSpec() != null) {
            if (CheckUtils.isEmptyStr(goods.getTGoodsSpec().getSpec())) {
                holder.tv_name.setText(goods.getName());
            } else {
                if(CheckUtils.isEmptyList(goods.getGoodsAttributeList())){
                    holder.tv_name.setText(goods.getName() + "(" + goods.getTGoodsSpec().getSpec() + ")");
                }else {
                    holder.tv_name.setText(goods.getName() + "(" + goods.getTGoodsSpec().getSpec() + ")"+goods.getGoodsAttributeList().get(0).getName());
                }
            }
        }
        showBuyView(goods, holder, i);

        return convertView;
    }

    private void showBuyView(final Goods bean, final ViewHolder holder, final int position) {
        int buy = 0;
        if(CheckUtils.isNoEmptyList(bean.getGoodsAttributeList())){
            for(int i=0;i<bean.getGoodsAttributeList().size();i++){
                goodsAttribute = bean.getGoodsAttributeList().get(i);
            }
        }
        final GoodsSpec goodsSpec = bean.getTGoodsSpec();
        goodsSpecList.clear();
        for (int i = 0; i < couDanModelValue.size(); i++) {
            goodsSpecList.add(couDanModelValue.get(i).getTGoodsSpec());
        }
        bean.setGoodsSpecList(goodsSpecList);

        for (PickGoods pGoods : products) {
            if (bean.getId() == pGoods.getGoodsId() && goodsSpec.getId() == pGoods.getGoodsSpecId()) {
                buy = pGoods.getPickCount();
            }
        }

        holder.tvBuyCount.setText("" + buy);
        goodsSpec.setBuyCount(buy);
        if (buy == 0) {
            holder.tv_price.setText("" + goodsSpec.getPrice());
        } else {
            holder.tv_price.setText("" + goodsSpec.getPrice().multiply(new BigDecimal(buy)));
        }

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int count = goodsSpec.getBuyCount();
                int maxCount = goodsSpec.getOrderLimit();
                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && count >= goodsSpec.getStock()) {
                    ToastUtils.displayMsg("该商品库存不足", context);
                    return;
                }
                if (count == 0) {
                    if (goodsSpec.getMinOrderNum() != 0 && count <= goodsSpec.getMinOrderNum()) {
                        ToastUtils.displayMsg(bean.getName() + "商品最少购买" + goodsSpec.getMinOrderNum() + "份", context);
                        count = goodsSpec.getMinOrderNum() - 1;
                    }
                }
                if (maxCount != 0 && count >= maxCount) {
                    ToastUtils.displayMsg(bean.getName() + "商品限购" + goodsSpec.getOrderLimit() + "份", context);
                    return;
                }
                if (count == 0) {
                    count++;
                    goodsSpec.setBuyCount(count);
                    holder.tvBuyCount.setText(count + "");
                } else {
                    count++;
                    goodsSpec.setBuyCount(count);
                    holder.tvBuyCount.setText(count + "");
                }
                if(CheckUtils.isEmptyList(bean.getGoodsAttributeList())){
                    //只要点击了就去更新购物车
                    listener.productHasChange(bean, bean.getCategoryId(), bean.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false);
                }else {
                    listener.newProductHasChange(bean, bean.getCategoryId(), bean.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false,goodsAttribute.getName());
                }

            }
        });
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int count = goodsSpec.getBuyCount();
                    if (count == goodsSpec.getMinOrderNum() ) {
                        if (goodsSpec.getMinOrderNum() != 0 && count <= goodsSpec.getMinOrderNum()) {
                            ToastUtils.displayMsg(goods.getName() + "商品最少购买" + goodsSpec.getMinOrderNum() + "份", context);
                        }
                        count = 1;
                    }
                if (count == 1) {
                    count--;
                    goodsSpec.setBuyCount(count);
                    holder.tvBuyCount.setText(count + "");
                    if (CheckUtils.isEmptyList(bean.getGoodsAttributeList())) {
                        //只要点击了就去更新购物车
                        listener.productHasChange(bean, bean.getCategoryId(), bean.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), true, false);
                    } else {
                        listener.newProductHasChange(bean, bean.getCategoryId(), bean.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), true, false,goodsAttribute.getName());
                    }

                } else {
                    if (count > 0) {
                        count--;
                        holder.tvBuyCount.setText(count + "");
                        goodsSpec.setBuyCount(count);
                        if (CheckUtils.isEmptyList(bean.getGoodsAttributeList())) {
                            //只要点击了就去更新购物车
                            listener.productHasChange(bean, bean.getCategoryId(), bean.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false);
                        } else {
                            listener.newProductHasChange(bean, bean.getCategoryId(), bean.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false,goodsAttribute.getName());
                        }
                    }
                }
            }
        });
    }

    public void setData(List<Goods> data) {
        couDanModelValue = data;
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tv_name;
        TextView tv_price;
        ImageView imgAdd;
        TextView tvBuyCount;
        ImageView imgMinus;

        public ViewHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.product_name);
            tv_price = (TextView) view.findViewById(R.id.goods_item_tv_price);
            imgAdd = (ImageView) view.findViewById(R.id.goods_item_img_add);
            tvBuyCount = (TextView) view.findViewById(R.id.goods_item_tv_buy_count);
            imgMinus = (ImageView) view.findViewById(R.id.goods_item_img_minus);
        }
    }
}
