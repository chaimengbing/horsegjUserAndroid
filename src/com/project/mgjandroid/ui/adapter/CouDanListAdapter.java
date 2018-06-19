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
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.ui.listener.BottomCartListener;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;

import java.math.BigDecimal;
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

    private PickGoods product;
    private Goods goods;

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
                holder.tv_name.setText(goods.getName() + "(" + goods.getTGoodsSpec().getSpec() + ")");
            }
        }
        showBuyView(goods, holder, products);

        return convertView;
    }

    private void showBuyView(final Goods bean, final ViewHolder holder, final List<PickGoods> products) {
        int buy = 0;
        final GoodsSpec goodsSpec = bean.getTGoodsSpec();
        for (int i = 0; i < products.size(); i++) {
            product = products.get(i);
            List<GoodsSpec> goodsSpecList = product.getGoods().getGoodsSpecList();
            goodsSpecList.add(bean.getTGoodsSpec());
        }


        for (PickGoods pGoods : products) {
            if (bean.getId() == pGoods.getGoodsId() && goodsSpec.getId() == pGoods.getGoodsSpecId()) {
                buy = pGoods.getPickCount();
            }
        }

        holder.tvBuyCount.setText("" + buy);
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
                    if (bean.getTGoodsSpec().getMinOrderNum() != 0 && count <= bean.getTGoodsSpec().getMinOrderNum()) {
                        ToastUtils.displayMsg(bean.getName() + "商品最少购买" + goodsSpec.getMinOrderNum() + "份", context);
                        count = goods.getTGoodsSpec().getMinOrderNum() - 1;
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
                //只要点击了就去更新购物车
                listener.productHasChange(bean, bean.getCategoryId(), bean.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, true);
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
                    if (product.getGoods().getEveryGoodsEveryOrderBuyCount() <= product.getGoods().getSurplusDiscountStock()) {
                        if (count - product.getGoods().getEveryGoodsEveryOrderBuyCount() <= 0) {
                            product.getGoods().setFirst(true);
                        }
                    } else {
                        if (count - product.getGoods().getSurplusDiscountStock() <= 0) {
                            product.getGoods().setFirst(true);
                        }
                    }
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
                        if (product.getGoods().getEveryGoodsEveryOrderBuyCount() <= product.getGoods().getSurplusDiscountStock()) {
                            if (count - product.getGoods().getEveryGoodsEveryOrderBuyCount() <= 0) {
                                product.getGoods().setFirst(true);
                            }
                        } else {
                            if (count - product.getGoods().getSurplusDiscountStock() <= 0) {
                                product.getGoods().setFirst(true);
                            }
                        }
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
