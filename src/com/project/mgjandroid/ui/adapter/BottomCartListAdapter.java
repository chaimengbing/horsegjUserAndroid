package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.bean.PickGoods;
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.ui.listener.BottomCartListener;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;

import java.util.List;

/**
 * Created by ning on 2016/3/9.
 */
public class BottomCartListAdapter extends BaseAdapter {
    private Context context;
    private List<PickGoods> products;
    private BottomCartListener listener;

    public BottomCartListAdapter(Context context, List<PickGoods> mCartProducts, BottomCartListener listener) {
        this.context = context;
        this.products = mCartProducts;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return products.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bottom_cart, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        PickGoods product = products.get(i);
        String spec = "";
        for (int j = 0; j < product.getGoods().getGoodsSpecList().size(); j++) {
            if (product.getGoodsSpecId() == product.getGoods().getGoodsSpecList().get(j).getId()) {
                if (!TextUtils.isEmpty(product.getGoods().getGoodsSpecList().get(j).getSpec())) {
                    spec = " (" + product.getGoods().getGoodsSpecList().get(j).getSpec() + ")";
                }
                break;
            }
        }
        if (product.getGoodsName() != null) {
            holder.tv_name.setText(product.getGoods().getName() + spec + product.getGoodsName());
        } else {
            holder.tv_name.setText(product.getGoods().getName() + spec);
        }

        showBuyView(product, holder);
        return convertView;
    }

    private void showBuyView(final PickGoods product, final ViewHolder holder) {
        GoodsSpec myGoodsSpec = null;
        for (GoodsSpec spec : product.getGoods().getGoodsSpecList()) {
            if (spec.getId() == product.getGoodsSpecId()) {
                myGoodsSpec = spec;
            }
        }
        final GoodsSpec goodsSpec = myGoodsSpec;
        final int maxCount = goodsSpec.getOrderLimit();
        holder.tv_price.setText(goodsSpec.getPrice() + "");
        holder.tvBuyCount.setText(product.getPickCount() + "");
        if (product.getPickCount() > 0) {
            holder.imgMinus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, context));
            holder.tvBuyCount.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, context));
        } else {
            holder.imgMinus.setTranslationX(0f);
            holder.tvBuyCount.setTranslationX(0f);
        }

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int count = product.getPickCount();
                int buyCount = 0;
                for (int i = 0; i < products.size(); i++) {
                    if (product.getGoodsId() == products.get(i).getGoods().getId() && product.getGoodsSpecId() == products.get(i).getGoodsSpecId()) {
                        buyCount += products.get(i).getPickCount();
                    }
                }
                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && buyCount >= goodsSpec.getStock()) {
                    ToastUtils.displayMsg("该商品库存不足", context);
                    return;
                }
                if (maxCount != 0 && buyCount >= maxCount) {
                    String spec = "";
                    if (!TextUtils.isEmpty(goodsSpec.getSpec())) {
                        spec = " (" + goodsSpec.getSpec() + ")";
                    }
                    ToastUtils.displayMsg(product.getGoods().getName() + spec + "商品限购" + goodsSpec.getOrderLimit() + "份", context);
                    return;
                }
                if (count == 0) {
                    count++;
                    product.setPickCount(count);
                    goodsSpec.setBuyCount(count);
                    holder.tvBuyCount.setText(count + "");
                    AnimatorUtils.leftTranslationRotating(holder.imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, context));
                    AnimatorUtils.leftTranslationRotating(holder.tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, context));
                } else {
                    count++;
                    holder.tvBuyCount.setText(count + "");
                    goodsSpec.setBuyCount(count);
                    product.setPickCount(count);
                }
                if (CheckUtils.isEmptyStr(product.getGoodsName())) {
                    //只要点击了就去更新购物车
                    listener.productHasChange(product.getGoods(), product.getGoods().getCategoryId(), product.getGoods().getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false);
                } else {
                    //只要点击了就去更新购物车
                    listener.newProductHasChange(product.getGoods(), product.getGoods().getCategoryId(), product.getGoods().getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false, product.getGoodsName());
                }

            }
        });
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int count = product.getPickCount();
                for (int i = 0; i < product.getGoods().getGoodsSpecList().size(); i++) {
                    if (count == product.getGoods().getGoodsSpecList().get(i).getMinOrderNum() && product.getGoods().getGoodsSpecList().get(i).getId() == goodsSpec.getId()) {
                        if (product.getGoods().getGoodsSpecList().get(i).getMinOrderNum() != 0 && count <= product.getGoods().getGoodsSpecList().get(i).getMinOrderNum()) {
                            ToastUtils.displayMsg(product.getGoods().getName() + "商品最少购买" + product.getGoods().getGoodsSpecList().get(i).getMinOrderNum() + "份", context);
                        }
                        count = 1;
                        break;
                    }
                }
                if (count == 1) {
                    count--;
                    product.setPickCount(count);
                    holder.tvBuyCount.setText(count + "");
                    if (CheckUtils.isEmptyList(product.getGoods().getGoodsAttributeList())) {
                        goodsSpec.setBuyCount(count);
//                    AnimatorUtils.rightTranslationRotating(holder.imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, context));
//                    AnimatorUtils.rightTranslationRotating(holder.tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, context));
                        //只要点击了就去更新购物车
                        listener.productHasChange(product.getGoods(), product.getGoods().getCategoryId(), product.getGoods().getId(), goodsSpec.getId(), product.getPickCount(), true, false);
                    } else {
                        listener.newProductHasChange(product.getGoods(), product.getGoods().getCategoryId(), product.getGoods().getId(), goodsSpec.getId(), product.getPickCount(), true, false, product.getGoodsName());
                    }

                } else {
                    if (count > 0) {
                        count--;
                        holder.tvBuyCount.setText(count + "");
                        product.setPickCount(count);
                        if (CheckUtils.isEmptyList(product.getGoods().getGoodsAttributeList())) {
                            goodsSpec.setBuyCount(count);
                            //只要点击了就去更新购物车
                            listener.productHasChange(product.getGoods(), product.getGoods().getCategoryId(), product.getGoods().getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false);
                        } else {
                            listener.newProductHasChange(product.getGoods(), product.getGoods().getCategoryId(), product.getGoods().getId(), goodsSpec.getId(), product.getPickCount(), false, false, product.getGoodsName());
                        }
                    }
                }
            }
        });
    }

    public void setData(List<PickGoods> data) {
        products = data;
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
